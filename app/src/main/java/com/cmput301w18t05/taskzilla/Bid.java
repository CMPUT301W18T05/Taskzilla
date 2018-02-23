package com.cmput301w18t05.taskzilla;

/**
 * Created by wyatt on 23/02/18.
 */

public class Bid implements Comparable<Bid> {

    private float quote;
    private int requesterId;

    public Bid(User user, float quote) {
        this.quote = quote;
        this.requesterId = user.getID();
    }

    public float getQuote() {
        return this.quote;
    }

    public int compareTo(Bid bid) {
        if (this.quote > bid.getQuote())
            return 1;
        else if (this.quote == bid.getQuote())
            return 0;
        else
            return -1;
    }

}
