package com.cmput301w18t05.taskzilla;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Jeremy on 2018-02-23.
 */

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest(){
        super(MainActivity.class);
    }

    /**
     * Test for setting the bid
     */
    public void testSetBid() {

        User user = new User();
        Task task = new Task();
        float bidAmount = 10.00;
        Bid bid = new Bid(user, task, bidAmount);
        assertEquals(bid.getBidAmount(), bidAmount);
    }

    /**
     * Test for comparing a bid to another bid
     *
     * greater than returns 1
     * equality returns 0
     * less than returns -1
     */
    public void testCompareTo() {

        User user1 = new User();
        Task task = new Task();
        float bidAmount1 = 10.00;
        Bid bid1 =  new Bid(user1, task, bidAmount1);

        User user2 = new User();
        float bidAmount2 = 1.00;
        Bid bid2 =  new Bid(user2, task, bidAmount2);
        assertEquals(bid1.compareTo(bid2), 1);

        User user3 = new User();
        float bidAmount3 = 10.00;
        Bid bid3 =  new Bid(user3, task, bidAmount3);
        assertEquals(bid1.compareTo(bid3), 0);

        User user4 = new User();
        float bidAmount4 = 20.00;
        Bid bid4 =  new Bid(user4, task, bidAmount4);
        assertEquals(bid1.compareTo(bid4), -1);
    }
}