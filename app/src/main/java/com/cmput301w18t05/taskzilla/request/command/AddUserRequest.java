package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.ElasticSearchController;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.InsertionRequest;

/**
 * Created by wyatt on 07/03/18.
 */

public class AddUserRequest extends InsertionRequest {
    User user;

    public AddUserRequest(User user) {
        this.user = user;
    }

    public void execute() {
        ElasticSearchController ESC = new ElasticSearchController();
        ESC.addUser(this.user);
    }

    public void executeOffline() {
    }

}
