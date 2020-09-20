package com.adida.dailycook.Upload.Fragment.Upload.TagUploadRecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;

import java.util.List;

public class TagUploadRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> list;

    public TagUploadRecyclerViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tag_upload_recyclerview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final ItemViewHolder holder = (ItemViewHolder) viewHolder;

        final String model = list.get(position);
        holder.title.setText(model);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitleTagRecyclerView);
        }
    }

}
