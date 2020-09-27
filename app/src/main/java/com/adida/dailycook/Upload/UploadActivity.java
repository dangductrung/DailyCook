package com.adida.dailycook.Upload;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

        final ImageButton imageButton = findViewById(R.id.imageButtonBackUploadActivity);


        frameLayout.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChildViewAdded(View parent, View child) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayoutUpload);
                if (fragment instanceof UploadFragment) {
                    textView.setText("Đăng tải công thức");
                    imageButton.setOnClickListener(v -> finish());
                    button.setOnClickListener(v -> upload());
                } else if (fragment instanceof TagFragment) {
                    textView.setText("Thêm thể loại");
                    imageButton.setOnClickListener(v -> {
                        getOrigin();
                        getSupportFragmentManager().popBackStack();
                    });
                    button.setOnClickListener(v -> getSupportFragmentManager().popBackStack());
                } else if (fragment instanceof StepFragment) {
                    textView.setText("Thêm bước chế biến");
                    imageButton.setOnClickListener(v -> getSupportFragmentManager().popBackStack());
                    button.setOnClickListener(v -> createStep());
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

    private void createStep() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayoutUpload);
        assert fragment != null;
        StepUploadModel step = ((StepFragment) fragment).createStep();

        if (!step.getDescription().isEmpty()) {
            model.addStep(step);

            getSupportFragmentManager().popBackStack();
        }
        else
            Toast.makeText(this, "Vui lòng điền mô tả bước chế biến", Toast.LENGTH_SHORT).show();
    }

    private void getOrigin() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayoutUpload);
        assert fragment != null;
        List<Tag> origin = ((TagFragment) fragment).getOrigin();

        model.setTags(origin);
    }

    private void upload() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayoutUpload);
        assert fragment != null;
        Map<String, String> recipeInfo = ((UploadFragment) fragment).createRecipe();


        HashMap<String, Object> params = new HashMap<>();

        if (Objects.requireNonNull(recipeInfo.get("title")).isEmpty()) {
            Toast.makeText(this, "Vui lòng điền tên công thức", Toast.LENGTH_SHORT).show();
            return;
        } else if (model.getTags().getValue() == null) {
            Toast.makeText(this, "Vui lòng thêm thể loại", Toast.LENGTH_SHORT).show();
            return;
        } else if (model.getIngredients().getValue() == null) {
            Toast.makeText(this, "Vui lòng thêm nguyên liệu", Toast.LENGTH_SHORT).show();
            return;
        } else if (recipeInfo.get("illustration") == null) {
            Toast.makeText(this, "Vui lòng chọn hình ảnh minh họa", Toast.LENGTH_SHORT).show();
            return;
        } else if (model.getSteps().getValue() == null) {
            Toast.makeText(this, "Vui lòng thêm bước chế biến", Toast.LENGTH_SHORT).show();
            return;
        }

        int totalTime = 0;
        ArrayList<Map<String, Object>> steps = new ArrayList<>();
        Map<String, Object> step = new HashMap<>();
        ArrayList<String> tags = new ArrayList<>();

        for (StepUploadModel element : model.getSteps().getValue()) {
            step.put("stepDescription", element.getDescription());
            step.put("stepDuration", element.getDuration());
            step.put("stepImageUrl", element.getUrl());
            totalTime += element.getDuration();
            steps.add(step);
        }

        for (Tag element : model.getTags().getValue())
            tags.add(element.getTitle());

        params.put("recipe_name", recipeInfo.get("title"));
        params.put("user_id", SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).get(Config.SHARED_PREFERENCES.USER.ID, Integer.class));
        params.put("recipe_description", recipeInfo.get("description"));
        params.put("image_url", recipeInfo.get("illustration"));
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

                getSupportFragmentManager().popBackStack();

                Handler handler = new Handler();
                handler.postDelayed(() -> finish(), 1000);
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                dialog.dismiss();
                dialog.setTitle("Loading...");
                Toast.makeText(getApplicationContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayoutUpload);
        if (fragment instanceof UploadFragment) {
            getSupportFragmentManager().popBackStack();
            finish();
        } else if (fragment instanceof TagFragment) {
            model.setTags(new ArrayList<>());
            getSupportFragmentManager().popBackStack();
        } else if (fragment instanceof StepFragment) {
            getSupportFragmentManager().popBackStack();
        }
    }

}