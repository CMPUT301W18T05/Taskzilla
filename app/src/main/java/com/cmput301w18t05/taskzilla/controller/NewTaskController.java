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
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.activity.NewTaskActivity;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;

import static android.app.Activity.RESULT_OK;

/**
 * Controller for creating a new task
 * @author Colin
 * @see NewTaskActivity
 * @version 1.0
 */
public class NewTaskController {

    private NewTaskActivity view;
    private Context ctx;
    private String taskId;

    /**
     * Context and view of the activity is passed into the controller
     * @param view
     * @param context
     */
    public NewTaskController(NewTaskActivity view, Context context) {
        this.view = view;
        this.ctx = context;
    }

    /**
     * Error checks the task name and description to enforce character limits.
     * Then the task is added to Elastic Search
     * @param name Task name
     * @param user User that is making the task
     * @param description Description of the task
     */
    public void addTask(String name, User user, String description, LatLng taskLocation){
        //Check field lengths and give error
        EditText taskName = view.findViewById(R.id.TaskName);
        EditText taskDescription = view.findViewById(R.id.Description);

        if(TextUtils.getTrimmedLength(taskName.getText()) <= 55
                && TextUtils.getTrimmedLength(taskName.getText()) >0
                && TextUtils.getTrimmedLength(taskDescription.getText()) <= 500
                && TextUtils.getTrimmedLength(taskDescription.getText()) >0 && taskLocation!=null){
            Task task = new Task(name, user, description,taskLocation);

            AddTaskRequest request = new AddTaskRequest(task);
            RequestManager.getInstance().invokeRequest(ctx, request);
            request.getResult();

            taskId = task.getId();

            Intent intent = new Intent();
            intent.putExtra("result", taskId);
            view.setResult(RESULT_OK, intent);
            //Toast.makeText(view, "New task created, refresh page to see updated list", Toast.LENGTH_SHORT).show();
            view.finish();

        } else {
            if (TextUtils.getTrimmedLength(taskName.getText()) > 55) {
                taskName.setError("Name can not exceed 55 characters");
            }

            if (TextUtils.getTrimmedLength(taskName.getText()) == 0) {
                taskName.setError("Name can not empty");
            }

            if (TextUtils.getTrimmedLength(taskDescription.getText()) > 500) {
                taskDescription.setError("Description can not exceed 500 characters");
            }

            if (TextUtils.getTrimmedLength(taskDescription.getText()) == 0) {
                taskDescription.setError("Description can not be empty");
            }
            if (taskLocation==null) {
                Toast.makeText(view, "Add a location to your task",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Cancels making a task and returns to previous activity
     */
    public void cancelTask(){
        view.finish();
    }
    //Add picture and location in part 5
}
