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

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.RemoveTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.SearchTaskRequest;


import java.util.ArrayList;

/**
 * Created by praharen on 2018-02-23.
 */

public class RequestManagerTest extends ActivityInstrumentationTestCase2 {
    private Context ctx;
    private User user;
    private Task task;
    private Bid bid;

    public RequestManagerTest(){
        super(MainActivity.class);
        ctx = InstrumentationRegistry.getTargetContext();   // for passing into request manager.
        RequestManager.getInstance().setContext(ctx);

        user = new User();
        user.setName("test name");
        user.setUsername("myuniqueUN2");
        user.setEmail(new EmailAddress("myuniqueUN2@cmput301.com"));

        task = new Task("Task name", user, "Task description");
        AddTaskRequest addTaskRequest = new AddTaskRequest(task);
        RequestManager.getInstance().invokeRequest(addTaskRequest);

        addUser();
        task.setRequesterId(user.getId());
        addTask();
    }

    public void addUser() {
        AddUserRequest addUserRequest = new AddUserRequest(user);
        RequestManager.getInstance().invokeRequest(addUserRequest);
        assertTrue(addUserRequest.getResult());
    }

    public void addTask() {
        AddTaskRequest addTaskRequest = new AddTaskRequest(task);
        RequestManager.getInstance().invokeRequest(addTaskRequest);
        assertTrue(addTaskRequest.getResult());
    }

    public void testSearchTaskRequest() {
        addUser();
        addTask();

        String keywords = task.getDescription();

        SearchTaskRequest request = new SearchTaskRequest(keywords);
        RequestManager.getInstance().invokeRequest(ctx, request);

        assertNotNull(request.getTasks());

        System.out.println("------- Search output -------");
        System.out.println("******************************");

        ArrayList<Task> ret = request.getTasks();
        System.out.println(ret);

        for (Task t : ret) {
            System.out.println(t);
            Log.i("Found Task", t.getId());
        }
    }

    public void testGetTaskRequest() {
        addUser();
        addTask();

        GetTaskRequest getTaskRequest = new GetTaskRequest(task.getId());
        RequestManager.getInstance().invokeRequest(ctx,getTaskRequest);

        Task ret = getTaskRequest.getResult();
        Log.i("Event", "RETURNED TASK: "+ret.toString());
        assertEquals(task.getId(), ret.getId());
        assertEquals(task.getName(), ret.getName());
        assertEquals(task.getDescription(), ret.getDescription());
    }

    public void testRemoveTask() {
        addUser();
        addTask();

        RemoveTaskRequest request = new RemoveTaskRequest(task.getId());
        RequestManager.getInstance().invokeRequest(ctx,request);

        GetTaskRequest getTaskRequest = new GetTaskRequest(task.getId());
        RequestManager.getInstance().invokeRequest(getTaskRequest);

        assertNull(getTaskRequest.getResult());
    }
}
