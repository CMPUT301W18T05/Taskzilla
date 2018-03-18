package com.cmput301w18t05.taskzilla.controller;

import android.content.Context;

import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
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
        this.bidRequest = new GetBidsByUserIdRequest(this.bidOwner.getId());
        RequestManager.getInstance().invokeRequest(this.ctx,this.bidRequest);
        //this.resultBidList = this.bidRequest.getResult();

        //this.resultBidList.add(new Bid());
        //resultBidList.add(new Bid());


        /*for (int i = 0; i < getResultBidList().size(); i++) {
            taskRequest = new GetTaskRequest(resultBidList.get(i).getTaskId());
            RequestManager.getInstance().invokeRequest(ctx, taskRequest);
            resultTaskList.add(taskRequest.getResult());
        }*/
    }
}
