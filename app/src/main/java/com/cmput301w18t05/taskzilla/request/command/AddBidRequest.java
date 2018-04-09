/*
 * Copyright 2018 (c) Andy Li, Colin Choi, James Sun, Jeremy Ng, Micheal Nguyen, Wyatt Praharenka
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.AppCache;
import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.request.InsertionRequest;

/**
 * Request for adding bids to elastic search
 * @author praharen
 * @see ElasticSearchController
 */
public class AddBidRequest extends InsertionRequest {
    ElasticSearchController.AddBid task;
    Bid bid;

    public AddBidRequest(Bid bid) {
        this.bid = bid;
        queueReady = true;
    }

    /**
     * Add bid to Elasticsearch
     */
    @Override
    public void execute() {
        this.bid.setId(null);
        System.out.println("Adding bid: " + bid.getId());
        System.out.println("Bid id: " + bid.getId() + " Task id: " + bid.getTaskId() + " User id: " + bid.getUserId());
        task = new ElasticSearchController.AddBid();
        task.execute(bid);
    }

    /**
     * add the bid to the AppCache
     */
    @Override
    public void executeOffline() {
        AppCache.getInstance().addInCache(bid);
    }

    /**
     * add bid does not require a connection
     * @return
     */
    @Override
    public boolean requiresConnection() {
        return false;
    }

    /**
     * get result, for consistencies sake.
     */
    public void getResult() {
    }
}
