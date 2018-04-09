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

import android.app.AlertDialog;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.activity.NewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.SignUpActivity;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.WelcomeActivity;
import com.cmput301w18t05.taskzilla.fragment.TasksRequesterFragment;
import com.robotium.solo.Solo;

/**
 * Created by Colin on 2018-03-19.
 */
public class BidActivityIntentTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private TasksRequesterFragment fragment;

    public BidActivityIntentTest(){
        super(MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }


    public void testBid(){

        //Set up for Test
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.usernameField), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.nameField), "TestName");
        solo.enterText((EditText) solo.getView(R.id.passwordField), "a");
        solo.enterText((EditText) solo.getView(R.id.emailField), "Test@Email.com");
        solo.enterText((EditText) solo.getView(R.id.phoneField), "1234567890");
        solo.clickOnButton("Sign Up");

        //Correct Log in Info
        solo.clearEditText((EditText) solo.getView(R.id.usernameText));
        solo.enterText((EditText) solo.getView(R.id.usernameText), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.passwordText), "a");
        solo.clickOnButton("Log In");
        solo.waitForText("Tasks");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.sleep(5000);

        View fab = solo.getCurrentActivity().findViewById(R.id.fab);
        solo.clickOnView(fab);
        solo.waitForActivity(NewTaskActivity.class);
        solo.assertCurrentActivity("Wrong Activity", NewTaskActivity.class);

        //Valid Information
        solo.clearEditText((EditText) solo.getView(R.id.Description));
        solo.enterText((EditText) solo.getView(R.id.TaskName), "Test Task Name");
        solo.clearEditText((EditText) solo.getView(R.id.Description));
        solo.enterText((EditText) solo.getView(R.id.Description), "Test Description");
        solo.clickOnButton("Add Task");
        solo.waitForActivity(WelcomeActivity.class);
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);



        solo.waitForText("Profile");
        solo.clickOnText("Profile");
        solo.clickOnText("Log out");
        solo.waitForActivity(MainActivity.class);
        solo.assertCurrentActivity("Wrong Activity",MainActivity.class);


        solo.clickOnText("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.usernameField), "TestUserOne");
        solo.enterText((EditText) solo.getView(R.id.nameField), "TestNameOne");
        solo.enterText((EditText) solo.getView(R.id.passwordField), "a");
        solo.enterText((EditText) solo.getView(R.id.emailField), "Test1@Email.com");
        solo.enterText((EditText) solo.getView(R.id.phoneField), "1234567890");
        solo.clickOnButton("Sign Up");

        //Correct Log in Info
        solo.clearEditText((EditText) solo.getView(R.id.usernameText));
        solo.enterText((EditText) solo.getView(R.id.usernameText), "TestUserOne");
        solo.enterText((EditText) solo.getView(R.id.passwordText), "a");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);

        //Test Bid on task
        solo.sleep(3000);
        solo.waitForText("Search");
        solo.clickOnText("Search");
        solo.sleep(3000);
        solo.waitForText("Status: ");
        solo.clickOnText("Status: ");
        solo.assertCurrentActivity("Wrong Activity", ViewTaskActivity.class);
        solo.clickOnButton("PLACE BID");
        solo.enterText((EditText) solo.getView(R.id.place_bid_edittext),"25.22");
        solo.clickOnButton("Place Bid");
        solo.sleep(1000);
        solo.goBack();

        //Test Bid in bid list
        solo.waitForText("Bids");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        solo.clickOnText("Bids");
        solo.waitForText("Bid amount: ");
        solo.clickOnText("25.22");
        solo.assertCurrentActivity("Wrong Activity", ViewTaskActivity.class);

        //Test update bid
        solo.sleep(500);
        solo.waitForText("PLACE BID");
        solo.clickOnButton("PLACE BID");
        solo.enterText((EditText) solo.getView(R.id.place_bid_edittext),"29.22");
        solo.clickOnButton("Place Bid");
        solo.goBack();
        solo.clickLongOnText("29.22");

        //Test Delete bid
        solo.sleep(2000);
        assertTrue(solo.waitForText("29.22"));

    }

}
