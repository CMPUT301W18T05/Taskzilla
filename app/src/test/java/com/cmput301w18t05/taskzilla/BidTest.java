package com.cmput301w18t05.taskzilla;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Jeremy on 2018-02-23.
 */

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest(){
        super(MainActivity.class);
    }

    public void testSetBid() {
        User user = new User();
        Task task = new Task();
        double bidAmount = 10.00;
        Bid bid = new Bid(bidAmount, user, task);


    }
}
