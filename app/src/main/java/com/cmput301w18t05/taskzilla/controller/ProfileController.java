package com.cmput301w18t05.taskzilla.controller;

import android.content.Context;
import android.view.View;

import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.GetUserRequest;
import com.cmput301w18t05.taskzilla.request.command.UpdateTaskRequest;

/**
 * Created by Micheal-Nguyen on 3/16/2018.
 */

public class ProfileController {
    private Context ctx;
    private View view;
    private String userId;
    private User user;

    public ProfileController(View v, Context context) {
        this.ctx = context;
        this.view = v;
        this.userId = new String();
    }

    public void setUserID(String id) {
        this.userId = id;
    }

    public User getUser() {
        return this.user;
    }

    public void getUserRequest() {
        GetUserRequest request = new GetUserRequest(userId);
        RequestManager.getInstance().invokeRequest(ctx, request);

        this.user = request.getResult();
    }

}
