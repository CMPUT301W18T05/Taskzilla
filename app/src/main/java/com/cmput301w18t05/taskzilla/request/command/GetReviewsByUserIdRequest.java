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

import com.cmput301w18t05.taskzilla.Review;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.request.Request;

import java.util.ArrayList;

/**
 * Request for getting a task object from Elastic Search using the username of the requester
 * @author Wyatt
 * @see ElasticSearchController
 * @version 1.0
 */
public class GetReviewsByUserIdRequest extends Request {
    private ElasticSearchController.GetReviewsByUserId task;
    private ArrayList<Review> result;
    private String userId;
    private boolean executedOffline = false;
    private boolean executedOfflineOnce = false;
    private int from = 0;
    private int size = 10;

    public GetReviewsByUserIdRequest(String userId) {
        this.userId = userId;
    }

    /**
     * get all reviews with this user id from elasticsearch
     */
    public void execute() {
        //System.out.println("Getting reviews by user ID: " + userId);
        task = new ElasticSearchController.GetReviewsByUserId();
        task.execute(userId);
    }

    @Override
    public void executeOffline() {
    }

    @Override
    public boolean requiresConnection() {
        return false;
    }

    public ArrayList<Review> getResult() {
        result = new ArrayList<>();
        try {
            result = task.get();
            return result;
        }
        catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
