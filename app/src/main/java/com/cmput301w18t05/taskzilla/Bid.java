package com.cmput301w18t05.taskzilla;

/**
 * Created by wyatt on 23/02/18.
 */

public class Bid implements Comparable<Bid> {

    private float bidAmount;
    private String id;
    private String requester;
    private String provider;
    private String taskName;

    public Bid(User user, float bidAmount) {
        this.bidAmount = bidAmount;
    }
    public Bid(User user, double bidAmount) {
        this(user,new Float(10.0));
    }

    public Bid(User user, Task task, float bidAmount) {
        this.bidAmount = bidAmount;
        //this.requesterID = user.getId();
        //this.taskID = task.getId();
    }

    public float getBidAmount() {
        return this.bidAmount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getRequester() {
        return requester;
    }

    public String getProvider() {
        return provider;
    }

    public String getTaskName() {
        return taskName;
    }

    public int compareTo(Bid bid) {
        if (this.bidAmount > bid.getBidAmount())
            return 1;
        else if (this.bidAmount == bid.getBidAmount())
            return 0;
        else
            return -1;
    }

}
