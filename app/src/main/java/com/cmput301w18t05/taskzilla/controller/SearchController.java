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

public class SearchController {
    private ArrayList<String> searchKeywords;
    private ArrayList<Task> searchResults;
    private SearchTaskRequest newRequest;
    private SearchFragment view;
    private Context ctx;

    public SearchController(SearchFragment v, Context context) {
        this.searchKeywords = new ArrayList<String>();
        this.searchResults = new ArrayList<Task>();
        this.view = v;
        this.ctx = context;
    }

    public void addKeywords(String word) {
        this.searchKeywords.add(word);
    }

    public ArrayList<String> getKeywords() {
        return this.searchKeywords;
    }

    public ArrayList<Task> getResults() {
        return this.searchResults;
    }

    public void clearKeywords() {
        this.searchKeywords.clear();
    }

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

    public void getAllRequest() {
        GetAllTasksRequest request = new GetAllTasksRequest();
        RequestManager.getInstance().invokeRequest(ctx, request);

        searchResults.clear();

        ArrayList<Task> temp;
        temp = request.getResult();

        while (temp.size() > 0) {

            for (Task t : temp)
                this.searchResults.add(t);

            RequestManager.getInstance().invokeRequest(ctx, request);
            temp = request.getResult();
        }

        view.notifyChange();
    }

}
