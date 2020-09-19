package com.adida.dailycook.Main.Fragment.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.adida.dailycook.retrofit2.ServiceManager;
import com.adida.dailycook.retrofit2.entities.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeProfileAdapter extends RecyclerView.Adapter<RecipeProfileAdapter.RecipeViewHolder> {

    public ArrayList<Recipe> m_recipeList = null;

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

        Recipe recipe = m_recipeList.get(position);

        Picasso.get().load(m_recipeList.indexOf(recipe.getImageUrl()))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.color.colorBlack)
                .into(holder.foodPortrait);
        holder.txtFoodName.setText(recipe.getRecipeName());
        holder.txtFoodFavorite.setText(recipe.getRating()+"");
        holder.txtFoodView.setText("0");

    }

    @Override
    public int getItemCount() {
        return m_recipeList == null ? 0 : m_recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{

        private ImageView foodPortrait;
        private TextView txtFoodName;
        private TextView txtFoodView;
        private TextView txtFoodFavorite;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            foodPortrait = itemView.findViewById(R.id.imgFoodItemRecipeUpload);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtFoodView = itemView.findViewById(R.id.txtFoodView);
            txtFoodFavorite = itemView.findViewById(R.id.txtFoodFavorite);
        }
    }

}
