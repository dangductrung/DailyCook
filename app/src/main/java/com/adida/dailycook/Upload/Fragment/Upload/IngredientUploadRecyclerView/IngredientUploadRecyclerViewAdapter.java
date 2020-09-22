package com.adida.dailycook.Upload.Fragment.Upload.IngredientUploadRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;

import java.util.List;

public class IngredientUploadRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> list;

    public IngredientUploadRecyclerViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_upload_recyclerview, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {


        final ItemViewHolder holder = (ItemViewHolder) viewHolder;

        final String model = list.get(position);
        holder.description.setText(model);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private ImageButton remove;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.textViewDescriptionIngredientUploadRecyclerView);
            remove = itemView.findViewById(R.id.imageButtonRemoveIngredientUploadRecyclerView);
        }
    }

}
