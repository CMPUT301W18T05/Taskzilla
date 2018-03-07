package com.cmput301w18t05.taskzilla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private TabHost tabs;

    private ListView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /* setup view */
        tabs = findViewById(R.id.nav_tabs);
        test = findViewById(R.id.tasks_list);

        /* setup tabs */
        tabs.setup();

        TabHost.TabSpec tasksTab = tabs.newTabSpec("Tasks");
        tasksTab.setContent(R.id.Tasks);
        tasksTab.setIndicator("Tasks");
        tabs.addTab(tasksTab);

        TabHost.TabSpec mybidsTab = tabs.newTabSpec("My Bids");
        mybidsTab.setContent(R.id.MyBids);
        mybidsTab.setIndicator("My Bids");
        tabs.addTab(mybidsTab);

        TabHost.TabSpec searchTab = tabs.newTabSpec("Search");
        searchTab.setContent(R.id.Search);
        searchTab.setIndicator("Search");
        tabs.addTab(searchTab);

        TabHost.TabSpec profileTab = tabs.newTabSpec("Profile");
        profileTab.setContent(R.id.Profile);
        profileTab.setIndicator("Profile");
        tabs.addTab(profileTab);

        /* setup tasks list view */
        ArrayList<Integer> test = new ArrayList();

        for (int i = 0; i < 100; i++) {
            test.add(i);
        }

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, R.layout.tasks_list_view,R.id.textView, test);
        ListView test2 = findViewById(R.id.tasks_list);
        test2.setAdapter(arrayAdapter);
    }
}
