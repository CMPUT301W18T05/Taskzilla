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

import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.Request;

/**
 * Request for updating a task object in Elastic Search. Inserts a new updated task overwriting the old one
 * @author Micheal
 * @see ElasticSearchController
 * @version 1.0
 */
@Deprecated
public class UpdateTaskRequest extends Request {
    ElasticSearchController.UpdateTask updateRequest;
    private Task taskData;

    public UpdateTaskRequest(Task task) {
        this.taskData = task;
    }

    public void execute() {
        updateRequest = new ElasticSearchController.UpdateTask();
        updateRequest.execute(taskData);
    }

    public void executeOffline() {}
}
