package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.Request;

import java.util.ArrayList;

/**
 * Created by wyatt on 07/03/18.
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
            result = this.task.get();
            from += size;
            return result;
        }
        catch (Exception e) {
            return null;
        }
    }
}
