package com.cmput301w18t05.taskzilla.request.command;

import android.util.Log;

import com.cmput301w18t05.taskzilla.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.Request;

/**
 * Created by kio22 on 2018-03-16.
 */

public class UpdateTaskRequest extends Request {
    ElasticSearchController.UpdateTask updateRequest;
    private String taskId;
    private Task taskData;

    public UpdateTaskRequest(Task task) {
        this.taskData = task;
    }

    public void execute() {
        updateRequest = new ElasticSearchController.UpdateTask();
        updateRequest.execute(taskData);
    }

    public void executeOffline(){}

}
