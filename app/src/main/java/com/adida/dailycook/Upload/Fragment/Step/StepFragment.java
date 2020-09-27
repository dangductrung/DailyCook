package com.adida.dailycook.Upload.Fragment.Step;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.adida.dailycook.R;
import com.adida.dailycook.Upload.Fragment.Upload.StepUploadRecyclerView.StepUploadModel;
import com.adida.dailycook.Upload.ViewModel.UploadViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class StepFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 71;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private UploadViewModel model;
    private EditText editText;
    private TimePicker timePicker;
    private ImageView illustration;
    private String url;

    //firebase
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private java.util.UUID UUID;
    private Uri uri;

    public StepFragment() {
        // Required empty public constructor
    }

    public static StepFragment newInstance(String param1, String param2) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_upload, container, false);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        model = new ViewModelProvider(requireActivity()).get(UploadViewModel.class);

        editText = view.findViewById(R.id.editTextDescriptionStepUploadFragment);
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        timePicker = view.findViewById(R.id.timePickerStepUploadFragment);
        timePicker.setIs24HourView(true);
        timePicker.setHour(0);
        timePicker.setMinute(0);

        TextView textView = view.findViewById(R.id.textViewStepOrderStepUploadFragment);
        if (model.getSteps().getValue() == null)
            textView.setText("1");
        else
            textView.setText(Integer.toString(model.getSteps().getValue().size() + 1));

        illustration = view.findViewById(R.id.imageViewIllustrationAddingStepUploadFragment);
        illustration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        return view;
    }

    private void uploadImage(Uri uri) {
        if (uri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Đang tải hình...");
            progressDialog.show();

            UUID = java.util.UUID.randomUUID();

            final StorageReference ref = storageReference.child("images/" + UUID.toString());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //url of img on firebase
                                    url = String.valueOf(uri);

                                    Picasso.get().load(url).into(illustration);

                                    progressDialog.dismiss();

                                    Toast.makeText(getContext(), "Tải hình thành công", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            url = null;
                            Toast.makeText(getContext(), "Tải hình thất bại ", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            url = null;
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Đã tải " + (int) progress + "%");
                        }
                    });

        }

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        //file = image.getAbsolutePath();
        return image;
    }

    private void selectImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select_image, null);

        builder.setView(dialogView);
        AlertDialog listener = builder.show();

        TextView take = dialogView.findViewById(R.id.textViewTakeSelectImageDialog);
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeImage();
                listener.dismiss();
            }
        });

        TextView pick = dialogView.findViewById(R.id.textViewPickSelectImageDialog);
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                listener.dismiss();
            }
        });

        TextView cancel = dialogView.findViewById(R.id.textViewCancelSelectImageDialog);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.dismiss();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void takeImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                uri = FileProvider.getUriForFile(getContext(),
                        "com.adida.dailycook.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            //here
            uploadImage(data.getData());
        }

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK
                && data != null) {
            //here
            uploadImage(uri);
        }
    }

    public StepUploadModel createStep() {
        StepUploadModel model = new StepUploadModel();
        model.setDescription(editText.getText().toString());
        model.setDuration(timePicker.getHour() * 60 + timePicker.getMinute());
        model.setUrl(url);

        return model;
    }

}
