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
import android.util.Log;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;

import java.util.ArrayList;

/**
 * Unit tests for ES controller
 */

public class ElasticSearchControllerTest extends ActivityInstrumentationTestCase2 {
    private User user;
    private Task task;
    private Bid bid;

    public ElasticSearchControllerTest(){
        super(MainActivity.class);


        user = new User();
        user.setName("Tester");
        user.setUsername("myuniqueusername");
        user.setPhone(new PhoneNumber());
        user.setEmail(new EmailAddress("test@301.com"));
        user.setProviderRating(1.8);
        user.setRequesterRating(8.0);
        // user.setPhotos(new ArrayList<Photo>());
        // user.setPhoto(new Photo());



        Bid bid = new Bid(user.getId(), task.getId(), 10.0f);

        task = new Task();
        task.setName("My test task");
        task.addBid(bid);

    }

    public void testAddUser() {
        String origUID;

        /* ES will make the mapping for us */
        ElasticSearchController.AddUser addUser = new ElasticSearchController.AddUser();
        addUser.execute(user);

        try {
            assertTrue(addUser.get());
            origUID = user.getId();
        }
        catch (Exception e) {
            Log.i("Failure", "Exception when trying to add user");
        }

        // try to get the same user;
        ElasticSearchController.GetUser getUser = new ElasticSearchController.GetUser();
        getUser.execute(user.getId());

        try {
            User foundUser = getUser.get();
            assertEquals(user.getName(), foundUser.getName()); // check if they are the same
            assertEquals(user.getId(), foundUser.getId());
        }
        catch (Exception e) {
            Log.i("Failure","Exception when getting user from db");
            assertTrue(false);
        }
    }

    public void testRemoveUser() {
        User mockUser = new User();

        ElasticSearchController.AddUser addUser = new ElasticSearchController.AddUser();
        addUser.execute(mockUser);

        try {
            boolean added = addUser.get();
        }
        catch (Exception e) {
            Log.i("Failure", "Exception when trying to add user");
        }

        String uid = mockUser.getId();

        // now remove the user
        ElasticSearchController.RemoveUser removeUser = new ElasticSearchController.RemoveUser();
        removeUser.execute(mockUser); // todo: add remove by UID

        // look for it again
        ElasticSearchController.GetUser getUser = new ElasticSearchController.GetUser();
        getUser.execute(uid);

        try {
            User found = getUser.get();
            assertEquals(null, found);
        }
        catch (Exception e) {
            Log.i("Failure", "Exception when getting user");
            assertTrue(false);
        }
    }

    public void testAddBid() {
        Bid bid = new Bid(user.getId(), task.getId(), 10.0f);
        ElasticSearchController.AddBid task = new ElasticSearchController.AddBid();
        try {
            boolean added = task.execute(bid).get();
            assertTrue(added);
        }
        catch (Exception e) {
            assertTrue(false);
        }

    }

    public void testAddTask() {
        Bid bid = new Bid(user.getId(), task.getId(), 10.0f);

        ElasticSearchController.AddBid task = new ElasticSearchController.AddBid();
        try {
            boolean added = task.execute(bid).get();
            assertTrue(added);
        }
        catch (Exception e) {
            assertTrue(false);
        }
    }

    public void testRemoveTask() {
        Task mockTask = new Task();

        ElasticSearchController.AddTask addTask = new ElasticSearchController.AddTask();
        addTask.execute(mockTask);

        // get result
        try {
            addTask.get();
        }
        catch (Exception e) {
            Log.i("Failure", "Exception was caught when adding task");
            assertTrue(false);
        }

        String taskId = mockTask.getId();

        // remove this one
        ElasticSearchController.RemoveTask removeTask = new ElasticSearchController.RemoveTask();
        removeTask.execute(taskId);

        // try to find again
        ElasticSearchController.SearchForTasks searchForTasks = new ElasticSearchController.SearchForTasks(0,10);
        searchForTasks.execute(taskId);

        try {
            ArrayList<Task> result = searchForTasks.get();
            assertTrue(result.isEmpty());
        }
        catch (Exception e) {
            Log.i("Failure", "Exception when searching for removed task");
        }
    }

    public void testGetTask() {
        Task mockTask = new Task();

        ElasticSearchController.AddTask addTask = new ElasticSearchController.AddTask();
        addTask.execute(mockTask);

        // get result
        try {
            addTask.get();
        }
        catch (Exception e) {
            Log.i("Failure", "Exception was caught when adding task");
            assertTrue(false);
        }

        String taskId = mockTask.getId();

        // check to see if we can find it in db
        ElasticSearchController.GetTask getTask = new ElasticSearchController.GetTask();
        getTask.execute(taskId);

        try {
            Task foundTask = getTask.get();
            assertEquals(mockTask.getName(), foundTask.getName());
            assertEquals(mockTask.getId(), foundTask.getId());
        }
        catch (Exception e) {
            Log.i("Failure", "Exception when getting task");
        }
    }

    public void testUpdateTask() {
        Task mockTask = new Task();
        mockTask.setName("OriginalName");

        ElasticSearchController.AddTask addTask = new ElasticSearchController.AddTask();
        addTask.execute(mockTask);

        // get result
        try {
            addTask.get();
        }
        catch (Exception e) {
            Log.i("Failure", "Exception was caught when adding task");
            assertTrue(false);
        }

        String taskId = mockTask.getId(); // keep this the same

        mockTask.setName("NewName");

        // update this task
        ElasticSearchController.UpdateTask updateTask = new ElasticSearchController.UpdateTask();
        updateTask.execute(mockTask);

        // check to see if we can find it in db
        ElasticSearchController.GetTask getTask = new ElasticSearchController.GetTask();
        getTask.execute(taskId);

        try {
            Task foundTask = getTask.get();
            assertEquals("NewName", foundTask.getName());
        }
        catch (Exception e) {
            Log.i("Failure", "Exception when getting task");
            assertTrue(false);
        }
    }
}
