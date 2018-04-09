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

import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.request.Request;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddBidRequest;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;

import java.text.DecimalFormat;


/**
 * Created by Jeremy
 */

public class BidTest extends ActivityInstrumentationTestCase2 {
    public BidTest(){
        super(MainActivity.class);
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
        AddUserRequest addUserRequest = new AddUserRequest(user1);
        RequestManager.getInstance().invokeRequest(getActivity(),addUserRequest);
        Task task = new Task("Task name", user1, "Task description");
        AddTaskRequest addTaskRequest = new AddTaskRequest(task);
        RequestManager.getInstance().invokeRequest(getActivity(), addTaskRequest);
        float bidAmount1 = 10.00f;
        Bid bid1 = new Bid(user1.getId(), task.getId(), bidAmount1);

        User user2 = new User();
        AddUserRequest addUserRequest2 = new AddUserRequest(user2);
        RequestManager.getInstance().invokeRequest(getActivity(),addUserRequest2);
        float bidAmount2 = 1.00f;
        Bid bid2 = new Bid(user2.getId(), task.getId(), bidAmount2);
        assertEquals(bid1.compareTo(bid2), 1);

        User user3 = new User();
        AddUserRequest addUserRequest3 = new AddUserRequest(user3);
        RequestManager.getInstance().invokeRequest(getActivity(),addUserRequest3);
        float bidAmount3 = 10.00f;
        Bid bid3 = new Bid(user3.getId(), task.getId(), bidAmount3);
        assertEquals(bid1.compareTo(bid3), 0);

        User user4 = new User();
        AddUserRequest addUserRequest4 = new AddUserRequest(user4);
        RequestManager.getInstance().invokeRequest(getActivity(),addUserRequest4);
        float bidAmount4 = 20.00f;
        Bid bid4 = new Bid(user4.getId(), task.getId(), bidAmount4);
        assertEquals(bid1.compareTo(bid4), -1);
    }
}
