package com.adida.dailycook.Main.Fragment.Search;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.Main.Fragment.Home.RecipeHomeFragment.RecipeHomeRecyclerview.RecipeHomeRecyclerViewAdapter;
import com.adida.dailycook.R;
import com.adida.dailycook.SharedPreference.SharedPreference;
import com.adida.dailycook.config.Config;
import com.adida.dailycook.retrofit2.ServiceManager;
import com.adida.dailycook.retrofit2.entities.RecipeDetail;

import java.util.List;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SearchFragment extends Fragment {

    EditText searchEditText;
    ImageView searchImageView;
    List<RecipeDetail> recipeList;
    RecipeHomeRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    int page = 0;
    Boolean isItemLoading = false;
    AlertDialog dialog;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        dialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setTheme(R.style.SpotsDialog)
                .build();

        recyclerView = view.findViewById(R.id.recyclerViewSearch);

        searchEditText = view.findViewById(R.id.editTextSearchView);
        searchEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        searchEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchImageView = view.findViewById(R.id.imageViewSearch);
        searchImageView.setOnClickListener(v -> {
            searchEditText.clearFocus();
            hideKeyBoard();
            if (!searchEditText.getText().toString().isEmpty()) {
                page = 0;
                LoadData();
            }
        });

        view.findViewById(R.id.search).setOnTouchListener((v, event) -> {
            hideKeyBoard();
            searchEditText.clearFocus();
            searchEditText.clearAnimation();
            return true;
        });

        view.findViewById(R.id.linearLayoutResultSearch).setOnTouchListener((v, event) -> {
            hideKeyBoard();
            searchEditText.clearFocus();
            return true;
        });

        return view;
    }

    private void LoadData() {
        if (page == 0)
            dialog.show();
        ServiceManager.getInstance().getRecipeService()
                .searchRecipe(page, SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).get(Config.SHARED_PREFERENCES.USER.ID, Integer.class), searchEditText.getText().toString())
                .enqueue(new Callback<List<RecipeDetail>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<RecipeDetail>> call, Response<List<RecipeDetail>> response) {
                        if (page == 0) {
                            recipeList = response.body();
                            setRecyclerViewAdapter();
                            dialog.dismiss();
                        } else {
                            if (response.body() != null) {
                                recipeList.remove(recipeList.size() - 1);

                                int scrollPosition = recipeList.size();
                                recyclerViewAdapter.notifyItemRemoved(scrollPosition);

                                recipeList.addAll(response.body());

                                recyclerViewAdapter.notifyDataSetChanged();
                                isItemLoading = false;
                            }
                        }

                        if (response.code() == 403) {
                            if (recipeList == null)
                                Toast.makeText(getContext(), "Không tìm thấy công thức trùng khớp", Toast.LENGTH_SHORT).show();
                            else {
                                recipeList.remove(recipeList.size() - 1);
                                int scrollPosition = recipeList.size();
                                recyclerViewAdapter.notifyItemRemoved(scrollPosition);
                                recyclerViewAdapter.notifyDataSetChanged();
                                isItemLoading = false;

                                recyclerView.clearOnScrollListeners();
                            }

                        } else {
                            page += 1;
                        }

                    }

                    @Override
                    public void onFailure(Call<List<RecipeDetail>> call, Throwable t) {
                    }
                });
    }

    private void setRecyclerViewAdapter() {
        recyclerViewAdapter = new RecipeHomeRecyclerViewAdapter(getContext(), recipeList);
        recyclerView.setAdapter(recyclerViewAdapter);
        initScrollListener();
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (linearLayoutManager != null) {
                    if (!isItemLoading) {
                        if (linearLayoutManager.findLastVisibleItemPosition() == recipeList.size() - 1) {
                            loadMore();
                            isItemLoading = true;
                        }
                    }
                }
            }
        });
    }

    public void loadMore() {
        recipeList.add(null);
        recyclerViewAdapter.notifyItemInserted(recipeList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(this::LoadData, 2000);
    }

    public void hideKeyBoard() {
        try {
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getActivity().getCurrentFocus()).getWindowToken(), 0);
        } catch (Exception ignored) {

        }
    }
}
