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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.activity.NewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.ProfileActivity;
import com.cmput301w18t05.taskzilla.activity.SignUpActivity;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.WelcomeActivity;
import com.robotium.solo.Solo;

/**
 * Created by Jeremy on 2018-02-23.
 */

public class UserProfileActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public UserProfileActivityTest(){
        super(MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testLogIn(){

        //Set up for Test
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.usernameField), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.nameField), "TestName");
        solo.enterText((EditText) solo.getView(R.id.emailField), "Test@Email.com");
        solo.enterText((EditText) solo.getView(R.id.phoneField), "1234567890");
        solo.clickOnButton("Sign Up");

        //No Log in Info
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Incorrect Log in Info
        solo.enterText((EditText) solo.getView(R.id.usernameText), "123456789101112131415161718192021222324252627282930");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        //Correct Log in Info
        solo.clearEditText((EditText) solo.getView(R.id.usernameText));
        solo.enterText((EditText) solo.getView(R.id.usernameText), "TestUser");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
    }

    public void testNewTaskProfile() {
        //valid login
        solo.enterText((EditText) solo.getView(R.id.usernameText), "TestUser");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);

        //create new task
        solo.waitForText("Tasks");
        View fab = solo.getCurrentActivity().findViewById(R.id.fab);
        solo.clickOnView(fab);
        solo.assertCurrentActivity("Wrong Activity", NewTaskActivity.class);

        //validate info
        solo.enterText((EditText) solo.getView(R.id.TaskName), "Test Task Name");
        solo.enterText((EditText) solo.getView(R.id.Description), "Test Description");
        solo.clickOnButton("Add Task");
        assertTrue(solo.waitForText("Test Task Name"));

        //check intent of other user profile
        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", ViewTaskActivity.class);
        solo.waitForText("Test Task Name");

        ImageButton editButton = (ImageButton) solo.getView(R.id.RequesterPicture);
        solo.clickOnView(editButton);
        solo.sleep(500);
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);

        //checks if name of user matches name in profile
        assertTrue(solo.waitForText(currentUser.getInstance().getName()));

        //checks if email of user matches email in profile
        assertTrue(solo.waitForText(currentUser.getInstance().getEmail().toString()));

        //checks if phone of user matches phone in profile
        assertTrue(solo.waitForText(currentUser.getInstance().getPhone().toString()));
    }

    public void testProfile() {
        //valid login
        solo.enterText((EditText) solo.getView(R.id.usernameText), "TestUser");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);

        solo.sleep(500);

        solo.clickOnText("Profile");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);

        //Not implemented yet
        //ImageButton editButton = (ImageButton) solo.getView(R.id.EditButton);
        //solo.clickOnView(editButton);
        //solo.sleep(500);
        //solo.assertCurrentActivity("Wrong Activity", EditProfileActivity.class);

        //checks if name of user matches name in profile
        assertTrue(solo.waitForText(currentUser.getInstance().getName()));

        //checks if email of user matches email in profile
        assertTrue(solo.waitForText(currentUser.getInstance().getEmail().toString()));

        //checks if phone of user matches phone in profile
        assertTrue(solo.waitForText(currentUser.getInstance().getPhone().toString()));

        String userName = currentUser.getInstance().getUsername();

        /*
        //checks if logout button brings the user back to the home screen
        View logoutButton = solo.getCurrentActivity().findViewById(R.id.LogOutButton);
        solo.clickOnView(logoutButton);
        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Wrong Acvitivy", MainActivity.class);
        assertTrue(solo.waitForText(userName));
        */
    }
}
