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

package com.cmput301w18t05.taskzilla.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.controller.NewTaskController;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.currentUser;

/**
 * Activity for creating a new task
 */
public class NewTaskActivity extends AppCompatActivity {

    private NewTaskController newTaskController;
    private User cUser = currentUser.getInstance();

    /**
     * Activity uses the activity_new_task.xml layout
     * New tasks are created through NewTaskController
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Add a Task");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        newTaskController =  new NewTaskController(this, getApplicationContext());
        Button cancel =  findViewById(R.id.CancelButton);
        Button addTask = findViewById(R.id.addTaskButton);
        final EditText taskName = findViewById(R.id.TaskName);
        final EditText taskDescription = findViewById(R.id.Description);

        /* cancel button */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskController.cancelTask();
            }
        });

        /* add task button */
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskController.addTask(taskName.getText().toString(),cUser, taskDescription.getText().toString());
            }
        });

    }
}
