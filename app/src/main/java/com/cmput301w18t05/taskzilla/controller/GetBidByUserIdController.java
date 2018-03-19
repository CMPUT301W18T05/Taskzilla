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

package com.cmput301w18t05.taskzilla.controller;

import android.content.Context;

import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetBidsByUserIdRequest;

import java.util.ArrayList;

/**
 * Created by James on 3/16/2018.
 *
 */

public class GetBidByUserIdController {

    private ArrayList<Bid> resultBidList;
    private User bidOwner;
    private GetBidsByUserIdRequest bidRequest;
    private Context ctx;

    public GetBidByUserIdController(Context context, User user) {
        this.resultBidList = new ArrayList<>();
        this.bidOwner = user;
        this.ctx = context;
    }

    public ArrayList<Bid> getResultBidList(){
        return this.resultBidList;
    }

    public void doTaskRequest() {
        bidRequest = new GetBidsByUserIdRequest(bidOwner.getId());
        RequestManager.getInstance().invokeRequest(ctx,bidRequest);
        resultBidList = bidRequest.getResult();
    }
}
