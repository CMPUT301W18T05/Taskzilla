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

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;

import java.util.ArrayList;

/**
 * TaskTest
 * unit test for Task class
 *
 * Created by wyatt on 23/02/18.
 *
 * @author praharen
 */
public class TaskTest extends ActivityInstrumentationTestCase2 {

    private User user;

    public TaskTest() {
        super(MainActivity.class);
        user = new User();
    }

    /**
     * add bid test
     *
     * test if bids are succesfully added
     * test sorted order of bids list in task
     * @author praharen
     */

    public void testAddBid() {
        Task testTask = new Task();
        assertEquals(0, testTask.numBids());
        Bid newBid = new Bid(user.getId(), testTask.getId(), 10.00f);
        testTask.addBid(newBid);
        assertEquals(1, testTask.numBids());
        Bid newBid2 = new Bid(user.getId(), testTask.getId(), 10.00f);
        testTask.addBid(newBid2);
        assertEquals(2, testTask.numBids());
        assertTrue(testTask.getBid(0).compareTo(testTask.getBid(1)) < 0);
    }

    // Test to remove all bids
    public void testRemoveAllBids() {
        Task testTask = new Task();
        Bid newBid = new Bid(user.getId(), testTask.getId(), 10.00f);
        testTask.addBid(newBid);
        Bid newBid2 = new Bid(user.getId(), testTask.getId(), 10.00f);
        testTask.addBid(newBid2);
        assertEquals(2, testTask.numBids());

        testTask.removeAllBids();
        assertEquals(0, testTask.numBids());
    }

    // Test to remove best bid
    public void testRemoveHighestBid() {
        Task testTask = new Task();
        Bid newBid = new Bid(user.getId(), testTask.getId(), 10.00f);
        testTask.addBid(newBid);

        testTask.removeHighestBid();
        assertEquals(testTask.getBestBid(), -1.0f);
        assertEquals(testTask.getBestBidder(), "");
    }

    // Test to get bids
    public void testGetBids() {

        AddUserRequest addUserRequest = new AddUserRequest(user);

        Task testTask = new Task();
        AddTaskRequest addTaskRequest = new AddTaskRequest(testTask);

        Bid newBid = new Bid(user.getId(), testTask.getId(), 10.00f);
        testTask.addBid(newBid);

        Bid newBid2 = new Bid(user.getId(), testTask.getId(), 10.00f);
        testTask.addBid(newBid2);

        assertEquals(2, testTask.numBids());

        ArrayList<Bid> bids = new ArrayList<>();
        bids.add(newBid);
        bids.add(newBid2);

        ArrayList<Bid> result = new ArrayList<>();
        assertTrue(bids.containsAll(result) && result.containsAll(bids));
    }
    /**
     * set/get location test
     *
     * Getting current location will return null
     * Add location, ensure the location is added
     * Adding second location will overwrite the current location
     * @author praharen
     */

    /*
    public void testSetGetLocation() {
        Location testLocation = new Location("");
        Task testTask = new Task();

        assertEquals(null, testTask.getLocation());

        testTask.setLocation(testLocation);
        assertEquals(testLocation, testTask.getLocation());

        Location testNewLocation = new Location("");
        testTask.setLocation(testNewLocation);

        assertEquals(testNewLocation,testTask.getLocation());
    }
*/

    /**
     * get/set status test
     *
     * @author praharen
     */
    /*
    public void testGetSetStatus() {
        Task testTask = new Task();
        String testStatus = "Done";

        assertEquals(null,testTask.getStatus());

        testTask.setStatus(testStatus);
        assertEquals(testStatus, testTask.getStatus());
    }
    */

    /**
     * get/set task name test
     *
     * @author praharen
     */
    public void testAddName() {
        Task testTask = new Task();
        String name = "test";
        testTask.setName(name);
        assertEquals(name, testTask.getName());
    }

    /**
     * get/set description test
     *
     * @author praharen
     */
    public void testGetSetDescription() {
        Task testTask = new Task();
        String desc = "test";
        testTask.setDescription(desc);
        assertEquals(desc, testTask.getDescription());
    }

    /** get/set set provider test
     *
     * @author praharen
     */
    /*
    public void testGetSetProvider() {
        Task testTask = new Task();
        user = new User();
        assertEquals(null, testTask.getTaskProvider());
        testTask.setTaskProvider(user);
        assertEquals(user, testTask.getTaskProvider());
    }*/

    /** get/set set provider test
     *
     * @author praharen
     */
    /*
    public void testGetSetRequester() {
        Task testTask = new Task();
        user = new User();
        assertEquals(null, testTask.getTaskProvider());
        testTask.setTaskRequester(user);
        assertEquals(user, testTask.getTaskRequester());
    }*/

    /**
     * add photo test
     *
     * @author prahare
     */
    /*
    public void testAddPhoto() {
        Task testTask = new Task();
        byte test[] = {1};
        Photo testPhoto = new Photo(test);
        testTask.addPhoto(testPhoto);
        assertEquals(test, testTask.getPhoto(0));
    }
    */
    /**
     * getBestBid test
     */

    public void testGetBestBid() {
        Task testTask = new Task();
        Bid bid1 = new Bid(user.getId(), testTask.getId(), 10.00f);
        assertEquals(new Float(100), testTask.getBestBid());

        Bid bid2 = new Bid(user.getId(), testTask.getId(), 10.00f);
        assertEquals(new Float(200), testTask.getBestBid());
    }
}
