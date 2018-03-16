package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.InsertionRequest;

/**
 * Created by wyatt on 07/03/18.
 */

public class AddTaskRequest extends InsertionRequest {
    private ElasticSearchController.AddTask task;
    private Task taskData;

    public AddTaskRequest(Task task) {
        this.taskData = task;
    }

    @Override
    public void execute() {
        task = new ElasticSearchController.AddTask();
        task.execute(taskData); // for now, subject to change.
    }

    @Override
    public void executeOffline() {
    }

    public boolean getResult() {
        try {
            return task.get();
        }
        catch (Exception e) {
            return false;
        }
    }

}
