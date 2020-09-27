package com.adida.dailycook.Main.Fragment.Home.RecipeHomeFragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeHomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int ITEMS_LIMIT = 10;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<RecipeDetail> itemList;
    private RecyclerView recyclerView;
    private boolean isItemLoading = false;
    private int page = 0;
    private RecipeHomeRecyclerViewAdapter recyclerViewAdapter;


    public RecipeHomeFragment() {
    }

    public static RecipeHomeFragment newInstance(String param1, String param2) {
        RecipeHomeFragment fragment = new RecipeHomeFragment();
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
        View view = inflater.inflate(R.layout.recipehome_fragment, container, false);
        if (itemList == null)
            itemList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewRecipeHomepage);
        recyclerViewAdapter = new RecipeHomeRecyclerViewAdapter(getActivity(), itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        getHomeData();
        return view;
    }

    private void getHomeData() {
        ServiceManager.getInstance().getRecipeService().getAllRecipe(page, SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).get(Config.SHARED_PREFERENCES.USER.ID, Integer.class)).enqueue(new Callback<List<RecipeDetail>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<RecipeDetail>> call, Response<List<RecipeDetail>> response) {
                if (response.body() != null) {
                    if (page == 0) {
                        itemList.addAll(response.body());
                        recyclerViewAdapter.notifyDataSetChanged();
                        initScrollListener();
                    } else {
                        itemList.addAll(response.body());
                        recyclerViewAdapter.notifyDataSetChanged();
                        isItemLoading = false;
                    }
                    page += 1;
                } else
                    recyclerView.clearOnScrollListeners();
            }

            @Override
            public void onFailure(Call<List<RecipeDetail>> call, Throwable t) {

            }
        });
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
        recyclerView.post(new Runnable() {
            public void run() {
                recyclerViewAdapter.notifyItemInserted(itemList.size() - 1);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            getHomeData();
        }, 2000);
    }
}