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

package com.cmput301w18t05.taskzilla.request.command;

import android.util.Log;

import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.SearchRequest;

import java.util.ArrayList;


/**
 * Request for getting a list for task objects from Elastic Search using keywords
 * @author Andy
 * @see ElasticSearchController
 * @version 1.0
 */
public class SearchTaskRequest extends SearchRequest {
    private String searchKeywords;
    private ArrayList<Task> results;
    private ElasticSearchController.SearchForTasks search;
    private int from = 0;
    private int size = 10;

    public SearchTaskRequest(String keywords) {
        this.searchKeywords = keywords;
    }

    @Override
    public void execute() {
        search = new ElasticSearchController.SearchForTasks(from,size);
        search.execute(searchKeywords);

        try {
            results = search.get();
            from += size;
        }
        catch(Exception e) {
            Log.i("Error", "Failed to get tasks from the async object");
        }
    }

    @Override
    public void executeOffline() {
        results = null;
    }

    @Override
    public boolean requiresConnection() {
        return true;
    }

    public ArrayList<Task> getTasks() {
        return results;
    }
}
