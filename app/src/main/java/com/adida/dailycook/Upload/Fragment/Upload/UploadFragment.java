package com.adida.dailycook.Upload.Fragment.Upload;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.adida.dailycook.SharedPreference.SharedPreference;
import com.adida.dailycook.Upload.Fragment.Upload.IngredientUploadRecyclerView.IngredientUploadRecyclerViewAdapter;
import com.adida.dailycook.Upload.Fragment.Upload.StepUploadRecyclerView.StepUploadModel;
import com.adida.dailycook.Upload.Fragment.Upload.StepUploadRecyclerView.StepUploadRecyclerViewAdapter;
import com.adida.dailycook.Upload.Fragment.Upload.TagUploadRecyclerview.TagUploadRecyclerViewAdapter;
import com.adida.dailycook.Upload.Fragment.Upload.ViewModel.UploadViewModel;
import com.adida.dailycook.config.Config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UploadFragment extends Fragment implements StepUploadRecyclerViewAdapter.StepUploadListener {
    private View view;
    private List<String> tags;
    private List<String> ingredients;
    private List<StepUploadModel> steps;
    private TagUploadRecyclerViewAdapter tagAdapter;
    private IngredientUploadRecyclerViewAdapter ingredientAdapter;
    private StepUploadRecyclerViewAdapter stepAdapter;
    private UploadViewModel model;

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
        view = inflater.inflate(R.layout.fragment_upload, container, false);

        RecyclerView tagRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewTagUploadFragment);
        RecyclerView ingredientRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewIngredientUploadFragment);
        RecyclerView stepRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewStepUploadFragment);

        tags = new ArrayList<String>();
        ingredients = new ArrayList<String>();
        steps = new ArrayList<StepUploadModel>();

        model = new ViewModelProvider(requireActivity()).get(UploadViewModel.class);
        model.getTags().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> data) {
                tags.clear();
                tags.addAll(data);
                tagAdapter.notifyDataSetChanged();
            }
        });

        model.getIngredients().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> data) {
                ingredients.clear();
                ingredients.addAll(data);
                ingredientAdapter.notifyDataSetChanged();
            }
        });

        model.getSteps().observe(getViewLifecycleOwner(), new Observer<List<StepUploadModel>>() {
            @Override
            public void onChanged(List<StepUploadModel> data) {
                steps.clear();
                steps.addAll(data);
                stepAdapter.notifyDataSetChanged();
            }
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

        EditText title = view.findViewById(R.id.editTextRecipeNameUploadFragment);
        title.setRawInputType(InputType.TYPE_CLASS_TEXT);
        title.setImeOptions(EditorInfo.IME_ACTION_DONE);

        Button tagButton = view.findViewById(R.id.buttonTagAddingUploadFragment);
        tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ImageButton ingredientButton = view.findViewById(R.id.imageButtonIngredientAddingUploadFragment);
        ingredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ingredientEditText.getText().toString().isEmpty()) {
                    model.addIngredient(ingredientEditText.getText().toString());
                    stepAdapter.notifyDataSetChanged();
                    ingredientEditText.setText("");
                }
            }
        });

        Button stepButton = view.findViewById(R.id.buttonStepAddingUploadFragment);
        stepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<StepUploadModel> sub = new ArrayList<StepUploadModel>();
                StepUploadModel temp = new StepUploadModel();
                temp.setDescription("Hello World");
                sub.add(temp);
                model.addSteps(sub);
                stepAdapter.notifyDataSetChanged();
            }
        });

        ImageButton illustrationButton = view.findViewById(R.id.imageButtonIllustrationAddingUploadFragment);
        tagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @SuppressLint("SimpleDateFormat")
    private void LoadHomepageData() {
        TextView userTextView = view.findViewById(R.id.textViewFirstNameHomepage);
        userTextView.setText(SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).get(Config.SHARED_PREFERENCES.USER.NAME, String.class));

        TextView dateTextView = view.findViewById(R.id.textViewDateHomepage);
        dateTextView.setText(new SimpleDateFormat("MM/dd/YYYY").format(Calendar.getInstance().getTime()));
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutUpload, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void removeStep(int index) {
        model.removeStep(index);
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}