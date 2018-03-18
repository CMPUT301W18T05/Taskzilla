package com.cmput301w18t05.taskzilla;

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
       return "asdasdasd";
        /// / return String.format(Locale.CANADA,"%f", this.bidAmount);
    }
}
