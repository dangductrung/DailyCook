package com.adida.dailycook.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.squareup.picasso.Picasso;

public class RecipeProfileAdapter extends RecyclerView.Adapter<RecipeProfileAdapter.RecipeViewHolder> {

    String[] m_recipeList = new String[]{"1", "2", "3", "4"};

    public RecipeProfileAdapter(){
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_upload_profile, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        Picasso.get().load("https://daynghevietuc.com/wp-content/uploads/2017/05/TD14_09_Canh_Ga_Chien_Nuoc_Mam_0000x0000_0.jpg")
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.color.colorBlack)
                .into(holder.foodPortrait);


    }

    @Override
    public int getItemCount() {
        return m_recipeList == null ? 0 : m_recipeList.length;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{

        private ImageView foodPortrait;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            foodPortrait = itemView.findViewById(R.id.imgFoodItemRecipeUpload);
        }
    }
}
