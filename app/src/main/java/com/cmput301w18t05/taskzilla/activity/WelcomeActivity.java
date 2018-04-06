/*
 * Copyright 2018 (c) Andy Li, Colin Choi, James Sun, Jeremy Ng, Micheal Nguyen, Wyatt Praharenka
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.cmput301w18t05.taskzilla.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.NotificationManager;
import com.cmput301w18t05.taskzilla.fragment.MyBidsFragment;
import com.cmput301w18t05.taskzilla.fragment.NotificationsFragment;
import com.cmput301w18t05.taskzilla.fragment.ProfileFragment;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.fragment.SearchFragment;
import com.cmput301w18t05.taskzilla.fragment.TasksFragment;
import com.cmput301w18t05.taskzilla.fragment.TasksProviderFragment;
import com.cmput301w18t05.taskzilla.fragment.TasksRequesterFragment;

/**
 * Welcome activity of the app
 * All fragments of the app are displayed and accessible from here
 *
 * @version 1.0
 */
public class WelcomeActivity extends AppCompatActivity {
    private int sortFilter;
    TabLayout tabs;
    ViewPager tabsContent;
    FragmentPagerAdapter tabsAdapter;

    private ListView test;

    /**
     * Activity uses the activity_welcome.xml layout
     * Initializes the navigation tabs on the bottom of the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        NotificationManager.getInstance(this.getApplicationContext());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        actionBar.setTitle(Html.fromHtml("<font color='#00e5ee'>Taskzilla</font>"));

        tabsAdapter = new TabsManager(this.getSupportFragmentManager());
        tabsContent = findViewById(R.id.welcome_tabs_content);
        tabsContent.setOffscreenPageLimit(5);
        tabsContent.setAdapter(tabsAdapter);

        tabs = findViewById(R.id.tabs_bar);
        tabs.setupWithViewPager(tabsContent);
        tabs.getTabAt(0).setIcon(android.R.drawable.ic_menu_my_calendar);
        tabs.getTabAt(1).setIcon(android.R.drawable.ic_menu_agenda);
        tabs.getTabAt(2).setIcon(android.R.drawable.ic_search_category_default);
        tabs.getTabAt(3).setIcon(android.R.drawable.ic_popup_reminder);
        tabs.getTabAt(4).setIcon(android.R.drawable.ic_menu_myplaces);

    }




    boolean doubleBackToExitPressedOnce = false;
    // Taken from https://stackoverflow.com/questions/8430805/clicking-the-back-button-twice-to-exit-an-activity
    // 2018-04-03
    // Adds a delay so the app doesn't immediately close when the back button is clicked.
    // Prevents accidentally closing the app
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public int getFilter(){
        return sortFilter;
    }
    /**
     * Manages the navigation tabs
     */
    public class TabsManager extends FragmentPagerAdapter {
        TabsManager(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return new fragments based on tab position
         * @param position The position of the tab
         * @return Fragment
         */
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
                    return new NotificationsFragment();
                case 4:
                    return new ProfileFragment();
                default:
                    return null;
            }
        }

        /**
         * Get the number of tabs
         * @return int
         */
        @Override
        public int getCount() {
            return 5;
        }


        /**
         * Get the name of the tab base on the tab position
         * @param position The position of the tab
         * @return CharSequence
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "";
                case 1:
                    return "";
                case 2:
                    return "";
                case 3:
                    return "";
                case 4:
                    return "";
                default:
                    return null;
            }
        }
    }




}
