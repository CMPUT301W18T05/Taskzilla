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

    /*
    public int Bid(User user, float bidAmount) {
        if (bidAmount < 0.0f) {
            return -1;
        }
        this.bidAmount = bidAmount;
        this.provider = user.getId();
        return 0;
    }
    public Bid(User user, double bidAmount) {
        this(user,new Float(10.0));
    }
    */

    public Bid(User user, Task task, float bidAmount) {
        this.bidAmount = bidAmount;
        this.requester = task.getTaskRequester().getId();
        this.taskName = task.getId();
        this.provider = user.getId();
    }

    public Bid(User user, double amt) {
        this.provider = user.getId();
        this.bidAmount = new Float(amt);
    }

    public float getBidAmount() {
        return this.bidAmount;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setBidAmount(float bidAmount) {
        this.bidAmount = bidAmount;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
