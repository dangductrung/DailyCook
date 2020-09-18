package com.adida.dailycook.Main.Fragment.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    CircleImageView imgUserAvatar;
    RecyclerView listUploadRecipes;
    RecipeProfileAdapter adapter;
    private View view;


    public ProfileFragment() {
    }

    public static com.adida.dailycook.Main.Fragment.Home.HomeFragment newInstance(String param1, String param2) {
        com.adida.dailycook.Main.Fragment.Home.HomeFragment fragment = new com.adida.dailycook.Main.Fragment.Home.HomeFragment();
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        imgUserAvatar = view.findViewById(R.id.imgUserAvatar);
        listUploadRecipes = view.findViewById(R.id.listUploadRecipe);

        initUI();

        return view;
    }

    private void initUI() {

        Picasso.get().load("https://i.pinimg.com/236x/25/8d/75/258d75c4e1fed2b2eaa8a3c5854df1ac.jpg")
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.color.colorBlack).into(imgUserAvatar);

        listUploadRecipes.setHasFixedSize(true);
        adapter = new RecipeProfileAdapter();
        listUploadRecipes.setAdapter(adapter);
    }
}