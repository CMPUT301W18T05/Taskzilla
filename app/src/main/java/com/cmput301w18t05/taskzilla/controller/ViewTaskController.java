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
import android.view.View;

import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.RemoveTaskRequest;


/**
 * Controller for ViewTaskActivity
 * @author Andy
 * @see com.cmput301w18t05.taskzilla.activity.ViewTaskActivity
 * @version 1.0
 */
public class ViewTaskController {
    private Context ctx;
    private View view;
    private String taskID;
    private Task task;

    /**
     * Takes in the view and the context of the activity
     * @param v
     * @param context
     */
    public ViewTaskController(View v, Context context) {
        this.ctx = context;
        this.view = v;
        this.taskID = new String();
    }

    /**
     * Sets the task id to be viewed
     * @param id
     */
    public void setTaskID(String id) {
        this.taskID = id;
    }

    /**
     * Returns the id of the task being viewed
     * @return
     */
    public Task getTask() {
        return this.task;
    }


    /**
     * getTaskRequest
     * get the task from elastic search using request manager
     * and set the task of the controller to be the result
     *
     * @author Micheal-Nguyen
     */
    public void getTaskRequest() {
        GetTaskRequest request = new GetTaskRequest(taskID);
        RequestManager.getInstance().invokeRequest(ctx, request);

        this.task = request.getResult();
    }

    public void updateTaskRequest(Task task) {
        AddTaskRequest request = new AddTaskRequest(task);
        RequestManager.getInstance().invokeRequest(ctx, request);
    }

    public void RemoveTaskRequest(Task task) {
        RemoveTaskRequest request = new RemoveTaskRequest(task.getId());
        RequestManager.getInstance().invokeRequest(ctx, request);
    }

}
