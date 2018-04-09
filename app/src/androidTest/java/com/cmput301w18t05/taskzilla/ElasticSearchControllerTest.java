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
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;

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
        user.setRequesterRating(3.0);
        user.setPhoto(new Photo("/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABALDA4MChAODQ4SERATGCgaGBYWGDEjJR0oOjM9PDkz ODdASFxOQERXRTc4UG1RV19iZ2hnPk1xeXBkeFxlZ2P/2wBDARESEhgVGC8aGi9jQjhCY2NjY2Nj Y2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2P/wAARCADwAPADASIA AhEBAxEB/8QAGgABAAMBAQEAAAAAAAAAAAAAAAIDBAEFB//EACoQAQACAQEGBQUBAQAAAAAAAAAB AgMRBCExM1FxEiIyQaETQoGRsVJh/8QAFAEBAAAAAAAAAAAAAAAAAAAAAP/EABQRAQAAAAAAAAAA AAAAAAAAAAD/2gAMAwEAAhEDEQA/APoAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAAAAAAAAAAAAja1axraYiFNtqrHpiZ+AaBkna7e1YI2u3vWAaxnrtVJ9UTX5 XVtFo1rMTAJAAAAAAAAAAAAAAAAAAAAAAM+baIp5ab7fx3aMvgjw19U/DGDtrTadbTrLgAAAO1tN Z1rOkuANmHPF/Lbdb+r3mNmz5fHXS3qj5BeAAAAAAAAAAAAAAAAAAja0VrMzwhJRtdtMcR1kGS1p tabTxlwAAAAAAAHaWml4tHs4A9KJi0RMcJdUbLbXFp0nReAAAAAAAAAAAAAAAAAybZPmrH/Gtk2y N9Z7gzgAAAAAAAAA07H98dmpl2OPXPZqAAAAAAAAAAAAAAAAAUbVXXFr0le5MRaJieEg80SvWaXm s+yIAAAAAAAJY6Te8Vj8g17NXw4onrvXORGkaQ6AAAAAAAAAAAAAAAAAACnPi+pXWPVHBimNJ0nd L01OXDGTfwt1BiEr47Y580flEAAAEqY7ZJ8sfn2ByImZ0jfMtuDF9Ou/1TxMWGuONeNuq0AAAAAA AAAAAAAAAAAAAAAAHJjWN6q2z47cI07LJtWONojvKM5scffAKp2SPa8/ojZI97z+ln18X+/g+vi/ 2Dldnx19te62N3BCM2OfvhKLVnhaJ7SCQAAAAAAAAAAAAAAAAAAIXvXHXW0gmqvnpTdrrPSGbLnt fdHlr0hUC+21Xn0xFflVbJe3G0z+UQAAAAAAEq5L14WlbXarx6oifhQA3Uz0vuidJ6SteYtxZ7U3 T5qg3CFL1vXWs6wmAAAAAAAAAAAACF7xSs2lhvecltbfros2m/iyeGOFf6pAAAAAAAAAAAAAABPH knHbWPzHVupaL1i0cJecv2XJ4b+CeE/0GwAAAAAAAAABG06VmekapK83Jv2Bg113gAAAAAAAAAAA AAAAETpMTHtvAHpROsRLqGLl17QmAAAAAAAAArzcm/ZYrzcm/YGAAAAAAAAAAAAAAAAAAHoYuVXt CaGLlV7QmAAAAAAAAArzcm/ZYrzcm/YGAAAAAAAAAAAAAAAAAAHoYuVXtCaGLlV7QmAAAAAAAAAr zcm/ZYrzcm/YGAAAAAAAAAAAAAAAAAAHoYuVXtCaGLlV7QmAAAAD/9k= "));
        AddUserRequest addUserRequest = new AddUserRequest(user);
        RequestManager.getInstance().invokeRequest(addUserRequest);

        Task task = new Task("Task name", user, "Task description");
        AddTaskRequest addTaskRequest = new AddTaskRequest(task);
        RequestManager.getInstance().invokeRequest(addTaskRequest);

        Bid bid = new Bid(user.getId(), task.getId(), 10.0f);

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
        Task mockTask = new Task("Task name", user, "Task description");

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
        Task mockTask = new Task("Task name", user, "Task description");

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
        Task mockTask = new Task("Task name", user, "Task description");
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
