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


import com.cmput301w18t05.taskzilla.AppCache;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.Request;

import java.util.ArrayList;


/**
 * Request for getting a task object from Elastic Search using the username of the requester
 * @author Wyatt
 * @see ElasticSearchController
 * @version 1.0
 */
public class GetTasksByRequesterUsernameRequest extends Request {
    private ElasticSearchController.GetTasksByRequesterUsername task;
    private ArrayList<Task> result;
    private String user;
    private boolean executedOffline = false;
    private boolean executedOfflineOnce = false;
    private int from = 0;
    private int size = 10;

    public GetTasksByRequesterUsernameRequest(String username) {
        this.user = username;
    }

    public void execute() {
        System.out.println("Getting tasks by requester username: " + user + " from: " + from + " with size: " + size);
        task = new ElasticSearchController.GetTasksByRequesterUsername(from, size);
        task.execute(user);

        try {
            result = task.get();
            from += size;
            AppCache.getInstance().addInCache(result);
            // dont have to add into app cache here
        } catch (Exception e) {
            result = new ArrayList<>();
            System.out.println("No more results");
        }
    }

    @Override
    public void executeOffline() {
        if (executedOfflineOnce) {
            result = new ArrayList<>();
            return;
        }
        executedOfflineOnce = true;

        System.out.println("Searching for tasks by requester username with username: "+user);
        executedOffline = true;
        AppCache appCache = AppCache.getInstance();
        ArrayList<Task> cachedTasks = appCache.getCachedTasks();

        this.result = new ArrayList<>();
        for (Task t : cachedTasks) {
            System.out.println("Looking at task: "+t);
            System.out.println("Looking at task with taskrequester uname: "+t.getTaskRequester().getUsername());
            if (t.getTaskRequester().getUsername().equals(user)) {
                System.out.println("Adding this to result");
                result.add(t);
            }
        }
    }

    @Override
    public boolean requiresConnection() {
        return false;
    }

    public ArrayList<Task> getResult() {
        return result;
    }
}
