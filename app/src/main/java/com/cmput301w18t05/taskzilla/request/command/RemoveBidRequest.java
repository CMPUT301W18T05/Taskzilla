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
