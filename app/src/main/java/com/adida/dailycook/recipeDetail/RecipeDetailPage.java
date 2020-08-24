package com.adida.dailycook.recipeDetail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.adida.dailycook.R;

public class RecipeDetailPage extends AppCompatActivity {
    ImageButton backButton, likeButton;
    ImageView recipeImage;
    TextView credientRecipeDetail, stepRecipeDetail;
    Button startButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        backButton = (ImageButton)findViewById(R.id.btnBackButton);
        likeButton = (ImageButton)findViewById(R.id.btnLikeButton);
        recipeImage = (ImageView)findViewById(R.id.RecipeImage);
        credientRecipeDetail = (TextView)findViewById(R.id.CredientRecipeDetail);
        stepRecipeDetail = (TextView)findViewById(R.id.StepRecipeDetail);
        startButton = (Button)findViewById(R.id.startButton);

        setVariable();
        addAction();
    }

    private void setVariable(){

    }

    private void addAction(){
        backButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                didTapBackButton();
            }
        });

        likeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                didTapLikeButton();
            }
        });

        startButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                didTapStartButton();
            }
        });
    }

    // Action
    private void didTapBackButton(){
    }

    private void didTapLikeButton(){
    }

    private void didTapStartButton(){
    }
}
