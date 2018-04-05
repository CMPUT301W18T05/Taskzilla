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

package com.cmput301w18t05.taskzilla.controller;

import android.content.Context;

import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.fragment.SearchFragment;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetAllTasksRequest;
import com.cmput301w18t05.taskzilla.request.command.SearchTaskRequest;

import java.util.ArrayList;

/**
 * Created by Andy on 3/12/2018.
 */

/**
 * Controller that deals with the backend stuff related to the screen.
 *
 * @author Andy
 * @see    SearchFragment
 * @version 1.0
 */

public class SearchController {
    private ArrayList<String> searchKeywords;
    private ArrayList<Task> searchResults;
    private SearchTaskRequest newRequest;
    private SearchFragment view;
    private Context ctx;

    /**
     * The view and context of the activity is passed into this controller
     * @param v
     * @param context
     */
    public SearchController(SearchFragment v, Context context) {
        this.searchKeywords = new ArrayList<String>();
        this.searchResults = new ArrayList<Task>();
        this.view = v;
        this.ctx = context;
    }

    /**
     * Adds keywords the user input into an arraylist
     *
     * @param word  string containing the keywords the user inputted
     */

    public void addKeywords(String word) {
        this.searchKeywords.add(word);
    }

    /**
     * Returns the arraylist containing the keywords the user inputted
     *
     * @return  the arraylist containing a string of keywords
     */

    public ArrayList<String> getKeywords() {
        return this.searchKeywords;
    }

    /**
     * Returns a list of tasks which corresponds to the keywords given
     *
     * @return  arraylist containing the tasks
     */

    public ArrayList<Task> getResults() {
        return this.searchResults;
    }

    /**
     * Clears the arraylist holding the keywords
     */

    public void clearKeywords() {
        this.searchKeywords.clear();
    }

    /**
     * This method invokes a search request using the given keywords, which is then sent
     * to the request manager which determines if the app is online or offline, before doing
     * the request.
     *
     * @param sentence  string of keywords
     * @see             RequestManager
     */

    public void searchRequest(String sentence) {
        newRequest = new SearchTaskRequest(sentence);
        RequestManager.getInstance().invokeRequest(ctx, newRequest);

        ArrayList<Task> temp;
        temp = newRequest.getTasks();

        if(temp.size() != 0) {

            while (temp.size() > 0) {

                for (Task t : temp)
                    this.searchResults.add(t);

                RequestManager.getInstance().invokeRequest(ctx, newRequest);
                temp = newRequest.getTasks();
            }
        }
        else {
            searchResults.clear();
        }

        view.notifyChange();
    }

    /**
     * This method invokes a get all request, which is then sent
     * to the request manager which determines if the app is online or offline,
     * before doing the request.
     *
     * @see             RequestManager
     */

    public void getAllRequest() {
        GetAllTasksRequest request = new GetAllTasksRequest();
        RequestManager.getInstance().invokeRequest(ctx, request);

        searchResults.clear();

        ArrayList<Task> temp;
        temp = request.getResult();
        System.out.println("Search result is: " + temp);

        while (temp != null && temp.size() > 0) {

            for (Task t : temp)
                this.searchResults.add(t);

            RequestManager.getInstance().invokeRequest(ctx, request);
            temp = request.getResult();
        }

        view.notifyChange();
    }

}
