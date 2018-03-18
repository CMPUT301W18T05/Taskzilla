package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
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

    public void executeOffline() {}

    public Task getResult() {
        try {
            //this.taskData = this.updateRequest.get();
            return taskData;
        }
        catch (Exception e) {
            return null;
        }    }
}
