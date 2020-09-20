package com.adida.dailycook.Main.Fragment.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.adida.dailycook.Main.Fragment.Home.RecipeHomeFragment.RecipeHomeFragment;
import com.adida.dailycook.PagerAdapter.PagerAdapter;
import com.adida.dailycook.R;
import com.adida.dailycook.SharedPreference.SharedPreference;
import com.adida.dailycook.config.Config;
import com.adida.dailycook.login.LoginPage;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    private View view;

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private TabLayout tabLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewPagerHomePage);
        tabLayout = view.findViewById(R.id.tabLayoutHomepage);

        ImageButton signOutImageButton = view.findViewById(R.id.imageButtonSignoutHomepage);
        signOutImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).clear();
                startActivity(new Intent(getActivity(), LoginPage.class));
                getActivity().finish();
            }
        });

        LoadHomepageData();

        setView();

        return view;
    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(pagerAdapter.getSelectedTabView(position));
    }

    @SuppressLint("SimpleDateFormat")
    private void LoadHomepageData() {
        TextView userTextView = view.findViewById(R.id.textViewFirstNameHomepage);
        userTextView.setText(SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).get(Config.SHARED_PREFERENCES.USER.NAME, String.class));

        TextView dateTextView = view.findViewById(R.id.textViewDateHomepage);
        dateTextView.setText(new SimpleDateFormat("MM/dd/YYYY").format(Calendar.getInstance().getTime()));
    }

    private void setView() {
        pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), getContext());
        pagerAdapter.addFragment(new RecipeHomeFragment(), "Soup");
        pagerAdapter.addFragment(new RecipeHomeFragment(), "Đồ chiên");
        pagerAdapter.addFragment(new RecipeHomeFragment(), "Đồ Hàn");
        pagerAdapter.addFragment(new RecipeHomeFragment(), "Đồ Âu");
        pagerAdapter.addFragment(new RecipeHomeFragment(), "Đồ Á");
        pagerAdapter.addFragment(new RecipeHomeFragment(), "Đồ Pháp");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        highLightCurrentTab(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}