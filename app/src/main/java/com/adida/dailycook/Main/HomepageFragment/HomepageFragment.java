package com.adida.dailycook.Main.HomepageFragment;

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

import com.adida.dailycook.Main.HomepageFragment.HomepageRecyclerView.HomepageRecyclerViewAdapter;
import com.adida.dailycook.R;
import com.adida.dailycook.retrofit2.ServiceManager;
import com.adida.dailycook.retrofit2.entities.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomepageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomepageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int ITEMS_LIMIT = 10;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Recipe> itemList;
    private RecyclerView recyclerView;
    private boolean isItemLoading = false;
    private int page = 0;
    private HomepageRecyclerViewAdapter recyclerViewAdapter;


    public HomepageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomepageFragment newInstance(String param1, String param2) {
        HomepageFragment fragment = new HomepageFragment();
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
        View view = inflater.inflate(R.layout.homepage_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewRecipeHomepage);
        getHomeData();
        return view;
    }

    private void getHomeData() {
        ServiceManager.getInstance().getRecipeService().getAllRecipes(page).enqueue(new Callback<List<Recipe>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (page == 0) {
                    itemList = response.body();
                    initScrollListener();

                    setRecipeHomeAdapter();
                } else {
                    if (response.body() != null) {
                        itemList.addAll(response.body());
                        recyclerViewAdapter.notifyDataSetChanged();
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
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }

    private void setRecipeHomeAdapter() {
        recyclerViewAdapter = new HomepageRecyclerViewAdapter(getActivity(), itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
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
        recyclerViewAdapter.notifyItemInserted(itemList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                itemList.remove(itemList.size() - 1);
                recyclerViewAdapter.notifyItemRemoved(itemList.size());

                getHomeData();
            }
        }, 2000);
    }
}