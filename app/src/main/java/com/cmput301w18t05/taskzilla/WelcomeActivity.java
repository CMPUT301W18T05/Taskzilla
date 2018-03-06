package com.cmput301w18t05.taskzilla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

public class WelcomeActivity extends AppCompatActivity {

    private TabHost tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /* setup view */
        tabs = findViewById(R.id.nav_tabs);

        /* setup tabs */
        tabs.setup();

        TabHost.TabSpec profileTab = tabs.newTabSpec("Profile");
        profileTab.setContent(R.id.Profile);
        profileTab.setIndicator("Profile");
        tabs.addTab(profileTab);

        TabHost.TabSpec tasksTab = tabs.newTabSpec("Tasks");
        profileTab.setContent(R.id.Tasks);
        profileTab.setIndicator("Tasks");
        tabs.addTab(profileTab);

        TabHost.TabSpec mybidsTab = tabs.newTabSpec("My Bids");
        profileTab.setContent(R.id.MyBids);
        profileTab.setIndicator("My Bids");
        tabs.addTab(profileTab);

        TabHost.TabSpec searchTab = tabs.newTabSpec("Search");
        profileTab.setContent(R.id.Search);
        profileTab.setIndicator("Search");
        tabs.addTab(profileTab);

    }
}
