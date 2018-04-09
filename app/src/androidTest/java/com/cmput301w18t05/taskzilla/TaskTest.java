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
import com.cmput301w18t05.taskzilla.request.RequestManager;
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
        Task testTask = new Task("Task name", user, "Task description");
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
        Task testTask = new Task("Task name", user, "Task description");
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
        Task testTask = new Task("Task name", user, "Task description");
        Bid newBid = new Bid(user.getId(), testTask.getId(), 10.00f);
        testTask.addBid(newBid);

        testTask.removeHighestBid();
        assertEquals(testTask.getBestBid(), -1.0f);
        assertEquals(testTask.getBestBidder(), "");
    }

    // Test to get list of bids on a task
    public void testGetBids() {

        AddUserRequest addUserRequest = new AddUserRequest(user);
        RequestManager.getInstance().invokeRequest(addUserRequest);

        Task testTask = new Task("Task name", user, "Task description");
        AddTaskRequest addTaskRequest = new AddTaskRequest(testTask);
        RequestManager.getInstance().invokeRequest(addTaskRequest);

        Bid newBid = new Bid(user.getId(), testTask.getId(), 10.00f);
        testTask.addBid(newBid);

        Bid newBid2 = new Bid(user.getId(), testTask.getId(), 10.00f);
        testTask.addBid(newBid2);

        assertEquals(2, testTask.numBids());

        ArrayList<Bid> bids = new ArrayList<>();
        bids.add(newBid);
        bids.add(newBid2);

        ArrayList<Bid> result = new ArrayList<>();
        result = testTask.getBids();
        assertTrue(bids.containsAll(result) && result.containsAll(bids));
    }

    // Test to get the task requester user object by userid
    public void testGetTaskRequester() {

        AddUserRequest addUserRequest = new AddUserRequest(user);
        RequestManager.getInstance().invokeRequest(addUserRequest);

        Task testTask = new Task("Task name", user, "Task description");
        testTask.setRequesterId(user.getId());
        AddTaskRequest addTaskRequest = new AddTaskRequest(testTask);
        RequestManager.getInstance().invokeRequest(addTaskRequest);

        User result = new User();
        result = testTask.getTaskRequester();

        assertEquals(user, result);
    }

    // Test to get the task provider user object by userid
    public void testGetTaskProvider() {

        AddUserRequest addUserRequest = new AddUserRequest(user);
        RequestManager.getInstance().invokeRequest(addUserRequest);

        Task testTask = new Task("Task name", user, "Task description");
        testTask.setProviderId(user.getId());
        AddTaskRequest addTaskRequest = new AddTaskRequest(testTask);
        RequestManager.getInstance().invokeRequest(addTaskRequest);

        User result = new User();
        result = testTask.getTaskProvider();

        assertEquals(user, result);
    }

    // Test to set the status of a task
    public void testSetStatus() {
        Task testTask = new Task("Task name", user, "Task description");
        testTask.setStatus("requested");
        assertEquals("requested", testTask.getStatus());

        testTask.setStatus("bidded");
        assertEquals("bidded", testTask.getStatus());
    }

    // Test to update the task
    public void testUpdateThis() {
        AddUserRequest addUserRequest = new AddUserRequest(user);
        RequestManager.getInstance().invokeRequest(addUserRequest);

        Task testTask = new Task("Task name", user, "Task description");
        testTask.setProviderId(user.getId());
        AddTaskRequest addTaskRequest = new AddTaskRequest(testTask);
        RequestManager.getInstance().invokeRequest(addTaskRequest);

        User newProvider = new User();
        AddUserRequest addUserRequest2 = new AddUserRequest(newProvider);
        RequestManager.getInstance().invokeRequest(addUserRequest2);
        testTask.setProviderId(newProvider.getId());
        testTask.updateThis();

        assertEquals(testTask.getTaskProvider(), newProvider);
    }

    // Test to unassign the provider
    public void testUnassignProvider() {
        Task testTask = new Task("Task name", user, "Task description");
        testTask.setTaskProvider(user);

        testTask.unassignProvider();
        assertEquals(testTask.getProviderId(), null);
        assertEquals(testTask.getStatus(), "requested");
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
        Task testTask = new Task("Task name", user, "Task description");
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
        Task testTask = new Task("Task name", user, "Task description");
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
        Task testTask = new Task("Task name", user, "Task description");
        Bid bid1 = new Bid(user.getId(), testTask.getId(), 10.00f);
        assertEquals(new Float(100), testTask.getBestBid());

        Bid bid2 = new Bid(user.getId(), testTask.getId(), 10.00f);
        assertEquals(new Float(200), testTask.getBestBid());
    }
}
