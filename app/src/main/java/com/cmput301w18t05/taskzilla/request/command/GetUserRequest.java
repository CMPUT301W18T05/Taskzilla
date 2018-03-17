package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.Request;

/**
 * Created by wyatt on 07/03/18.
 */

public class GetUserRequest extends Request {
    ElasticSearchController.GetUser user;
    String userId;
    User result;

    public GetUserRequest(String userId) {
        this.userId = userId;
    }

    public void execute() {
        user = new ElasticSearchController.GetUser();
        user.execute(this.userId);
    }

    @Override
    public void executeOffline() {
    }

    public User getResult() {
        try {
            result = this.user.get();
            return result;
        }
        catch (Exception e) {
            return null;
        }
    }
}
