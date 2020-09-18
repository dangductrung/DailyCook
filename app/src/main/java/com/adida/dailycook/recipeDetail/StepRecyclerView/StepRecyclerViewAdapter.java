package com.adida.dailycook.recipeDetail.StepRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.adida.dailycook.R;
import com.adida.dailycook.retrofit2.entities.RecipeStep;

import java.util.List;

public class StepRecyclerViewAdapter extends RecyclerView.Adapter<StepRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<RecipeStep> models;

    public StepRecyclerViewAdapter(Context context, List<RecipeStep> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View stepView = inflater.inflate(R.layout.step_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(stepView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeStep model = models.get(position);

        holder.orderTextView.setText(Integer.toString(model.getStep_Order()));
        holder.descriptionTextView.setText(model.getStep_description());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView orderTextView;
        private TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderTextView = itemView.findViewById(R.id.textViewStepOrder);
            descriptionTextView = itemView.findViewById(R.id.textViewStepDescription);
        }
    }
}

