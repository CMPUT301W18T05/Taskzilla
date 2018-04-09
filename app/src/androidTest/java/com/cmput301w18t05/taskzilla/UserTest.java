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
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;

import java.util.ArrayList;

/**
 * Created by James on 2/23/2018.
 * Updated by Jeremy
 */

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest() {
        super(MainActivity.class);
    }

    /**
     * Test for setting the name of user
     */
    public void testSetName() {
        User user = new User();

        String name = "James Sun";
        assertTrue(user.setName(name));

        name = "C0lin Ch0i";
        assertTrue(user.setName(name));

        name = "4ndy_l1";
        assertTrue(user.setName(name));

        name = "~Jeremy Ng~";
        assertFalse(user.setName(name));

        name = "";
        assertFalse(user.setName(name));

        name = "    Wyatt Praharen";
        assertFalse(user.setName(name));

        name = "Michael Nguyen Michael Nguyen Michael Nguyen Michael Nguyen ";
        assertFalse(user.setName(name));
    }

    /**
     * Test for setting the name of username
     */
    public void testSetUsername() {
        User user = new User();

        String username = "James Sun";
        assertFalse(user.setUsername(username));

        username = "C0lin Ch0i";
        assertFalse(user.setUsername(username));

        username = "4ndy_l1";
        assertTrue(user.setUsername(username));

        username = "~Jeremy Ng~";
        assertFalse(user.setUsername(username));

        username = "";
        assertFalse(user.setUsername(username));

        username = "    Wyatt Praharen";
        assertFalse(user.setUsername(username));

        username = "Michael Nguyen Michael Nguyen Michael Nguyen Michael Nguyen ";
        assertFalse(user.setUsername(username));
    }

    //
    public void setProviderRating() {
        User user = new User();

        Float floatRating = 3.5f;
        Double doubleRating = 3.5d;
        assertTrue(user.setProviderRating(floatRating));
        assertTrue(user.setProviderRating(doubleRating));

        floatRating = -1.0f;
        doubleRating = -1.0d;
        assertFalse(user.setProviderRating(floatRating));
        assertFalse(user.setProviderRating(doubleRating));

        floatRating = 6.0f;
        doubleRating = 6.0d;
        assertFalse(user.setProviderRating(floatRating));
        assertFalse(user.setProviderRating(doubleRating));
    }

    public void setRequesterRating() {
        User user = new User();

        Float floatRating = 3.5f;
        Double doubleRating = 3.5d;
        assertTrue(user.setProviderRating(floatRating));
        assertTrue(user.setProviderRating(doubleRating));

        floatRating = -1.0f;
        doubleRating = -1.0d;
        assertFalse(user.setProviderRating(floatRating));
        assertFalse(user.setProviderRating(doubleRating));

        floatRating = 6.0f;
        doubleRating = 6.0d;
        assertFalse(user.setProviderRating(floatRating));
        assertFalse(user.setProviderRating(doubleRating));
    }

    // Test for setting password
    public void testSetPassword() {
        User user = new User();
        String password = "jeremy";
        assertTrue(user.setPassword(password));

        password = "Jeremy01";
        assertTrue(user.setPassword(password));

        password = "!@#$%";
        assertFalse(user.setPassword(password));

        password = "";
        assertFalse(user.setPassword(password));

        password = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
        assertFalse(user.setPassword(password));
    }

    // Test for toString method
    public void testToString() {
        User user = new User();
        String name = "jeremy";
        String id = "123abc";
        assertTrue(user.setName(name));
        user.setId(id);
        assertEquals(user.toString(), "jeremy 123abc");
    }

    // Test for getting a list of tasks that the user has requested
    public void testGetTasksRequested() {
        User user = new User();
        AddUserRequest addUserRequest = new AddUserRequest(user);

        Task task1 = new Task();
        AddTaskRequest addTaskRequest1 = new AddTaskRequest(task1);

        Task task2 = new Task();
        AddTaskRequest addTaskRequest2 = new AddTaskRequest(task2);

        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(task1);
        tasks.add(task2);

        RequestManager.getInstance().invokeRequest(addUserRequest);
        RequestManager.getInstance().invokeRequest(addTaskRequest1);
        RequestManager.getInstance().invokeRequest(addTaskRequest2);

        ArrayList<Task> result = new ArrayList<>();
        result = user.getTasksRequested();

        assertTrue(tasks.containsAll(result) && result.containsAll(tasks));
    }

    /**
     * Test for getting everything
     */
    public void testGetEverything() {
        User user = new User();

        String name = "Asd Dsa";
        String username = "hax04";
        user.setName(name);
        assertEquals(user.getName(), name);

        user.setUsername(username);
        assertEquals(user.getUsername(), username);

        PhoneNumber phone = new PhoneNumber("1234567890");
        user.setPhone(phone);
        assertEquals(user.getPhone(), phone);

        user.setRequesterRating(2.3f);
        assertEquals(user.getRequesterRating(), 2.3f);

        user.setProviderRating(2.3f);
        assertEquals(user.getProviderRating(), 2.3f);

        EmailAddress mail = new EmailAddress("asd@dsa.f");
        user.setEmail(mail);
        assertEquals(user.getEmail(), mail);
    }
}
