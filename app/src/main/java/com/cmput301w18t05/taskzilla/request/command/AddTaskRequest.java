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
import com.cmput301w18t05.taskzilla.request.InsertionRequest;

/**
 * Request for adding tasks to elastic search
 * @author Wyatt
 * @see ElasticSearchController
 * @version 1.0
 */
public class AddTaskRequest extends InsertionRequest {
    private ElasticSearchController.AddTask task;
    private Task taskData;
    private boolean executedOffline = false;
    private boolean isUpdate = false;
    private String originalId;

    public AddTaskRequest(Task task) {
        queueReady = true; // put this into queue if needed!
        this.taskData = task;
        originalId = taskData.getId();
    }

    /**
     * add task to elasticsearch. if this is an update then keep the id, otherwise
     * remove the id for elasticsearch to assign one.
     */
    @Override
    public void execute() {
        if (!isUpdate)
            this.taskData.setId(null);
        else
            taskData.setId(originalId);
        task = new ElasticSearchController.AddTask();
        task.execute(taskData); // for now, subject to change.
    }

    /**
     * add the task into the appcache for offline viewing.
     */
    @Override
    public void executeOffline() {
        AppCache appCache = AppCache.getInstance();
        appCache.addInCache(taskData);
        executedOffline = true;
    }

    /**
     * adding a task dooes not require a connection
     * @return
     */
    @Override
    public boolean requiresConnection() {
        return false;
    }

    /**
     * return true if it was successfully added (at least we think so)
     * @return
     */
    public boolean getResult() {
        try {
            return executedOffline || task.get();
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * set the update flag.
     * @param bool
     */
    public void setUpdate(boolean bool) {
        isUpdate = bool;
    }

}
