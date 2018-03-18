package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.request.DeletionRequest;

/**
 * Created by myapp on 3/17/2018.
 *
 */

public class RemoveBidRequest extends DeletionRequest{
    private Bid bid;

    public RemoveBidRequest(Bid bid) {
        this.bid = bid;
    }

    public void execute(){
        ElasticSearchController.RemoveBid deleteRequest = new ElasticSearchController.RemoveBid();
        deleteRequest.execute(this.bid);
    }

    @Override
    public void executeOffline() {
    }

    public void getResult() {

    }

}
