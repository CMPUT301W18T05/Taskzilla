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
 * Request for getting a bid object from Elastic Search using a task id
 * @author Wyatt
 * @see ElasticSearchController
 * @version 1.0
 *
 */
public class GetBidsByTaskIdRequest extends Request {
    ElasticSearchController.GetBidsByTaskID task;
    private ArrayList<Bid> result;
    private String taskId;

    public GetBidsByTaskIdRequest(String taskId) {
        this.taskId = taskId;
    }

    public void execute() {
        task = new ElasticSearchController.GetBidsByTaskID();
        task.execute(this.taskId);
    }

    @Override
    public void executeOffline() {
        executedOffline = false;
        AppCache appCache = AppCache.getInstance();
        result = appCache.findCachedBidsByTaskid(taskId);
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
            return this.result;
        }
    }
}

