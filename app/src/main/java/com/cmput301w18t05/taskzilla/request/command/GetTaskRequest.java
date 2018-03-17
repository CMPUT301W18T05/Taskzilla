package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.Request;

/**
 * Created by wyatt on 07/03/18.
 */

public class GetTaskRequest extends Request {
    ElasticSearchController.GetTask task;
    String taskId;
    Task result;

    public GetTaskRequest(String taskId) {
        this.taskId = taskId;
    }

    public void execute() {
        task = new ElasticSearchController.GetTask();
        task.execute(this.taskId);
    }

    @Override
    public void executeOffline() {

    }

    public Task getResult() {
        try {
            result = this.task.get();
            return result;
        }
        catch (Exception e) {
            return null;
        }
    }
}
