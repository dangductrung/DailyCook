package com.adida.dailycook.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.adida.dailycook.Main.HomepageFragment.HomepageFragment;
import com.adida.dailycook.PagerAdapter.PagerAdapter;
import com.adida.dailycook.R;
import com.adida.dailycook.SharedPreference.SharedPreference;
import com.adida.dailycook.config.Config;
import com.adida.dailycook.login.LoginPage;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Intent loginIntent;
    private PagerAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginIntent = new Intent(this, LoginPage.class);
        if(getSharedPreferences(Config.SHARED_PREFERENCES.USER.SP_NAME, MODE_PRIVATE).getAll().isEmpty()) {
            startActivity(loginIntent);
            finish();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setElevation(0);

        viewPager = findViewById(R.id.viewPagerHomePage);
        tabLayout = findViewById(R.id.tabLayoutHomepage);
        setView();


        ImageButton signOutImageButton = findViewById(R.id.imageButtonSignoutHomepage);
        signOutImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).clear();
                startActivity(loginIntent);
                finish();
            }
        });

        LoadHomepageData();
    }



    private void highLightCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(tabAdapter.getTabView(i));
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(tabAdapter.getSelectedTabView(position));
    }

    @SuppressLint("SimpleDateFormat")
    private void LoadHomepageData(){
        TextView userTextView = findViewById(R.id.textViewFirstNameHomepage);
        userTextView.setText(SharedPreference.getInstance(Config.SHARED_PREFERENCES.USER.SP_NAME).get(Config.SHARED_PREFERENCES.USER.NAME, String.class));

        TextView dateTextView = findViewById(R.id.textViewDateHomepage);
        dateTextView.setText(new SimpleDateFormat("MM/dd/YYYY").format(Calendar.getInstance().getTime()));
    }


    private void setView() {
        tabAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        tabAdapter.addFragment(new HomepageFragment(), "Soup");
        tabAdapter.addFragment(new HomepageFragment(), "Đồ chiên");
        tabAdapter.addFragment(new HomepageFragment(), "Đồ Hàn");
        tabAdapter.addFragment(new HomepageFragment(), "Đồ Âu");
        tabAdapter.addFragment(new HomepageFragment(), "Đồ Á");
        tabAdapter.addFragment(new HomepageFragment(), "Đồ Pháp");

        viewPager.setAdapter(tabAdapter);
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