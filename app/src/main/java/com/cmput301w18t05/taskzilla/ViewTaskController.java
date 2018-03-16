package com.cmput301w18t05.taskzilla;

import android.content.Context;
import android.view.View;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;

/**
 * Created by Andy on 3/16/2018.
 */

public class ViewTaskController {
    private Context ctx;
    private View view;
    private String taskID;
    private Task task;

    public ViewTaskController(View v, Context context) {
        this.ctx = context;
        this.view = v;
        this.taskID = new String();
    }

    public void setTaskID(String id) {
        this.taskID = id;
    }

    public Task getTask() {
        return this.task;
    }

    public void getTaskRequest() {
        GetTaskRequest request = new GetTaskRequest(taskID);
        RequestManager.getInstance().invokeRequest(ctx, request);

        this.task = request.getResult();
    }

}
