package com.adida.dailycook.Main.Fragment.Favorite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.adida.dailycook.recipeDetail.RecipeDetailPage;
import com.adida.dailycook.retrofit2.entities.RecipeDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeFavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_ITEM_LOADING = 1;
    private final int ITEMS_LIMIT = 10;

    private List<RecipeDetail> m_itemList;
    private Context m_context;

    public RecipeFavoriteAdapter() {
        super();
    }

    public RecipeFavoriteAdapter(Context context, List<RecipeDetail> list) {
        m_context = context;
        m_itemList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(m_context).inflate(R.layout.item_recipe_favorite, parent, false);
            return new ItemRecipeViewHolder(view);
        } else {
            View view = LayoutInflater.from(m_context).inflate(R.layout.item_loading, parent, false);
            return new ItemLoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemRecipeViewHolder) {
            final ItemRecipeViewHolder viewHolder = (ItemRecipeViewHolder) holder;

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RecipeDetailPage.class);
                    intent.putExtra("id", m_itemList.get(viewHolder.getPosition()).getRecipe().getRecipeId());
                    view.getContext().startActivity(intent);
                }
            });

            final RecipeDetail currentRecipe = m_itemList.get(position);
            viewHolder.txtRecipeNameFavoritePage.setText(currentRecipe.getRecipe().getRecipeName());
            viewHolder.txtRecipeUploaderFavoritePage.setText(currentRecipe.getChef().getName());
            Picasso.get().load(currentRecipe.getRecipe().getImageUrl()).error(R.drawable.ic_error).placeholder(R.drawable.ic_placeholder)
                    .into(viewHolder.imgRecipeFavoritePage);

        } else if (holder instanceof ItemLoadingViewHolder) {
            ItemLoadingViewHolder viewHolder = (ItemLoadingViewHolder) holder;
        }
    }

    @Override
    public int getItemCount() {
        return m_itemList == null ? 0 : m_itemList.size();
    }

     public class ItemRecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRecipeFavoritePage;
        TextView txtRecipeNameFavoritePage;
        TextView txtRecipeUploaderFavoritePage;
        public ItemRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRecipeFavoritePage = itemView.findViewById(R.id.imgRecipeFavoritePage);
            txtRecipeNameFavoritePage = itemView.findViewById(R.id.txtRecipeNameFavoritePage);
            txtRecipeUploaderFavoritePage = itemView.findViewById(R.id.txtRecipeUploaderFavoritePage);
        }
    }

    public class ItemLoadingViewHolder extends RecyclerView.ViewHolder{

        public ItemLoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
