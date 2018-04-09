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

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.cmput301w18t05.taskzilla.activity.EditTaskActivity;
import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.activity.NewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.SignUpActivity;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.WelcomeActivity;
import com.cmput301w18t05.taskzilla.fragment.TasksRequesterFragment;
import com.robotium.solo.Solo;

/**
 * Created by Micheal-Nguyen on 2018-03-18.
 */

public class UserTaskActivityIntentTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private TasksRequesterFragment fragment;


    public UserTaskActivityIntentTest(){
        super(MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testNewTask(){
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);


        solo.clickOnText("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.usernameField), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.nameField), "TestName");
        solo.enterText((EditText) solo.getView(R.id.emailField), "Test@Email.com");
        solo.enterText((EditText) solo.getView(R.id.phoneField), "1234567890");
        solo.clickOnButton("Sign Up");

        solo.enterText((EditText) solo.getView(R.id.usernameText), "TestUser");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);

        // taken from https://stackoverflow.com/questions/33125017/how-to-access-floatingactionmenu-and-floating-action-button-in-robotium
        // 2018-3-17
        // View v = inflater.inflate(R.layout.fragment_tasks_requester, container, false);
        solo.waitForText("Tasks");
        View fab = solo.getCurrentActivity().findViewById(R.id.fab);
        solo.clickOnView(fab);
        solo.assertCurrentActivity("Wrong Activity", NewTaskActivity.class);



        //No Task Name
        solo.enterText((EditText) solo.getView(R.id.Description), "Test Description");
        solo.clickOnButton("Add Task");
        solo.assertCurrentActivity("Wrong Activity", NewTaskActivity.class);

        //Task Name Too Long
        solo.clearEditText((EditText) solo.getView(R.id.TaskName));
        solo.enterText((EditText) solo.getView(R.id.TaskName), "Test Task Test Task Test Task Test Task Test Task Test Task Test Task Test Task Test Task");
        solo.clickOnButton("Add Task");
        solo.assertCurrentActivity("Wrong Activity", NewTaskActivity.class);

        //No Task Description
        solo.clearEditText((EditText) solo.getView(R.id.TaskName));
        solo.clearEditText((EditText) solo.getView(R.id.Description));
        solo.enterText((EditText) solo.getView(R.id.TaskName), "Test Task Name");
        solo.clickOnButton("Add Task");
        solo.assertCurrentActivity("Wrong Activity", NewTaskActivity.class);

        //Task Description too long
        solo.enterText((EditText) solo.getView(R.id.Description), "Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description " +
                "Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description " +
                "Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description Test Description");
        solo.clickOnButton("Add Task");
        solo.assertCurrentActivity("Wrong Activity", NewTaskActivity.class);


        //Valid Information
        solo.clearEditText((EditText) solo.getView(R.id.Description));
        solo.enterText((EditText) solo.getView(R.id.Description), "Test Description");
        solo.clickOnButton("Add Task");
        solo.waitForActivity(WelcomeActivity.class);
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        View fab2 = solo.getCurrentActivity().findViewById(R.id.fab);
        solo.clickOnView(fab2);
        assertTrue(solo.waitForText("Test Task Name"));

    }


    public void testEditTask(){
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);


        solo.clickOnText("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.usernameField), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.nameField), "TestName");
        solo.enterText((EditText) solo.getView(R.id.emailField), "Test@Email.com");
        solo.enterText((EditText) solo.getView(R.id.phoneField), "1234567890");
        solo.clickOnButton("Sign Up");

        solo.enterText((EditText) solo.getView(R.id.usernameText), "TestUser");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);

        // taken from https://stackoverflow.com/questions/33125017/how-to-access-floatingactionmenu-and-floating-action-button-in-robotium
        // 2018-3-17
        // View v = inflater.inflate(R.layout.fragment_tasks_requester, container, false);
        solo.waitForText("Tasks");
        View fab = solo.getCurrentActivity().findViewById(R.id.fab);
        solo.clickOnView(fab);
        solo.assertCurrentActivity("Wrong Activity", NewTaskActivity.class);

        solo.enterText((EditText) solo.getView(R.id.TaskName), "Test Task Name");
        solo.enterText((EditText) solo.getView(R.id.Description), "Test Description");
        solo.clickOnButton("Add Task");
        solo.waitForActivity(WelcomeActivity.class);
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        View fab2 = solo.getCurrentActivity().findViewById(R.id.fab);
        solo.clickOnView(fab2);
        assertTrue(solo.waitForText("Test Task Name"));

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ViewTaskActivity.class);
        solo.waitForText("Test Task Name");

        //Click Edit Button
        View editButton = solo.getCurrentActivity().findViewById(R.id.EditButton);
        solo.clickOnView(editButton);
        solo.waitForActivity(EditTaskActivity.class);
        solo.assertCurrentActivity("Wrong Activity", EditTaskActivity.class);


        //No Task Name
        solo.clearEditText((EditText) solo.getCurrentActivity().findViewById(R.id.TaskName));
        solo.clickOnButton("Save");
        solo.assertCurrentActivity("Wrong Activity", EditTaskActivity.class);

        //Task Name Too Long
        solo.enterText((EditText) solo.getCurrentActivity().findViewById(R.id.TaskName), "Task Name Task Name Task Name Task Name Task Name Task Name Task Name Task Name Task Name Task Name Task Name");
        solo.clickOnButton("Save");
        solo.assertCurrentActivity("Wrong Activity", EditTaskActivity.class);

        //Task Description Too Long
        solo.clearEditText((EditText) solo.getCurrentActivity().findViewById(R.id.TaskName));
        solo.enterText((EditText) solo.getCurrentActivity().findViewById(R.id.TaskName), "Task Name Edited");
        solo.enterText((EditText) solo.getCurrentActivity().findViewById(R.id.Description), "Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description" +
                "Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description" +
                "Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description Task Description" +
                "Task Description Task Description Task Description Task Description Task Description");
        solo.clickOnButton("Save");
        solo.assertCurrentActivity("Wrong Activity", EditTaskActivity.class);

        //Valid Information
        solo.clearEditText((EditText) solo.getCurrentActivity().findViewById(R.id.Description));
        solo.enterText((EditText) solo.getCurrentActivity().findViewById(R.id.Description), "Task Description Edited");
        solo.clickOnButton("Save");

        solo.waitForActivity(ViewTaskActivity.class);
        solo.assertCurrentActivity("Wrong Activity", ViewTaskActivity.class);
        assertTrue(solo.waitForText("Task Name Edited"));
        assertTrue(solo.waitForText("Task Description Edited"));
    }

    public void testDeleteTask(){
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);


        solo.clickOnText("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.usernameField), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.nameField), "TestName");
        solo.enterText((EditText) solo.getView(R.id.emailField), "Test@Email.com");
        solo.enterText((EditText) solo.getView(R.id.phoneField), "1234567890");
        solo.clickOnButton("Sign Up");

        solo.enterText((EditText) solo.getView(R.id.usernameText), "TestUser");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);

        // taken from https://stackoverflow.com/questions/33125017/how-to-access-floatingactionmenu-and-floating-action-button-in-robotium
        // 2018-3-17
        // View v = inflater.inflate(R.layout.fragment_tasks_requester, container, false);
        solo.waitForText("Tasks");
        View fab = solo.getCurrentActivity().findViewById(R.id.fab);
        solo.clickOnView(fab);
        solo.assertCurrentActivity("Wrong Activity", NewTaskActivity.class);

        solo.enterText((EditText) solo.getView(R.id.TaskName), "Test Task To Be Deleted");
        solo.enterText((EditText) solo.getView(R.id.Description), "Test Description");
        solo.clickOnButton("Add Task");
        solo.waitForActivity(WelcomeActivity.class);
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        View fab2 = solo.getCurrentActivity().findViewById(R.id.fab);
        solo.clickOnView(fab2);
        assertTrue(solo.waitForText("Test Task To Be Deleted"));

        solo.clickOnText("Test Task To Be Deleted");
        solo.assertCurrentActivity("Wrong Activity", ViewTaskActivity.class);
        solo.waitForText("Test Task to Be Deleted");

        View DeleteButton = solo.getCurrentActivity().findViewById(R.id.DeleteButton);
        solo.clickOnView(DeleteButton);
        solo.clickOnText(("Yes"));
        solo.waitForActivity(WelcomeActivity.class);
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);

    }

}
