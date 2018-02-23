package com.cmput301w18t05.taskzilla;

/**
 * Created by wyatt on 23/02/18.
 */

public class Bid implements Comparable<Bid> {

    private float bidAmount;
    private int requesterID;
    private int taskID;
    private float quote;
    private int providerID;


    public Bid(User user, float bidAmount) {
        this.bidAmount = bidAmount;
<<<<<<< HEAD
        this.requesterID = user.getId();
        this.taskID = task.getId();
=======
        //this.requesterID = user.getID();
        //this.taskID = task.getID();
>>>>>>> 34b19088c0e7e4600a1287c1dce845e00646920d
    }

    public float getBidAmount() {
        return this.bidAmount;
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
