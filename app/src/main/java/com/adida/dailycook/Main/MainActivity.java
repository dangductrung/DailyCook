package com.adida.dailycook.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.adida.dailycook.Main.Fragment.Home.HomeFragment;
import com.adida.dailycook.Main.Fragment.Profile.ProfileFragment;
import com.adida.dailycook.R;
import com.adida.dailycook.config.Config;
import com.adida.dailycook.helpers.BottomNavigationBehavior;
import com.adida.dailycook.login.LoginPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Intent loginIntent;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginIntent = new Intent(this, LoginPage.class);
        if (getSharedPreferences(Config.SHARED_PREFERENCES.USER.SP_NAME, MODE_PRIVATE).getAll().isEmpty()) {
            startActivity(loginIntent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);

        loadFragment(new HomeFragment());

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_homepage:
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_dinning:
                        return true;
                    case R.id.navigation_search:
                        return true;
                    case R.id.navigation_favorite:
                        return true;
                    case R.id.navigation_profile:
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutMain, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}