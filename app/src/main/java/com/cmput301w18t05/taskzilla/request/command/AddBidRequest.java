package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.request.InsertionRequest;

/**
 * Created by wyatt on 07/03/18.
 */

public class AddBidRequest extends InsertionRequest {
    ElasticSearchController.AddBid task;
    Bid bid;

    public AddBidRequest(Bid bid) {
        this.bid = bid;
    }

    public void execute() {
        task = new ElasticSearchController.AddBid();
        task.execute(bid);
    }

    @Override
    public void executeOffline() {
    }

    public void getResult() {

    }

}
