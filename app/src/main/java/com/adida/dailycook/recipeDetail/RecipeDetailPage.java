package com.adida.dailycook.recipeDetail;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.Main.Fragment.Favorite.RecipeFavoriteAdapter;
import com.adida.dailycook.R;
import com.adida.dailycook.SharedPreference.SharedPreference;
import com.adida.dailycook.config.Config;
import com.adida.dailycook.recipeDetail.IngredientRecyclerView.IngredientRecyclerViewAdapter;
import com.adida.dailycook.recipeDetail.StepRecyclerView.StepRecyclerViewAdapter;
import com.adida.dailycook.recipeSteps.RecipeSteps;
import com.adida.dailycook.retrofit2.ServiceManager;
import com.adida.dailycook.retrofit2.entities.RecipeDetail;
import com.adida.dailycook.retrofit2.entities.RecipeDetailSteps;
import com.adida.dailycook.retrofit2.entities.RecipeStep;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailPage extends AppCompatActivity {
    private int recipeID;
    ImageButton backButton, likeButton;
    ImageView recipeImage;
    RecyclerView ingredientRecipeDetail , stepRecipeDetail;
    TextView recipeTitle, descriptionRecipe;
    Button startButton;
    RecipeDetailSteps detail;
    ProgressDialog progressDialog;

    Boolean favorite = false;

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
        descriptionRecipe = (TextView)findViewById(R.id.CredientRecipeInformation);
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

    private void updateRecipe(){
        HashMap<String, Object> params = new HashMap<>();

        params.put("user_id", SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).get(Config.SHARED_PREFERENCES.USER.ID, Integer.class));
        params.put("recipe_id", detail.getRecipe().getRecipeId());

        if (favorite == true) {
            ServiceManager.getInstance().getFavoriteService().addToFavorite(params).enqueue(new Callback<Map<String, String>>() {

                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {

                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ServiceManager.getInstance().getFavoriteService().delete(params).enqueue(new Callback<Map<String, String>>() {

                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    // Action
    private void didTapBackButton() {
        finish();
    }

    private void didTapLikeButton() {
        favorite = !favorite;

        if (favorite == true) {
            likeButton.setBackgroundResource(R.mipmap.like_button);
        } else {
            likeButton.setBackgroundResource(R.mipmap.nolike_button);
        }

        updateRecipe();

    }

    private void didTapStartButton() {
        RecipeSteps.startActivity(this,detail);
    }

    private void LoadData() {
        ServiceManager.getInstance().getRecipeService().getRecipeSteps(recipeID, SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).get(Config.SHARED_PREFERENCES.USER.ID, Integer.class)).enqueue(new Callback<RecipeDetailSteps>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<RecipeDetailSteps> call, Response<RecipeDetailSteps> response) {
                if (response.body() != null) {
                    detail = response.body();
                    recipeTitle.setText(detail.getRecipe().getRecipeName());

                    try {
                        Picasso.get().setLoggingEnabled(true);
                        Picasso.get().load(detail.getRecipe().getImageUrl()).error(R.drawable.ic_error).placeholder(R.drawable.ic_placeholder).into(recipeImage);
                    } catch (Exception e) {
                        recipeImage.setImageAlpha(R.drawable.ic_error);
                    }

                    ingredientRecipeDetail.setAdapter(new IngredientRecyclerViewAdapter(getApplicationContext(), detail.getIngredients()));
                    ingredientRecipeDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext().getApplicationContext()));

                    stepRecipeDetail.setAdapter(new StepRecyclerViewAdapter(getApplicationContext(), detail.getSteps()));
                    stepRecipeDetail.setLayoutManager(new LinearLayoutManager(getApplicationContext().getApplicationContext()));

                    descriptionRecipe.setText(detail.getRecipe().getDescription());

                    favorite = detail.getFavorite();
                    if (favorite == true) {
                        likeButton.setBackgroundResource(R.mipmap.like_button);
                    } else {
                        likeButton.setBackgroundResource(R.mipmap.nolike_button);
                    }

                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<RecipeDetailSteps> call, Throwable t) {

            }
        });
    }
}
