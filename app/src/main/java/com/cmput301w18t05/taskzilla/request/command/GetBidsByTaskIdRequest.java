package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.request.Request;

import java.util.ArrayList;

/**
 * Created by wyatt
 */

public class GetBidsByTaskIdRequest extends Request {
    ElasticSearchController.GetBidsByTaskID task;
    private ArrayList<Bid> result;
    private String taskId;

    public GetBidsByTaskIdRequest(String taskId) {
        this.taskId = taskId;
        this.result = new ArrayList<>();
    }

    public void execute() {
        task = new ElasticSearchController.GetBidsByTaskID();
        task.execute(this.taskId);
    }

    @Override
    public void executeOffline() {
    }

    public ArrayList<Bid> getResult() {
        try {
            this.result.addAll(this.task.get());
            return this.result;
        }
        catch (Exception e) {
            return this.result;
        }
    }
}

