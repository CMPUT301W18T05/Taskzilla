package com.cmput301w18t05.taskzilla;

import android.content.Context;

import com.cmput301w18t05.taskzilla.request.Request;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetBidsByUserIdRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;

import java.util.ArrayList;

/**
 * Created by James on 3/16/2018.
 *
 */

public class GetBidByUserIdController {

    private ArrayList<Bid> resultBidList;
    private ArrayList<Task> resultTaskList;
    private User bidOwner;
    private GetBidsByUserIdRequest bidRequest;
    private GetTaskRequest taskRequest;
    private Context ctx;

    public GetBidByUserIdController(Context context, User user) {
        this.resultBidList = new ArrayList<>();
        this.bidOwner = user;
        this.ctx = context;
    }

    public ArrayList<Bid> getResultBidList(){
        return this.resultBidList;
    }

    public ArrayList<Task> getResultTaskList() {
        return this.resultTaskList;
    }

    public void doTaskRequest() {
        bidRequest = new GetBidsByUserIdRequest(bidOwner.getId());
        RequestManager.getInstance().invokeRequest(ctx,bidRequest);
        resultBidList = bidRequest.getResult();

        for (int i = 0; i < getResultBidList().size(); i++) {
            taskRequest = new GetTaskRequest(resultBidList.get(i).getTaskId());
            RequestManager.getInstance().invokeRequest(ctx, taskRequest);
            resultTaskList.add(taskRequest.getResult());
        }
    }
}
