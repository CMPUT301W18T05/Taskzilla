package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.request.Request;

import java.util.ArrayList;

/**
 * Created by wyatt on 07/03/18.
 */

public class GetBidsByUserIdRequest extends Request {
    ElasticSearchController.GetBidsByUserID task;
    private String userId;
    private ArrayList<Bid> result;

    public GetBidsByUserIdRequest(String userId) {
        this.userId = userId;
    }

    public void execute() {
        task = new ElasticSearchController.GetBidsByUserID();
        task.execute(this.userId);
    }

    @Override
    public void executeOffline() {
    }

    public ArrayList<Bid> getResult() {
        try {
            this.result = this.task.get();
            return this.result;
        }
        catch (Exception e) {
            return null;
        }
    }
}

