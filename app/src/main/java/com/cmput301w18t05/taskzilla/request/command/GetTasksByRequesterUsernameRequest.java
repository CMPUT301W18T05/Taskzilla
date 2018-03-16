package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.Request;

import java.util.ArrayList;

/**
 * Created by wyatt on 07/03/18.
 */

public class GetTasksByRequesterUsernameRequest extends Request {
    ElasticSearchController.GetTasksByRequesterUsername task;
    ArrayList<Task> result;

    public GetTasksByRequesterUsernameRequest(String username) {
    }

    public void execute() {
        task = new ElasticSearchController.GetTasksByRequesterUsername();
        task.execute();
    }

    @Override
    public void executeOffline() {
    }

    public ArrayList<Task> getResult() {
        try {
            result = this.task.get();
            return result;
        }
        catch (Exception e) {
            return null;
        }
    }
}
