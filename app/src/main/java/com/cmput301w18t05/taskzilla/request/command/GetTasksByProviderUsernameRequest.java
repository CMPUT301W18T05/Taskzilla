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

import com.cmput301w18t05.taskzilla.AppCache;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.Request;

import java.util.ArrayList;


/**
 * Request for getting a task object from Elastic Search using the username of the provider
 * @author Wyatt
 * @see ElasticSearchController
 * @version 1.0
 */
public class GetTasksByProviderUsernameRequest extends Request {
    private ElasticSearchController.GetTasksByProviderUsername task;
    private ArrayList<Task> result;
    private String user;

    public GetTasksByProviderUsernameRequest(String username) {
        this.user = username;
    }

    public void execute() {
        task = new ElasticSearchController.GetTasksByProviderUsername();
        task.execute(user);
    }

    @Override
    public void executeOffline() {
        executedOffline = true;
        AppCache appCache = AppCache.getInstance();
        ArrayList<Task> cachedTasks = appCache.getCachedTasks();

        result = new ArrayList<>();
        for (Task t : cachedTasks) {
            if (t.getTaskProvider().getUsername() == user) {
                result.add(t);
            }
        }
    }

    @Override
    public boolean requiresConnection() {
        return false;
    }

    public ArrayList<Task> getResult() {
        try {
            if (!executedOffline) {
                result = this.task.get();
            }

            for(Task t : result)
                Log.i("Result", t.getId());

            return result;
        }
        catch (Exception e) {
            return null;
        }
    }
}
