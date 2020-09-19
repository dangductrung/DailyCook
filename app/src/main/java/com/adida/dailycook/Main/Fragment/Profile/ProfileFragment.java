package com.adida.dailycook.Main.Fragment.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adida.dailycook.R;
import com.adida.dailycook.SharedPreference.SharedPreference;
import com.adida.dailycook.config.Config;
import com.adida.dailycook.retrofit2.ServiceManager;
import com.adida.dailycook.retrofit2.entities.RecipeDetail;
import com.adida.dailycook.retrofit2.entities.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private CircleImageView imgUserAvatar;
    private RecyclerView listUploadRecipes;
    private RecipeProfileAdapter adapter;
    private TextView txtUserName;
    private TextView txtUserEmail;
    private TextView txtUserPhone;
    private Toolbar toolbar;
    private View view;
    private User user;



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
        txtUserName = view.findViewById(R.id.txtUserName);
        txtUserEmail = view.findViewById(R.id.txtUserEmail);
        txtUserPhone = view.findViewById(R.id.txtUserPhone);
        toolbar = view.findViewById(R.id.toolbarProfile);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.menu_profile);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //
                return false;
            }
        });

        loadUserData();

        return view;
    }

    private void loadUserData() {

        ServiceManager.getInstance().getUserService()
                .getUserByID(SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).getUserId(Config.SHARED_PREFERENCES.USER.ID)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                setData();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    private void setData(){
//        Picasso.get().load(user.getAvatar())
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.color.colorBlack).into(imgUserAvatar);
//        txtUserName.setText(user.getName());
//        txtUserEmail.setText(user.getEmail());
//        adapter = new RecipeProfileAdapter();
//        loadUploadData();
    }

    private void loadUploadData(){
        ServiceManager.getInstance().getRecipeService().getManagedRecipe(0, user.getId()).enqueue(new Callback<List<RecipeDetail>>() {
            @Override
            public void onResponse(Call<List<RecipeDetail>> call, Response<List<RecipeDetail>> response) {
                List<RecipeDetail> list = response.body();
                for(RecipeDetail recipeDetail : list) {
                    adapter.m_recipeList.add(recipeDetail.getRecipe());
                }
                listUploadRecipes.setHasFixedSize(true);
                listUploadRecipes.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<RecipeDetail>> call, Throwable t) {

            }
        });
    }
}