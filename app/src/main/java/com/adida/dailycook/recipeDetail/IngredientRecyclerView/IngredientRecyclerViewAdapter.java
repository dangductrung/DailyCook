package com.adida.dailycook.recipeDetail.IngredientRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.adida.dailycook.R;
import com.adida.dailycook.retrofit2.entities.Ingredient;

import java.util.List;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Ingredient> models;

    public IngredientRecyclerViewAdapter(Context context, List<Ingredient> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View ingredientView = inflater.inflate(R.layout.ingredient_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(ingredientView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient model = models.get(position);
        holder.description.setText(model.getIngredient());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.textViewIngredientContext);
        }
    }
}

