package com.cmput301w18t05.taskzilla.request.command;

import android.util.Log;

import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.Request;

import java.util.ArrayList;

/**
 * Created by wyatt on 07/03/18.
 */

public class GetTasksByRequesterUsernameRequest extends Request {
    ElasticSearchController.GetTasksByRequesterUsername task;
    ArrayList<Task> result;
    String user;

    public GetTasksByRequesterUsernameRequest(String username) {
        this.user = username;
    }

    public void execute() {
        task = new ElasticSearchController.GetTasksByRequesterUsername();
        task.execute(user);
    }

    @Override
    public void executeOffline() {
    }

    public ArrayList<Task> getResult() {
        try {
            result = this.task.get();

            for(Task t:result)
                Log.i("Result",t.getId());

            return result;
        }
        catch (Exception e) {
            return null;
        }
    }
}
