package com.adida.dailycook.Upload.Fragment.Upload.StepUploadRecyclerView;

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

public class StepUploadRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<StepUploadModel> list;
    private StepUploadListener callback;

    public StepUploadRecyclerViewAdapter(Context context, List<StepUploadModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.step_upload_recyclerview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {


        final ItemViewHolder holder = (ItemViewHolder) viewHolder;

        final StepUploadModel model = list.get(position);
        holder.description.setText(model.getDescription());
        holder.order.setText(Integer.toString(position + 1));
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //list.remove(position);
                //notifyDataSetChanged();
                callback.removeStep(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private TextView order;
        private ImageButton remove;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.textViewStepDescriptionStepUploadRecyclerView);
            order = itemView.findViewById(R.id.textViewStepOrderStepUploadRecyclerView);
            remove = itemView.findViewById(R.id.imageButtonRemoveStepUploadRecyclerView);
        }
    }

    public interface StepUploadListener {
        public void removeStep(int index);
    }

    public void setOnShareClickedListener(StepUploadListener callback) {
        this.callback = callback;
    }
}

