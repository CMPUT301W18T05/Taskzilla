package com.cmput301w18t05.taskzilla.activity;

import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.cmput301w18t05.taskzilla.fragment.MyBidsFragment;
import com.cmput301w18t05.taskzilla.fragment.ProfileFragment;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.fragment.SearchFragment;
import com.cmput301w18t05.taskzilla.fragment.TasksFragment;

public class WelcomeActivity extends AppCompatActivity {

    TabLayout tabs;
    ViewPager tabsContent;
    FragmentPagerAdapter tabsAdapter;

    private ListView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tabsAdapter = new TabsManager(this.getSupportFragmentManager());
        tabsContent = findViewById(R.id.welcome_tabs_content);
        //tabsContent.setOffscreenPageLimit(4);
        tabsContent.setAdapter(tabsAdapter);

        tabs = findViewById(R.id.tabs_bar);
        tabs.setupWithViewPager(tabsContent);

    }

    public class TabsManager extends FragmentPagerAdapter {
        TabsManager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TasksFragment();
                case 1:
                    return new MyBidsFragment();
                case 2:
                    return new SearchFragment();
                case 3:
                    return new ProfileFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tasks";
                case 1:
                    return "My Bids";
                case 2:
                    return "Search";
                case 3:
                    return "Profile";
                default:
                    return null;
            }
        }
    }



}
