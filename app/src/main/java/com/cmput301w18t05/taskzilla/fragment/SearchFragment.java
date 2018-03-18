package com.cmput301w18t05.taskzilla.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.controller.SearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.activity.MapActivity;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 *
 */

public class SearchFragment extends Fragment {//implements SearchView.OnQueryTextListener {

    private SearchView searchField;
    private ListView availableTasks;
    private ArrayAdapter<Task> adapter;
    private ArrayList<Task> searchResults;
    private SearchController searchController;
    private Task currentTask;

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
        searchController = new SearchController(this, getActivity());

        adapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, searchResults);
        availableTasks.setAdapter(adapter);

        availableTasks.setClickable(true);

        availableTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentTask = searchResults.get(i);
                viewTask(searchResults.get(i).getId());

            }
        });

        // get all available tasks
        searchController.getAllRequest();

        return mConstraintLayout;
    }

    public void viewTask(String taskId){
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        intent.putExtra("TaskId", taskId);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        if(reqCode == 1) {
            if(resultCode == RESULT_OK) {
                Boolean result = data.getBooleanExtra("result", false);

                if(result == true)
                    searchResults.remove(currentTask);
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // expand search bar by default
        searchField = view.findViewById(R.id.searchView);

        searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                String sentence;
                sentence = text.toLowerCase();

                if (sentence.length() == 0) {                          // Checks if user entered text in search bar
                    if (searchController.getKeywords().isEmpty()){ // Checks if keywords is empty, if yes return already loaded array of tasks
                        //do nothing
                    }

                    else {                                          // Since keywords isn't empty, previous array of tasks isn't all available tasks
                        searchController.clearKeywords();
                        searchController.getAllRequest();
                    }
                }

                else {                                             // Adds keyword to list and loads new set of tasks based on keywords
                    searchController.clearKeywords();
                    searchController.addKeywords(sentence);
                    searchController.searchRequest(sentence);
                }

                return false;
            }
        });

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

    // Clears adapter and inputs new tasks
    public void notifyChange() {
        searchResults.clear();

        for(Task t : searchController.getResults())
            searchResults.add(t);

        adapter.notifyDataSetChanged();
    }

    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
