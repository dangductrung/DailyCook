package com.adida.dailycook.recipeDetail;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.adida.dailycook.recipeDetail.IngredientRecyclerView.IngredientRecyclerViewAdapter;
import com.adida.dailycook.recipeDetail.StepRecyclerView.StepRecyclerViewAdapter;
import com.adida.dailycook.retrofit2.ServiceManager;
import com.adida.dailycook.retrofit2.entities.RecipeDetail;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailPage extends AppCompatActivity {
    private int recipeID;
    ImageButton backButton, likeButton;
    ImageView recipeImage;
    RecyclerView ingredientRecipeDetail , stepRecipeDetail;
    TextView recipeTitle;
    Button startButton;
    RecipeDetail detail;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        recipeID = getIntent().getIntExtra("id", 0);

        backButton = (ImageButton) findViewById(R.id.btnBackButton);
        likeButton = (ImageButton) findViewById(R.id.btnLikeButton);
        recipeImage = (ImageView) findViewById(R.id.RecipeImage);
        ingredientRecipeDetail = findViewById(R.id.IngredientRecipeDetail);
        recipeTitle = findViewById(R.id.RecipeTitle);
        stepRecipeDetail = findViewById(R.id.StepRecipeDetail);
        startButton = (Button) findViewById(R.id.startButton);

        LoadData();
        setVariable();
        addAction();
    }

    private void setVariable() {

    }

    private void addAction() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                didTapBackButton();
            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                didTapLikeButton();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                didTapStartButton();
            }
        });
    }

    // Action
    private void didTapBackButton() {
        finish();
    }

    private void didTapLikeButton() {
    }

    private void didTapStartButton() {
        finish();
    }

    private void LoadData() {
        ServiceManager.getInstance().getRecipeService().getRecipeDetail(recipeID).enqueue(new Callback<RecipeDetail>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<RecipeDetail> call, Response<RecipeDetail> response) {
                if (response.body() != null) {
                    detail = response.body();
                    recipeTitle.setText(detail.getName());

                    try {
                        Picasso.get().setLoggingEnabled(true);
                        Picasso.get().load(detail.getImageUrl()).error(R.drawable.ic_error).placeholder(R.drawable.ic_placeholder).into(recipeImage);
                    } catch (Exception e) {
                        recipeImage.setImageAlpha(R.drawable.ic_error);
                    }

                    ingredientRecipeDetail.setAdapter(new IngredientRecyclerViewAdapter(getApplicationContext(), detail.getIngredients()));
                    ingredientRecipeDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext().getApplicationContext()));

                    stepRecipeDetail.setAdapter(new StepRecyclerViewAdapter(getApplicationContext(), detail.getSteps()));
                    stepRecipeDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext().getApplicationContext()));

                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<RecipeDetail> call, Throwable t) {

            }
        });
    }
}
