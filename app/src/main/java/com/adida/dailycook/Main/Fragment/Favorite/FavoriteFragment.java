package com.adida.dailycook.Main.Fragment.Favorite;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.adida.dailycook.R;
import com.adida.dailycook.SharedPreference.SharedPreference;
import com.adida.dailycook.config.Config;
import com.adida.dailycook.retrofit2.ServiceManager;
import com.adida.dailycook.retrofit2.entities.RecipeDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int ITEM_LIMIT = 10;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<RecipeDetail> itemList;
    private RecyclerView recyclerView;
    private TextView txtEmptyFavorite;
    private boolean isItemLoading = false;
    private int page = 0;
    private RecipeFavoriteAdapter adapter;


    public FavoriteFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.viewFavoriteRecipes);
        txtEmptyFavorite = view.findViewById(R.id.txtEmptyFavorite);
        getFavoriteData();
        return view;
    }

    private void getFavoriteData() {
        ServiceManager.getInstance().getFavoriteService().
                getAllFavoriteRecipe(page, SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME)
                .get(Config.SHARED_PREFERENCES.USER.ID, Integer.class)).enqueue(new Callback<List<RecipeDetail>>() {
            @Override
            public void onResponse(Call<List<RecipeDetail>> call, Response<List<RecipeDetail>> response) {
                if (page == 0) {
                    itemList = response.body();
                    initScrollListener();

                    if(itemList.size() == 0){
                        recyclerView.setVisibility(View.GONE);
                        txtEmptyFavorite.setVisibility(View.VISIBLE);
                    }
                    else {
                        recyclerView.setVisibility(View.VISIBLE);
                        txtEmptyFavorite.setVisibility(View.GONE);
                    }

                    setRecipeFavoriteAdapter();
                } else {
                    if (response.body() != null) {
                        itemList.addAll(response.body());
                        adapter.notifyDataSetChanged();
                        isItemLoading = false;
                    }
                }

                if (response.body() == null) {
                    recyclerView.clearOnScrollListeners();
                } else {
                    page += 1;
                }
            }

            @Override
            public void onFailure(Call<List<RecipeDetail>> call, Throwable t) {

            }
        });
    }

    private void setRecipeFavoriteAdapter() {
        adapter = new RecipeFavoriteAdapter(getActivity(), itemList);
        recyclerView.setAdapter(adapter);
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

                if (!isItemLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == itemList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isItemLoading = true;
                    }
                }
            }
        });
    }

    public void loadMore() {
        itemList.add(null);
        adapter.notifyItemInserted(itemList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                itemList.remove(itemList.size() - 1);
                adapter.notifyItemRemoved(itemList.size());

                getFavoriteData();
            }
        }, 2000);
    }
}