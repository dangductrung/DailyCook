package com.adida.dailycook.Upload.Fragment.Tag;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.adida.dailycook.Upload.Fragment.Tag.ProvidedTagUploadRecyclerView.ProvidedTagUploadRecyclerViewAdapter;
import com.adida.dailycook.Upload.Fragment.Tag.SelectedTagUploadRecyclerview.SelectedTagUploadRecyclerViewAdapter;
import com.adida.dailycook.Upload.ViewModel.UploadViewModel;
import com.adida.dailycook.retrofit2.ServiceManager;
import com.adida.dailycook.retrofit2.entities.Tag;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagFragment extends Fragment implements ProvidedTagUploadRecyclerViewAdapter.ProvidedTagUploadListener, SelectedTagUploadRecyclerViewAdapter.SelectedTagUploadListener {
    private List<Tag> provided;
    private List<Tag> selected;
    private ProvidedTagUploadRecyclerViewAdapter providedTagUploadRecyclerViewAdapter;
    private SelectedTagUploadRecyclerViewAdapter selectedTagUploadRecyclerViewAdapter;
    private UploadViewModel model;
    private AlertDialog dialog;

    public TagFragment() {
        // Required empty public constructor
    }

    public static TagFragment newInstance(String param1, String param2) {
        TagFragment fragment = new TagFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.SpotsDialog)
                .build();
        dialog.show();
        View view = inflater.inflate(R.layout.fragment_tag_upload, container, false);

        RecyclerView providedTagRecyclerView = view.findViewById(R.id.recyclerViewProvidedTagUploadFragment);
        FlexboxLayoutManager providedLayoutManager = new FlexboxLayoutManager(getContext());
        providedLayoutManager.setFlexWrap(FlexWrap.WRAP);
        providedLayoutManager.setFlexDirection(FlexDirection.ROW);
        providedLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        providedTagRecyclerView.setLayoutManager(providedLayoutManager);

        RecyclerView selectedTagRecyclerView = view.findViewById(R.id.recyclerViewSelectedTagUploadFragment);
        FlexboxLayoutManager selectedLayoutManager = new FlexboxLayoutManager(getContext());
        selectedLayoutManager.setFlexWrap(FlexWrap.WRAP);
        selectedLayoutManager.setFlexDirection(FlexDirection.ROW);
        selectedLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        selectedTagRecyclerView.setLayoutManager(selectedLayoutManager);

        provided = new ArrayList<>();
        selected = new ArrayList<>();

        model = new ViewModelProvider(requireActivity()).get(UploadViewModel.class);
        model.getTags().observe(getViewLifecycleOwner(), new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> data) {
                selected.clear();
                selected.addAll(data);
                selectedTagUploadRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        providedTagUploadRecyclerViewAdapter = new ProvidedTagUploadRecyclerViewAdapter(requireActivity(), provided);
        selectedTagUploadRecyclerViewAdapter = new SelectedTagUploadRecyclerViewAdapter(requireActivity(), selected);
        providedTagUploadRecyclerViewAdapter.setOnShareClickedListener(this);
        selectedTagUploadRecyclerViewAdapter.setOnShareClickedListener(this);

        providedTagRecyclerView.setAdapter(providedTagUploadRecyclerViewAdapter);
        selectedTagRecyclerView.setAdapter(selectedTagUploadRecyclerViewAdapter);

        LoadData();

        return view;
    }

    private void LoadData() {
        ServiceManager.getInstance().getTagService().getAllTags().enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.body() != null) {
                    provided.addAll(response.body());

                    if (model.getTags().getValue() != null) {
                        for (Tag tag : selected) {
                            provided.remove(tag);
                        }
                    }

                    providedTagUploadRecyclerViewAdapter.notifyDataSetChanged();

                    dialog.dismiss();
                }
                else {
                    dialog.dismiss();
                    Toast.makeText(requireContext(), "Không nhận được dữ liệu từ máy chủ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(requireContext(), "Không thể kết nối đến máy chủ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void selectTag(Tag tag) {
        model.addTag(tag);
        provided.remove(tag);
        providedTagUploadRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void deselectTag(Tag tag) {
        provided.add(tag);
        model.removeTag(tag);
        providedTagUploadRecyclerViewAdapter.notifyDataSetChanged();
    }
}