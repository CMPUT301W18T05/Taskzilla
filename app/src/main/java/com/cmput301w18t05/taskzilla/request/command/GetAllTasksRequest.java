package com.cmput301w18t05.taskzilla.request.command;

import android.util.Log;

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

        try {
            result = task.get();
            from += size;
        }
        catch (Exception e) {
            Log.i("Query", "No more results");
        }

    }

    @Override
    public void executeOffline() {
    }

    public ArrayList<Task> getResult() {
        return result;
    }
}
