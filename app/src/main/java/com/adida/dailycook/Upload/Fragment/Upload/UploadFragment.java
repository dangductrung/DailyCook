package com.adida.dailycook.Upload.Fragment.Upload;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.adida.dailycook.Upload.Fragment.Step.StepFragment;
import com.adida.dailycook.Upload.Fragment.Tag.TagFragment;
import com.adida.dailycook.Upload.Fragment.Upload.IngredientUploadRecyclerView.IngredientUploadRecyclerViewAdapter;
import com.adida.dailycook.Upload.Fragment.Upload.StepUploadRecyclerView.StepUploadModel;
import com.adida.dailycook.Upload.Fragment.Upload.StepUploadRecyclerView.StepUploadRecyclerViewAdapter;
import com.adida.dailycook.Upload.Fragment.Upload.TagUploadRecyclerview.TagUploadRecyclerViewAdapter;
import com.adida.dailycook.Upload.ViewModel.UploadViewModel;
import com.adida.dailycook.retrofit2.entities.Tag;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class UploadFragment extends Fragment implements StepUploadRecyclerViewAdapter.StepUploadListener {
    private static final int PICK_IMAGE_REQUEST = 71;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private List<Tag> tags;
    private List<String> ingredients;
    private List<StepUploadModel> steps;
    private TagUploadRecyclerViewAdapter tagAdapter;
    private IngredientUploadRecyclerViewAdapter ingredientAdapter;
    private StepUploadRecyclerViewAdapter stepAdapter;
    private ImageView illustration;
    private EditText title;
    private EditText description;
    private UploadViewModel model;

    private StorageReference storageReference;

    private String url;
    private Uri uri;

    public UploadFragment() {
        // Required empty public constructor
    }

    public static UploadFragment newInstance(String param1, String param2) {
        UploadFragment fragment = new UploadFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        //firebase
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        RecyclerView tagRecyclerView = view.findViewById(R.id.recyclerViewTagUploadFragment);
        RecyclerView ingredientRecyclerView = view.findViewById(R.id.recyclerViewIngredientUploadFragment);
        RecyclerView stepRecyclerView = view.findViewById(R.id.recyclerViewStepUploadFragment);

        FlexboxLayoutManager tagLayoutManager = new FlexboxLayoutManager(getContext());
        tagLayoutManager.setFlexWrap(FlexWrap.WRAP);
        tagLayoutManager.setFlexDirection(FlexDirection.ROW);
        tagLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        tagRecyclerView.setLayoutManager(tagLayoutManager);

        tags = new ArrayList<>();
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();

        model = new ViewModelProvider(requireActivity()).get(UploadViewModel.class);
        model.getTags().observe(getViewLifecycleOwner(), data -> {
            tags.clear();
            tags.addAll(data);
            tagAdapter.notifyDataSetChanged();
        });

        model.getIngredients().observe(getViewLifecycleOwner(), data -> {
            ingredients.clear();
            ingredients.addAll(data);
            ingredientAdapter.notifyDataSetChanged();
        });

        model.getSteps().observe(getViewLifecycleOwner(), data -> {
            steps.clear();
            steps.addAll(data);
            stepAdapter.notifyDataSetChanged();
        });

        tagAdapter = new TagUploadRecyclerViewAdapter(requireActivity(), tags);
        ingredientAdapter = new IngredientUploadRecyclerViewAdapter(requireActivity(), ingredients);
        stepAdapter = new StepUploadRecyclerViewAdapter(requireActivity(), steps);
        stepAdapter.setOnShareClickedListener(this);

        tagRecyclerView.setAdapter(tagAdapter);
        ingredientRecyclerView.setAdapter(ingredientAdapter);
        stepRecyclerView.setAdapter(stepAdapter);

        final EditText ingredientEditText = view.findViewById(R.id.editTextIngredientAddingUploadFragment);
        ingredientEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        ingredientEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        title = view.findViewById(R.id.editTextRecipeNameUploadFragment);
        title.setRawInputType(InputType.TYPE_CLASS_TEXT);
        title.setImeOptions(EditorInfo.IME_ACTION_DONE);

        description = view.findViewById(R.id.editTextDescriptionUploadFragment);
        description.setRawInputType(InputType.TYPE_CLASS_TEXT);
        description.setImeOptions(EditorInfo.IME_ACTION_DONE);

        Button tagButton = view.findViewById(R.id.buttonTagAddingUploadFragment);
        tagButton.setOnClickListener(v -> loadFragment(new TagFragment()));

        ImageButton ingredientButton = view.findViewById(R.id.imageButtonIngredientAddingUploadFragment);
        ingredientButton.setOnClickListener(v -> {
            if (!ingredientEditText.getText().toString().isEmpty()) {
                model.addIngredient(ingredientEditText.getText().toString());
                stepAdapter.notifyDataSetChanged();
                ingredientEditText.setText("");
            }
        });

        Button stepButton = view.findViewById(R.id.buttonStepAddingUploadFragment);
        stepButton.setOnClickListener(v -> {
            //List<StepUploadModel> sub = new ArrayList<StepUploadModel>();
            //StepUploadModel temp = new StepUploadModel();
            //temp.setDescription("Hello World");
            //sub.add(temp);
            //model.addSteps(sub);
            //stepAdapter.notifyDataSetChanged();
            loadFragment(new StepFragment());
        });

        illustration = view.findViewById(R.id.imageViewIllustrationAddingUploadFragment);

        if(url != null)
            Picasso.get().load(url).into(illustration);

        illustration.setOnClickListener(v -> selectImage());

        return view;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        @SuppressLint({"NewApi", "LocalSuppress"}) FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutUpload, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void removeStep(int index) {
        model.removeStep(index);
    }

    private void uploadImage(Uri uri) {
        if (uri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Đang tải hình...");
            progressDialog.show();

            java.util.UUID UUID = java.util.UUID.randomUUID();

            final StorageReference ref = storageReference.child("images/" + UUID.toString());
            ref.putFile(uri)
                    .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri1 -> {
                        //url of img on firebase
                        url = String.valueOf(uri1);

                        Picasso.get().load(url).into(illustration);

                        progressDialog.dismiss();

                        Toast.makeText(getContext(), "Tải hình thành công", Toast.LENGTH_SHORT).show();
                    }))
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Tải hình thất bại ", Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Đã tải " + (int) progress + "%");
                    });

        }

    }

    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Objects.requireNonNull(getContext()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //file = image.getAbsolutePath();
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    private void selectImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select_image, null);

        builder.setView(dialogView);
        AlertDialog listener = builder.show();

        TextView take = dialogView.findViewById(R.id.textViewTakeSelectImageDialog);
        take.setOnClickListener(v -> {
            takeImage();
            listener.dismiss();
        });

        TextView pick = dialogView.findViewById(R.id.textViewPickSelectImageDialog);
        pick.setOnClickListener(v -> {
            chooseImage();
            listener.dismiss();
        });

        TextView cancel = dialogView.findViewById(R.id.textViewCancelSelectImageDialog);
        cancel.setOnClickListener(v -> listener.dismiss());
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void takeImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                uri = FileProvider.getUriForFile(Objects.requireNonNull(getContext()),
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

    public Map<String, String> createRecipe() {
        Map<String, String> map = new HashMap<>();

        map.put("title", title.getText().toString());
        map.put("description", description.getText().toString());
        map.put("illustration", url);

        return map;
    }
}