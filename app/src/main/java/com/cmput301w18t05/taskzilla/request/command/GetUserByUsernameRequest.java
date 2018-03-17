package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.Request;

/**
 * Created by wyatt on 07/03/18.
 */

public class GetUserByUsernameRequest extends Request {
    ElasticSearchController.GetUserByUsername task;
    String username;
    User result;

    public GetUserByUsernameRequest(String username) {
        this.username = username;
    }

    public void execute() {
        task = new ElasticSearchController.GetUserByUsername();
        task.execute(this.username);
    }

    @Override
    public void executeOffline() {
    }

    public User getResult() {
        if (result != null) {
            return result;
        }
        try {
            result = this.task.get();
            return result;
        }
        catch (Exception e) {
            return null;
        }
    }
}
