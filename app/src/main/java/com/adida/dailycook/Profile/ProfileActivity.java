package com.adida.dailycook.Profile;

import android.os.Bundle;
import android.text.Layout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    CircleImageView imgUserAvatar;
    RecyclerView listUploadRecipes;
    RecipeProfileAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imgUserAvatar = findViewById(R.id.imgUserAvatar);
        listUploadRecipes = findViewById(R.id.listUploadRecipe);

        initUI();
    }

    private void initUI(){

        Picasso.get().load("https://i.pinimg.com/236x/25/8d/75/258d75c4e1fed2b2eaa8a3c5854df1ac.jpg")
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.color.colorBlack).into(imgUserAvatar);

        listUploadRecipes.setHasFixedSize(true);
        adapter = new RecipeProfileAdapter();
        listUploadRecipes.setAdapter(adapter);
    }

}
