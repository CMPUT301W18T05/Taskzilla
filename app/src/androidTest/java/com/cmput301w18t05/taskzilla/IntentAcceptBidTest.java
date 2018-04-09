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

import android.support.design.widget.TabLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cmput301w18t05.taskzilla.activity.EditProfileActivity;
import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.activity.NewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.ProfileActivity;
import com.cmput301w18t05.taskzilla.activity.SignUpActivity;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.WelcomeActivity;
import com.robotium.solo.Solo;


public class IntentAcceptBidTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private String username1;
    private String username2;

    public IntentAcceptBidTest(){
        super(MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
        username1 = "TestUser";
        username2 = "TestUserOne";
    }

    public void login(String usern) {
        //Set up for Test
        solo.clickOnText("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.usernameField), usern);
        solo.enterText((EditText) solo.getView(R.id.nameField), "TestName");
        solo.enterText((EditText) solo.getView(R.id.emailField), "Test@Email.com");
        solo.enterText((EditText) solo.getView(R.id.phoneField), "1234567890");
        solo.enterText((EditText) solo.getView(R.id.passwordField), "a");
        solo.clickOnButton("Sign Up");

        //Correct Log in Info
        solo.clearEditText((EditText) solo.getView(R.id.usernameText));
        solo.enterText((EditText) solo.getView(R.id.usernameText), usern);
        solo.enterText((EditText) solo.getView(R.id.passwordText), "a");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        assertTrue(solo.waitForActivity(WelcomeActivity.class));
    }

    public void addtask(String taskname) {
        solo.waitForActivity(WelcomeActivity.class);
        solo.assertCurrentActivity("Wrong", WelcomeActivity.class);

        View fab = solo.getView(R.id.fab);
        solo.waitForView(fab);
        solo.clickOnView(fab);

        solo.waitForActivity(NewTaskActivity.class);
        View taskField = solo.getView(R.id.TaskName);
        solo.waitForView(taskField);
        solo.typeText((EditText)taskField, taskname);

        EditText descriptionField = (EditText) solo.getView(R.id.Description);
        solo.typeText(descriptionField, "tag1 tag2 tag3 tag4");
        View addButton = solo.getView(R.id.addTaskButton);
        solo.clickOnView(addButton);
    }

    public void searchTask() {
        solo.clickOnText("Search");
        solo.waitForText("My task");
        solo.clickOnText("My task");
    }

    public void addBid() {
        solo.clickOnText("PLACE BID");
        solo.goBack();
    }

    public void logout() {
        solo.clickOnText("Profile");
        solo.clickOnText("Log out");
    }

    public void testAcceptBid(){
        login(username1);
        //addtask("My task");
        //logout();
        //login(username2);
        searchTask();
        addBid();
    }
}
