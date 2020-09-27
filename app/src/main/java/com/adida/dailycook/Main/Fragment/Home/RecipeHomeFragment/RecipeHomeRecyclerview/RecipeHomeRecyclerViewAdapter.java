package com.adida.dailycook.Main.Fragment.Home.RecipeHomeFragment.RecipeHomeRecyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.adida.dailycook.recipeDetail.RecipeDetailPage;
import com.adida.dailycook.retrofit2.entities.RecipeDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeHomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_ITEM_LOADING = 1;
    private final int ITEMS_LIMIT = 10;

    private Context context;
    private List<RecipeDetail> list;

    public RecipeHomeRecyclerViewAdapter(Context context, List<RecipeDetail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.recipe_recyclerview, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {
            final ItemViewHolder holder = (ItemViewHolder) viewHolder;

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RecipeDetailPage.class);
                    intent.putExtra("id", list.get(holder.getPosition()).getRecipe().getRecipeId());
                    view.getContext().startActivity(intent);
                }
            });

            final RecipeDetail currentRecipe = list.get(position);
            holder.recipeName.setText(currentRecipe.getRecipe().getRecipeName());
            holder.uploader.setText(currentRecipe.getChef().getName());
            try {
                Picasso.get().setLoggingEnabled(true);
                Picasso.get().load(currentRecipe.getRecipe().getImageUrl()).error(R.drawable.ic_error).placeholder(R.drawable.ic_placeholder).into(holder.recipeImage);
            }catch (Exception e) {
                holder.recipeImage.setImageAlpha(R.drawable.ic_error);
            }

        } else if (viewHolder instanceof LoadingViewHolder) {
            LoadingViewHolder holder = (LoadingViewHolder) viewHolder;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_ITEM_LOADING : VIEW_TYPE_ITEM;
    }


    public int getItemLimit() {
        return this.ITEMS_LIMIT;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView recipeImage;
        private TextView recipeName;
        private TextView uploader;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.imageViewRecipeHomepage);
            recipeName = itemView.findViewById(R.id.textviewRecipeNameHomepage);
            uploader = itemView.findViewById(R.id.textviewUploaderHomepage);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar prbarItemLoading;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            prbarItemLoading = itemView.findViewById(R.id.prbarItemLoading);
        }
    }

}
