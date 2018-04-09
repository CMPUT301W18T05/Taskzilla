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

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.AppColors;
import com.cmput301w18t05.taskzilla.NotificationManager;
import com.cmput301w18t05.taskzilla.PersonalColors;
import com.cmput301w18t05.taskzilla.fragment.MyBidsFragment;
import com.cmput301w18t05.taskzilla.fragment.NotificationsFragment;
import com.cmput301w18t05.taskzilla.fragment.ProfileFragment;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.fragment.SearchFragment;
import com.cmput301w18t05.taskzilla.fragment.TasksFragment;
import com.google.gson.Gson;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


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
    ActionBar actionBar;
    private ListView test;

    private AppColors appColors = AppColors.getInstance();

    Integer defaultColorR;
    Integer defaultColorG;
    Integer defaultColorB;
    ColorPicker cp;
    private static final String FILENAME = "Themes.sav";

    /**
     * Activity uses the activity_welcome.xml layout
     * Initializes the navigation tabs on the bottom of the screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        appColors.setActionBarColor("#000000");
        appColors.setActionBarTextColor("#05e5ee");
        getDrawable(android.R.drawable.ic_popup_reminder).setColorFilter( 0xff808080, PorterDuff.Mode.MULTIPLY );
        loadAppColors();

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(appColors.getActionBarColor())));
        actionBar.setTitle(Html.fromHtml("<font color='" + appColors.getActionBarTextColor() + "'>Tasks</font>"));

        tabsAdapter = new TabsManager(this.getSupportFragmentManager());
        tabsContent = findViewById(R.id.welcome_tabs_content);
        tabsContent.setOffscreenPageLimit(5);
        tabsContent.setAdapter(tabsAdapter);

        tabsContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch(position) {
                    case 0: actionBar.setTitle(Html.fromHtml("<font color='" + appColors.getActionBarTextColor() + "'>Tasks</font>")); break;
                    case 1: actionBar.setTitle(Html.fromHtml("<font color='" + appColors.getActionBarTextColor() + "'>Bids</font>")); break;
                    case 2: actionBar.setTitle(Html.fromHtml("<font color='" + appColors.getActionBarTextColor() + "'>Search</font>")); break;
                    case 3: actionBar.setTitle(Html.fromHtml("<font color='" + appColors.getActionBarTextColor() + "'>Notifications</font>")); break;
                    case 4: actionBar.setTitle(Html.fromHtml("<font color='" + appColors.getActionBarTextColor() + "'>Profile</font>")); break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabs = findViewById(R.id.tabs_bar);
        tabs.setupWithViewPager(tabsContent);

        NotificationManager.getInstance(this.getApplicationContext(), tabs);


        tabs.getTabAt(0).setIcon(android.R.drawable.ic_menu_my_calendar);
        tabs.getTabAt(1).setIcon(android.R.drawable.ic_menu_agenda);
        tabs.getTabAt(2).setIcon(android.R.drawable.ic_search_category_default);
        tabs.getTabAt(3).setIcon(android.R.drawable.ic_popup_reminder).setCustomView(R.layout.badged_tab);
        tabs.getTabAt(4).setIcon(android.R.drawable.ic_menu_myplaces);

        tabs.getTabAt(0).getIcon().setColorFilter(Color.parseColor(appColors.getActionBarTextColor()), PorterDuff.Mode.MULTIPLY );
        tabs.getTabAt(1).getIcon().setColorFilter(Color.parseColor(appColors.getActionBarTextColor()), PorterDuff.Mode.MULTIPLY );
        tabs.getTabAt(2).getIcon().setColorFilter(Color.parseColor(appColors.getActionBarTextColor()), PorterDuff.Mode.MULTIPLY );
        tabs.getTabAt(3).getIcon().setColorFilter(Color.parseColor(appColors.getActionBarTextColor()), PorterDuff.Mode.MULTIPLY );
        tabs.getTabAt(4).getIcon().setColorFilter(Color.parseColor(appColors.getActionBarTextColor()), PorterDuff.Mode.MULTIPLY );




        tabs.setBackground(new ColorDrawable(Color.parseColor(appColors.getActionBarColor())));
        // Count notifications user currently has and updates badge accordingly

        NotificationManager.getInstance().countNotifications();
        NotificationManager.getInstance().updateBadge();
    }

    /**
     *  create the menu
     * @param menu
     * @return true showing the options menu created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    /**
     * When an option is clicked, open the color picker allowing the user
     * to select a theme or text color
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.Theme:
                defaultColorR = 0;
                defaultColorG = 0;
                defaultColorB = 0;
                cp = new ColorPicker(WelcomeActivity.this, defaultColorR, defaultColorG, defaultColorB);
                cp.setTitle("Test");
                cp.show();
                cp.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(int color) {
                        appColors.setActionBarColor(String.format("#%06X", (0xFFFFFF & color)));
                        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(appColors.getActionBarColor())));
                        tabs.setBackground(new ColorDrawable(Color.parseColor(String.format("#%06X", (0xFFFFFF & color)))));
                        saveAppColors();
                        cp.dismiss();
                    }
                });
                return true;

            case R.id.TextColor:
                defaultColorR = 0;
                defaultColorG = 0;
                defaultColorB = 0;
                cp = new ColorPicker(WelcomeActivity.this, defaultColorR, defaultColorG, defaultColorB);
                cp.setTitle("Test");
                cp.show();
                cp.setCallback(new ColorPickerCallback() {
                    @Override
                    public void onColorChosen(int color) {
                        appColors.setActionBarTextColor(String.format("#%06X", (0xFFFFFF & color)));
                        actionBar.setTitle(Html.fromHtml("<font color='" + appColors.getActionBarTextColor() + "'>Taskzilla</font>"));
                        Log.i("colors",Integer.toString(color));
                        tabs.getTabAt(0).getIcon().setColorFilter(Color.parseColor(appColors.getActionBarTextColor()), PorterDuff.Mode.MULTIPLY );
                        tabs.getTabAt(1).getIcon().setColorFilter(Color.parseColor(appColors.getActionBarTextColor()), PorterDuff.Mode.MULTIPLY );
                        tabs.getTabAt(2).getIcon().setColorFilter(Color.parseColor(appColors.getActionBarTextColor()), PorterDuff.Mode.MULTIPLY );
                        tabs.getTabAt(3).getIcon().setColorFilter(Color.parseColor(appColors.getActionBarTextColor()), PorterDuff.Mode.MULTIPLY );
                        tabs.getTabAt(4).getIcon().setColorFilter(Color.parseColor(appColors.getActionBarTextColor()), PorterDuff.Mode.MULTIPLY );
                        saveAppColors();
                        cp.dismiss();

                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    boolean doubleBackToExitPressedOnce = false;
    // Taken from https://stackoverflow.com/questions/8430805/clicking-the-back-button-twice-to-exit-an-activity
    // 2018-04-03
    // Adds a delay so the app doesn't immediately close when the back button is clicked.
    // Prevents accidentally closing the app

    /**
     * if back button clicked twice, exit app
     *
     */
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

    /**
     * load the AppColors singleton from storage
     */
    private void loadAppColors() {
        try {
            FileInputStream fis = openFileInput("COLORS.TXT");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            PersonalColors c = new Gson().fromJson(br, PersonalColors.class);
            AppColors.getInstance().setColors(c);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    /**
     * save the AppColors singleton to storage
     */
    private void saveAppColors() {
        try {
            Gson gson = new Gson();
            FileOutputStream fos = openFileOutput("COLORS.TXT", Context.MODE_PRIVATE);
            String json = gson.toJson(this.appColors.getColors());
            fos.write(json.getBytes());
            fos.close();
        }
        catch (IOException e) {
            System.out.println("Something went wrong when saving colors!");
        }
    }
}
