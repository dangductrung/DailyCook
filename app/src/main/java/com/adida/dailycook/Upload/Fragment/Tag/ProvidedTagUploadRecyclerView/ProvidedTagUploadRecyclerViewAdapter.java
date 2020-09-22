package com.adida.dailycook.Upload.Fragment.Tag.ProvidedTagUploadRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.adida.dailycook.Upload.Fragment.Tag.SelectedTagUploadRecyclerview.SelectedTagUploadRecyclerViewAdapter;
import com.adida.dailycook.retrofit2.entities.Tag;

import java.util.List;

public class ProvidedTagUploadRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Tag> list;
    private ProvidedTagUploadListener callback;

    public ProvidedTagUploadRecyclerViewAdapter(Context context, List<Tag> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.provided_tag_upload_recyclerview, parent, false);
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
                callback.selectTag(list.get(position));
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
            title = itemView.findViewById(R.id.textViewTitleProvidedTagUploadRecyclerView);
            imageButton = itemView.findViewById(R.id.imageButtonTagAddingProvidedTagUploadRecyclerView);
        }
    }

    public interface ProvidedTagUploadListener {
        public void selectTag(Tag tag);
    }

    public void setOnShareClickedListener(ProvidedTagUploadRecyclerViewAdapter.ProvidedTagUploadListener callback) {
        this.callback = callback;
    }

}
