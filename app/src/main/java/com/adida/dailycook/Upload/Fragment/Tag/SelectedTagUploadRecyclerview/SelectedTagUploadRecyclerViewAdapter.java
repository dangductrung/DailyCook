package com.adida.dailycook.Upload.Fragment.Tag.SelectedTagUploadRecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.adida.dailycook.Upload.Fragment.Upload.StepUploadRecyclerView.StepUploadRecyclerViewAdapter;
import com.adida.dailycook.retrofit2.entities.Tag;

import java.util.List;

public class SelectedTagUploadRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Tag> list;
    private SelectedTagUploadListener callback;

    public SelectedTagUploadRecyclerViewAdapter(Context context, List<Tag> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.selected_tag_upload_recyclerview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final ItemViewHolder holder = (ItemViewHolder) viewHolder;

        final Tag model = list.get(position);
        holder.title.setText(model.getTitle());
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.deselectTag(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageButton imageButton;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitleSelectedTagUploadRecyclerView);
            imageButton = itemView.findViewById(R.id.imageButtonTagRemovingSelectedTagUploadRecyclerView);
        }
    }

    public interface SelectedTagUploadListener {
        public void deselectTag(Tag tag);
    }

    public void setOnShareClickedListener(SelectedTagUploadRecyclerViewAdapter.SelectedTagUploadListener callback) {
        this.callback = callback;
    }

}
