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
    ArrayList<Bid> result;

    private int from = 0;
    private int size = 10;
    private String taskId;

    public GetBidsByTaskIdRequest(String taskId) {
        this.taskId = taskId;
    }

    public void execute() {
        task = new ElasticSearchController.GetBidsByTaskID();
        task.execute(taskId);
    }

    @Override
    public void executeOffline() {
    }

    public ArrayList<Bid> getResult() {
        try {
            this.result = task.get();
            return this.result;
        }
        catch (Exception e) {
            return null;
        }
    }
}
