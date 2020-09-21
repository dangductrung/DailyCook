package com.adida.dailycook.Upload;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.adida.dailycook.R;
import com.adida.dailycook.SharedPreference.SharedPreference;
import com.adida.dailycook.Upload.Fragment.Step.StepFragment;
import com.adida.dailycook.Upload.Fragment.Tag.TagFragment;
import com.adida.dailycook.Upload.Fragment.Upload.StepUploadRecyclerView.StepUploadModel;
import com.adida.dailycook.Upload.Fragment.Upload.UploadFragment;
import com.adida.dailycook.Upload.ViewModel.UploadViewModel;
import com.adida.dailycook.config.Config;
import com.adida.dailycook.retrofit2.ServiceManager;
import com.adida.dailycook.retrofit2.entities.Tag;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {
    private UploadViewModel model;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload);

        FrameLayout frameLayout = findViewById(R.id.frameLayoutUpload);

        model = new ViewModelProvider(this).get(UploadViewModel.class);

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.SpotsDialog)
                .build();

        final TextView textView = findViewById(R.id.textViewTitleUploadActivity);

        final Button button = findViewById(R.id.buttonDoneUploadActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        final ImageButton imageButton = findViewById(R.id.imageButtonBackUploadActivity);


        frameLayout.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayoutUpload);
                if (fragment instanceof UploadFragment) {
                    textView.setText("Đăng tải công thức");
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.show();

                        }
                    });
                } else if (fragment instanceof TagFragment) {
                    textView.setText("Thêm thể loại");
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            model.setTags(new ArrayList<Tag>());
                            getSupportFragmentManager().popBackStack();
                        }
                    });
                } else if (fragment instanceof StepFragment) {
                    textView.setText("Thêm bước chế biến");
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getSupportFragmentManager().popBackStack();
                        }
                    });
                }
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
            }
        });


        loadFragment(new UploadFragment());

    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutUpload, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void onPublishRecipeButtonClicked() {

        HashMap<String, Object> params = new HashMap<>();
        //params.put("recipe_name", foodName.getText().toString());
        params.put("user_id", SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).get(Config.SHARED_PREFERENCES.USER.ID, Integer.class));
        params.put("recipe_description", "");
        //params.put("image_url", recipeImageUrl);

        int totalTime = 0;

        ArrayList<Map<String, Object>> steps = new ArrayList<>();
        Map<String, Object> step = new HashMap<>();
        for (StepUploadModel element : model.getSteps().getValue()) {
            step.put("stepDescription", element.getDescription());
            step.put("stepDuration", element.getDuration());
            step.put("stepImageUrl", element.getUrl());
            totalTime += element.getDuration();
            steps.add(step);
        }

        ArrayList<String> tags = new ArrayList<>();
        for (Tag element : model.getTags().getValue())
            tags.add(element.getTitle());


        params.put("recipe_time", totalTime);
        params.put("recipeSteps", steps);
        params.put("recipeIngredients", model.getIngredients().getValue());
        params.put("tags", tags);

        dialog.setTitle("Uploading...");
        dialog.show();
        ServiceManager.getInstance().getRecipeService().addRecipe(params).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                dialog.dismiss();
                dialog.setTitle("Loading...");
                Toast.makeText(getApplicationContext(), "Đã đăng tải thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                dialog.dismiss();
                dialog.setTitle("Loading...");
                Toast.makeText(getApplicationContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}