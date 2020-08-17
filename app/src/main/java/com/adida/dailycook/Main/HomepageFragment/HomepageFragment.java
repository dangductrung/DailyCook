package com.adida.dailycook.Main.HomepageFragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.Main.HomepageFragment.HomepageRecyclerView.HomepageModel;
import com.adida.dailycook.Main.HomepageFragment.HomepageRecyclerView.HomepageRecyclerViewAdapter;
import com.adida.dailycook.R;

import java.util.ArrayList;
import java.util.List;


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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<HomepageModel> homepageModelList;
    private final int ITEMS_LIMIT = 10;

    private RecyclerView homepageRecyclerView;
    private boolean isItemLoading = false;
    private int page = 0;
    private HomepageRecyclerViewAdapter homepageRecyclerViewAdapter;


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
        homepageRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewRecipeHomepage);
        getHomeData();
        return view;
    }

    private void getHomeData(){
        homepageModelList = new ArrayList<HomepageModel>();
        homepageModelList.add(new HomepageModel("Phở Hà Nội Tại Gia", "Nguyễn Phi Thăng"));
        homepageModelList.add(new HomepageModel("Phở Hà Nội Tại Quán", "Nguyễn Thăng Thiên"));
        homepageModelList.add(new HomepageModel("Phở Hà Nội Tại Tiệm", "Nguyễn Thăng Hoa"));
        homepageModelList.add(new HomepageModel("Phở Hà Nội Tại Trường", "Nguyễn Phi Thiên"));
        homepageModelList.add(new HomepageModel("Phở Hà Nội Tại Ga", "Nguyễn Phi Phàm"));

        setRecipeHomeAdapter();
    }

    private void setRecipeHomeAdapter() {
        homepageRecyclerViewAdapter = new HomepageRecyclerViewAdapter(getActivity(), homepageModelList);
        homepageRecyclerView.setAdapter(homepageRecyclerViewAdapter);
    }

    private void initScrollListener(){
        homepageRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if(linearLayoutManager != null){
                    if(!isItemLoading){
                        if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == homepageModelList.size() - 1){
                            loadMore();
                            isItemLoading = true;
                        }
                    }
                }
            }
        });
    }

    public void loadMore(){
        homepageModelList.add(null);
        homepageRecyclerViewAdapter.notifyItemInserted(homepageModelList.size() - 1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                homepageModelList.remove(homepageModelList.size() - 1);
                int scrollPosition = homepageModelList.size();
                homepageRecyclerViewAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + homepageRecyclerViewAdapter.getItemLimit();

                while (currentSize - 1 < nextLimit) {
                    getHomeData();

                    currentSize++;
                }

                homepageRecyclerViewAdapter.notifyDataSetChanged();
                isItemLoading = false;
            }
        }, 2000);
    }
}