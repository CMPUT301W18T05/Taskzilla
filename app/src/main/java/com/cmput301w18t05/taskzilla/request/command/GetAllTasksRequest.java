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

import android.os.AsyncTask;
import android.util.Log;

import com.cmput301w18t05.taskzilla.AppCache;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.Request;

import java.util.ArrayList;

/**
 * Request for getting all tasks objects from elastic search
 * @author Wyatt
 * @see ElasticSearchController
 * @version 1.0
 */
public class GetAllTasksRequest extends Request {
    ElasticSearchController.GetAllTasks task;
    ArrayList<Task> result;

    private int from = 0;
    private int size = 10;
    private boolean executedOfflineOnce = false;

    public GetAllTasksRequest() {
    }

    @Override
    public void execute() {
        task = new ElasticSearchController.GetAllTasks(from,size);
        task.execute();

        try {
            result = task.get();
            from += size;
            AppCache.getInstance().addInCache(result);
        }
        catch (Exception e) {
            Log.i("Query", "No more results");
        }

    }

    @Override
    public void executeOffline() {
        // return what is in app cache
        if (executedOfflineOnce) { // prevent infinite loop
            result = new ArrayList<Task>();
            return;
        }

        AppCache appCache = AppCache.getInstance();
        result = appCache.getCachedTasks();
        executedOfflineOnce = true;
    }

    @Override
    public boolean requiresConnection() {
        return false;
    }

    public ArrayList<Task> getResult() {
        return result;
    }
}
