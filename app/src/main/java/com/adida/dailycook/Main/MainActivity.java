package com.adida.dailycook.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.adida.dailycook.Main.HomepageFragment.HomepageFragment;
import com.adida.dailycook.PagerAdapter.PagerAdapter;
import com.adida.dailycook.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private PagerAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setElevation(0);

        viewPager = (ViewPager) findViewById(R.id.viewPagerHomePage);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutHomepage);

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

}