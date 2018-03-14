package com.cmput301w18t05.taskzilla;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.SearchRequest;

import java.util.ArrayList;

import io.searchbox.core.Search;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 *
 */

public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    private SearchView searchField;
    private ListView availableTasks;
    private ArrayAdapter<Task> adapter;
    private ArrayList<Task> searchResults;
    private SearchController searchController;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ConstraintLayout mConstraintLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_search,
                container, false);

        ImageButton mButton = (ImageButton) mConstraintLayout.findViewById(R.id.MapButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMap();
            }
        });

        //Set up listview and adapter
        searchResults = new ArrayList<Task>();
        availableTasks = (ListView) mConstraintLayout.findViewById(R.id.ListView2);
        adapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, searchResults);
        availableTasks.setAdapter(adapter);
        searchController = new SearchController();

        availableTasks.setClickable(true);

        availableTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Object task = availableTasks.getItemAtPosition(i);
                viewTask();
            }
        });


        //Dummy Tasks for testing. Remove these and get the tasks from elastic search
        searchResults.add(new Task());

        return mConstraintLayout;
    }

    public void viewTask(){
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // expand search bar by default
        searchField = view.findViewById(R.id.searchView);

        //searchField.setIconified(false);
    }

    /**
     * Switch to Map Activity when map button is clicked
     */
    public void viewMap(){
        Intent intent = new Intent(getActivity(), MapActivity.class);
        startActivity(intent);
    }

    public void setSearchController (SearchController searchController) {
        this.searchController = searchController;
    }

    @Override
    public boolean onQueryTextChange(String quary) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String text) {
        //final String keywordArray[] = text.split(" ");
        String sentence;
        sentence = text.toLowerCase();

        if (sentence.length() == 0) {                          // Checks if user entered text in search bar
            if (searchController.getKeywords().isEmpty()){ // Checks if keywords is empty, if yes return already loaded array of tasks
                // do nothing
            }
            else {                                         // Since keywords isn't empty, previous array of tasks isn't all available tasks
                searchController.clearKeywords();

                RequestManager requestManager = new RequestManager();
                SearchRequest newRequest = new SearchRequest(searchController);

                requestManager.invokeRequest(newRequest);
                searchResults = searchController.getResults();
                // do elastic search and add all task
            }
        }

        else {                                             // Adds keyword to list and loads new set of tasks based on keywords
            /*
            for (int i = 0; i < keywordArray.length; i++) {
                word = keywordArray[i].toLowerCase();
                */

            searchController.clearKeywords();
            searchController.addKeywords(sentence);

            // do elastic search and add results

            RequestManager requestManager = new RequestManager();
            SearchRequest newRequest = new SearchRequest(searchController);

            requestManager.invokeRequest(newRequest);

            searchController = newRequest.getTasks();
            searchResults = searchController.getResults();
            searchResults.add(new Task());
            //}
        }

        adapter.notifyDataSetChanged();

        return false;
    }

}
