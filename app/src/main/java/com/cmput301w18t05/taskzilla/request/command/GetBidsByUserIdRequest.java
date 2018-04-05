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
import com.cmput301w18t05.taskzilla.request.Request;

import java.util.ArrayList;


/**
 * Request for getting an user object from elastic search using a user id
 * @author Wyatt
 * @see ElasticSearchController
 * @version 1.0
 */
public class GetBidsByUserIdRequest extends Request {
    ElasticSearchController.GetBidsByUserID task;
    private String userId;
    private ArrayList<Bid> result;
    private boolean executedOffline = false;

    public GetBidsByUserIdRequest(String userId) {
        this.userId = userId;
    }

    public void execute() {
        task = new ElasticSearchController.GetBidsByUserID();
        task.execute(this.userId);
    }

    @Override
    public void executeOffline() {
        executedOffline = true;
        AppCache appCache = AppCache.getInstance();
        result = appCache.findCachedBidsByUserid(userId);
    }

    @Override
    public boolean requiresConnection() {
        return false;
    }

    public ArrayList<Bid> getResult() {
        try {
            if (!executedOffline) {
                result = task.get();
                AppCache.getInstance().addBidsToCache(result);
            }
            return result;
        }
        catch (Exception e) {
            return null;
        }
    }
}

