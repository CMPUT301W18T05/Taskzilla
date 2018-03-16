package com.cmput301w18t05.taskzilla;

import io.searchbox.annotations.JestId;

/**
 * Created by wyatt on 23/02/18.
 */

public class Bid implements Comparable<Bid> {

    private float bidAmount;

    @JestId
    private String id;

    private String requesterId;
    private String providerId;
    private String taskId;

    /*
    public int Bid(User user, float bidAmount) {
        if (bidAmount < 0.0f) {
            return -1;
        }
        this.bidAmount = bidAmount;
        this.providerId = user.getId();
        return 0;
    }
    public Bid(User user, double bidAmount) {
        this(user,new Float(10.0));
    }
    */

    public Bid(User user, Task task, float bidAmount) {
        this.bidAmount = bidAmount;
        this.requesterId = task.getTaskRequester().getId();
        this.taskId = task.getId();
        this.providerId = user.getId();
    }

    public Bid(User user, double amt) {
        this.providerId = user.getId();
        this.bidAmount = new Float(amt);
    }

    public float getBidAmount() {
        return this.bidAmount;
    }

    public String getId() {
        return id;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public String getProviderId() {
        return providerId;
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

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
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
        return "test bid stuff";
    }



}
