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

/**
 * Request for getting a task object from Elastic Search using the task id
 * @author Wyatt
 * @see ElasticSearchController
 * @version 1.0
 */
public class GetTaskRequest extends Request {
    ElasticSearchController.GetTask task;
    String taskId;
    Task result;

    public GetTaskRequest(String taskId) {
        this.taskId = taskId;
    }

    /**
     * get this task from elasticsearch
     */
    public void execute() {
        task = new ElasticSearchController.GetTask();
        task.execute(this.taskId);
    }

    /**
     * get the tasks with this id in the app cache
     */
    @Override
    public void executeOffline() {
        executedOffline = false;
        AppCache appCache = AppCache.getInstance();
        result = appCache.findCachedTaskById(taskId);
    }

    @Override
    public boolean requiresConnection() {
        return false;
    }

    /**
     * get the result once, task is done
     * @return null if no task exists, task object if found
     */
    public Task getResult() {
        try {
            if (!executedOffline)
                result = this.task.get();

            return result;
        }
        catch (Exception e) {
            return result;
        }
    }
}
