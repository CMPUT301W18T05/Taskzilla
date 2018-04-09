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
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByProviderUsernameRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByRequesterUsernameRequest;
import com.cmput301w18t05.taskzilla.request.command.GetUserRequest;

import java.util.ArrayList;

/**
 * Controller for pro
 * @author Micheal
 * @see com.cmput301w18t05.taskzilla.fragment.ProfileFragment
 * @version 1.0
 */
public class ProfileController {
    private Context ctx;
    private View view;
    private String userId;
    private User user;
    private ArrayList<Task> taskList;
    private GetTasksByRequesterUsernameRequest requestTasksRequester;
    private GetTasksByProviderUsernameRequest requestTasksProvider;
    private Integer tasksDone;

    /**
     * Context and view of the activity is passed into the controller
     * @param v
     * @param context
     */
    public ProfileController(View v, Context context) {
        this.ctx = context;
        this.view = v;
        this.userId = new String();
    }

    /**
     * Sets the id of the user
     * @param id
     */
    public void setUserID(String id) {
        this.userId = id;
    }

    /**
     * Returns the id of the user
     * @return
     */
    public User getUser() {
        return this.user;
    }

    /**
     * getUserRequest
     * get user from RequestManager and set the
     * user to be the result for the controller
     *
     * @author Micheal-Nguyen
     */

    public void updateUserRequest(User user) {
        AddUserRequest request = new AddUserRequest(user);
        RequestManager.getInstance().invokeRequest(ctx, request);
    }

    public void getUserRequest() {
        GetUserRequest request = new GetUserRequest(userId);
        RequestManager.getInstance().invokeRequest(ctx, request);

        this.user = request.getResult();
    }


    /**
     * getNumberOfRequests
     * get the number of tasks that the user
     * has requested
     *
     * @param username
     * @return number of tasks requested by user
     */
    public String getNumberOfRequests(String username) {
        requestTasksRequester = new GetTasksByRequesterUsernameRequest(username);
        RequestManager.getInstance().invokeRequest(ctx, requestTasksRequester);
        Integer tempNumRequests = requestTasksRequester.getResult().size();
        Integer numRequestsInteger = tempNumRequests;
        while(tempNumRequests>0){
            RequestManager.getInstance().invokeRequest(ctx, requestTasksRequester);
            tempNumRequests = requestTasksRequester.getResult().size();
            numRequestsInteger+=tempNumRequests;
        }
        return Integer.toString(numRequestsInteger);


    }

    /**
     * getNumberOfTasksDone
     * get the number of tasks that the user
     * has provided and completed for another user
     *
     * @param username
     * @return number of tasks done by the user
     */
    public String getNumberOfTasksDone(String username) {
        requestTasksProvider = new GetTasksByProviderUsernameRequest(username);
        RequestManager.getInstance().invokeRequest(ctx, requestTasksProvider);
        taskList = new ArrayList<Task>();
        this.taskList.addAll(requestTasksProvider.getResult());
        tasksDone = 0;
        for(Task task: taskList) {
            if(task.getStatus().equals("Completed")){
                tasksDone++;
            }
        }
        while(taskList.size()>0 && taskList != null){
            taskList.clear();
            RequestManager.getInstance().invokeRequest(ctx, requestTasksProvider);
            this.taskList.addAll(requestTasksProvider.getResult());
            for(Task task: taskList) {
                if(task.getStatus().equals("Completed")){
                    tasksDone++;
                }
            }
        }
        return Integer.toString(tasksDone);
    }

}
