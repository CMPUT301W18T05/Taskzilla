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
 * Main screen the user interacts with when searching for tasks.
 *
 * @author Andy
 * @see SearchController
 * @version 1
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

        ImageButton mButton = mConstraintLayout.findViewById(R.id.MapButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMap();
            }
        });

        //Set up listview and adapter
        searchResults = new ArrayList<>();
        availableTasks = mConstraintLayout.findViewById(R.id.ListView2);
        searchController = new SearchController(this, getActivity());

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, searchResults);
        availableTasks.setAdapter(adapter);

        availableTasks.setClickable(true);

        /*
         *  Listens for user tapping on a task in the listview
         *
         *  Sets currentTask variable to the current task tapped, which
         *  is used later on to determine which item to remove from the listview
         *  if the item was deleted.
         */
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

    /**
     * Starts an intent of the given task the user clicked on.
     *
     * @param taskId    a string containing the id of the task selected
     * @return          boolean value to determine if task was deleted; true = deleted, default is false
     * @see             ViewTaskActivity
     */

    public void viewTask(String taskId){
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        intent.putExtra("TaskId", taskId);
        startActivityForResult(intent, 1);
    }

    /**
     * Checks the result from viewTask and acts accordingly.
     *
     * @param reqCode       an integer determining the type of request was done
     * @param resultCode    an integer determining what was done in the request
     * @param data          data which was sent back from the activity
     * @see                 ViewTaskActivity
     */

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        if(reqCode == 1) {
            if(resultCode == RESULT_OK) {
                Boolean result = data.getBooleanExtra("result", false);

                //if item is deleted, result == true
                if(result)
                    searchResults.remove(currentTask);
            }
        }
    }

    /**
     * Determines what is done on the view once it has been created
     *
     * @param view                  The current activity
     * @param savedInstanceState    The state of the screen before interrupts appear, such as leaving the app
     */

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // expand search bar by default
        searchField = view.findViewById(R.id.searchView);

        /*
         * Listens for changes in the searchview
         * OnQueryTextChange invokes whenever the user types, while on the other hand
         * OnQueryTextSubmit invokes only when the user submits the keywords.
         *
         * There are 3 scenarios:
         *      1. if(sentence.length == 0)  and if(searchController.getKeywords().isEmpty() == true)
         *          sentence is the words the user types in.
         *
         *          These conditions check if the user entered in previous search words,
         *          if not then nothing happens since all tasks are shown to begin with
         *
         *      2. if(sentence.length == 0)  and if(searchController.getKeywords().isEmpty() == false)
         *
         *          These conditions are for when the user had previously searched for keywords and now wants
         *          to see all the available tasks. It first clears the listview then does a search returning all
         *          available tasks.
         *
         *      3. The last condition is when the user is searching for tasks by entering in keyword(s).
         *          This clears the listview and then proceeds to make a search request using the keyword(s)
         *          as a parameter. It then returns the tasks containing the keywords in the descriptions.
         *
         */

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
                    } else {                                          // Since keywords isn't empty, previous array of tasks isn't all available tasks
                        searchController.clearKeywords();
                        searchController.getAllRequest();
                    }
                } else {                                             // Adds keyword to list and loads new set of tasks based on keywords
                    searchController.clearKeywords();
                    searchController.addKeywords(sentence);
                    searchController.searchRequest(sentence);
                }
                return false;
            }
        });
    }

    /**
     * Switch to Map Activity when map button is clicked
     */
    public void viewMap(){
        Intent intent = new Intent(getActivity(), MapActivity.class);
        startActivity(intent);
    }

    // Clears adapter and inputs new tasks
    public void notifyChange() {
        searchResults.clear();
        searchResults.addAll(searchController.getResults());
        adapter.notifyDataSetChanged();
    }

    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}