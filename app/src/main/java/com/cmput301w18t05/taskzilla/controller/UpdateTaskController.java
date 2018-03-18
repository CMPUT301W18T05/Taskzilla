package com.cmput301w18t05.taskzilla.controller;

import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.UpdateTaskRequest;

/**
 * Created by myapplestory on 3/17/2018.
 *
 */

public class UpdateTaskController {
    private UpdateTaskRequest request;
    private Task task;

    public UpdateTaskController(Task task){
        this.task = task;
    }

    public Task getUpdatedTask() {
        return this.task;
    }

    public void doRequest() {
        request = new UpdateTaskRequest(this.task);
        RequestManager.getInstance().invokeRequest(request);
        this.task = request.getResult();
    }



}
