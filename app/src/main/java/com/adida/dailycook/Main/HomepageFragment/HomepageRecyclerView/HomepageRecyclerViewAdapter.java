package com.adida.dailycook.Main.HomepageFragment.HomepageRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;

import java.util.List;

public class HomepageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_ITEM_LOADING = 1;
    private final int ITEMS_LIMIT = 10;

    private Context m_context;
    private List<HomepageModel> m_recipeList;

    public HomepageRecyclerViewAdapter(Context context, List<HomepageModel> recipeList){
        m_context = context;
        m_recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(m_context).inflate(R.layout.homepage_recyclerview, parent, false);
            return new ItemViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(m_context).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof ItemViewHolder){
            ItemViewHolder holder = (ItemViewHolder) viewHolder;

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            final HomepageModel currentRecipe = m_recipeList.get(position);
            holder.recipeName.setText(currentRecipe.getRecipeName());
            holder.uploader.setText(currentRecipe.getUsername());

//        Picasso.get().setLoggingEnabled(true);
            //Picasso.get().load(currentRecipe.getRecipe().getImageUrl()).error(R.drawable.ic_error).placeholder(R.drawable.ic_placeholder).into(holder.foodPortrait);
            //Picasso.get().load(currentRecipe.getChef().getAvatar()).error(R.drawable.ic_error).placeholder(R.drawable.ic_placeholder).into(holder.userAvatar);


        }
        else if(viewHolder instanceof LoadingViewHolder){
            LoadingViewHolder holder = (LoadingViewHolder) viewHolder;
        }
    }

    @Override
    public int getItemCount() {
//        return m_recipeList == null ? 0 : m_recipeList.size() > ITEMS_LIMIT ? ITEMS_LIMIT : m_recipeList.size();
        return m_recipeList == null ? 0 : m_recipeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return m_recipeList.get(position) == null ? VIEW_TYPE_ITEM_LOADING : VIEW_TYPE_ITEM;
    }


    public int getItemLimit(){
        return this.ITEMS_LIMIT;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
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

    private class LoadingViewHolder extends RecyclerView.ViewHolder{

        private ProgressBar prbarItemLoading;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            prbarItemLoading = itemView.findViewById(R.id.prbarItemLoading);
        }
    }

}
