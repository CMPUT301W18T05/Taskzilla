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

package com.cmput301w18t05.taskzilla;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;


import java.text.DecimalFormat;

import io.searchbox.annotations.JestId;

/**
 * Represents a bid object in the app
 * @author myapplestory
 */
public class Bid implements Comparable<Bid> {

    private float bidAmount;

    @JestId
    private String id;

    private String userId;
    private String taskId;

    public Bid(){}

    /**
     * Constructs a bid object using the given parameters
     *
     * @param userId user id of the bidder
     * @param taskId task id of the task being bidded to
     * @param bidAmount amount of the bid
     */
    public Bid(String userId, String taskId, float bidAmount) {
        this.userId = userId;
        this.taskId = taskId;
        this.bidAmount = bidAmount;
    }

    /**
     * Returns the id of the user
     * @return this.userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns the price of the bid
     * @return this.bidAmount
     */
    public float getBidAmount() {
        return this.bidAmount;
    }

    /**
     * Returns the id of the bid
     * @return the bidId
     */
    public String getId() {
        return id;
    }

    /**
     * return the id of the task being bidded on
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * set the id of the bid
     * @param id the id to set this bid to
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Compares two bids to see which one has the higher price
     * @param bid The bid being compared to
     * @return Returns: 1 if bid is larger than the bid being compared to. 0 = if the two bids are the same price. -1 if bid is lower than the bid being compared to
     */
    public int compareTo(Bid bid) {
        if (this.bidAmount > bid.getBidAmount())
            return 1;
        else if (this.bidAmount == bid.getBidAmount())
            return 0;
        else
            return -1;
    }

    /**
     * Returns a string with the bid details
     * @return the string which is with the details of the bid
     */
    public String toString() {
        GetTaskRequest taskRequest = new GetTaskRequest(this.taskId);
        RequestManager.getInstance().invokeRequest(taskRequest);
        DecimalFormat cents = new DecimalFormat("#0.00");
        //return String.format("%-20s \n\n%-45s%s", this.name, this.startDate, ("$" + cents.format(this.price)));
        return "Task: " + taskRequest.getResult().getName() +
                "\nBid amount: $"  + cents.format(this.bidAmount);
    }
}
