package com.cmput301w18t05.taskzilla.controller;

import android.content.Context;
import android.view.View;

import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.RemoveTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.UpdateTaskRequest;

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
