package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.InsertionRequest;

/**
 * Created by wyatt on 07/03/18.
 */

public class AddUserRequest extends InsertionRequest {
    User user;
    ElasticSearchController.AddUser task;

    public AddUserRequest(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        task = new ElasticSearchController.AddUser();
        task.execute(user); // for now, subject to change.
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
