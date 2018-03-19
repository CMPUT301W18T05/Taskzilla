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

import java.util.Locale;

import io.searchbox.annotations.JestId;

public class Bid implements Comparable<Bid> {

    private float bidAmount;

    @JestId
    private String id;

    private String userId;
    private String taskId;

    public Bid(){}

    public Bid(String userId, String taskId, float bidAmount) {
        this.userId = userId;
        this.taskId = taskId;
        this.bidAmount = bidAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getBidAmount() {
        return this.bidAmount;
    }

    public String getId() {
        return id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBidAmount(float bidAmount) {
        this.bidAmount = bidAmount;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int compareTo(Bid bid) {
        if (this.bidAmount > bid.getBidAmount())
            return 1;
        else if (this.bidAmount == bid.getBidAmount())
            return 0;
        else
            return -1;
    }

    public String toString() {
        GetTaskRequest taskRequest = new GetTaskRequest(this.taskId);
        RequestManager.getInstance().invokeRequest(taskRequest);
        return "Task: " + taskRequest.getResult().getName() +
                "\nBid amount: $" + Float.toString(this.bidAmount);
    }
}
