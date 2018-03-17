package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.Request;

import java.util.ArrayList;

/**
 * Created by wyatt on 07/03/18.
 */

public class GetAllTasksRequest extends Request {
    ElasticSearchController.GetAllTasks task;
    ArrayList<Task> result;

    private int from = 0;
    private int size = 10;

    public GetAllTasksRequest() {
    }

    public void execute() {
        task = new ElasticSearchController.GetAllTasks(from,size);
        task.execute();
    }

    @Override
    public void executeOffline() {
    }

    public ArrayList<Task> getResult() {
        try {
            result = this.task.get();
            from += size;
            return result;
        }
        catch (Exception e) {
            return null;
        }
    }
}
