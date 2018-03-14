package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.InsertionRequest;

/**
 * Created by wyatt on 07/03/18.
 */

public class AddTaskRequest extends InsertionRequest {
    private Task task;

    public AddTaskRequest(Task task) {
        this.task = task;
    }

    public void execute() {
    }

    public void executeOffline() {
    }

}
