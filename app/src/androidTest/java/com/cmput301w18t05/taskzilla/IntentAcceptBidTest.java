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
import android.widget.EditText;
import android.widget.ImageButton;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.activity.NewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.ProfileActivity;
import com.cmput301w18t05.taskzilla.activity.SignUpActivity;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.WelcomeActivity;
import com.robotium.solo.Solo;


public class IntentAcceptBidTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public IntentAcceptBidTest(){
        super(MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAcceptBid(){
        //Set up for Test
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.usernameField), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.nameField), "TestName");
        solo.enterText((EditText) solo.getView(R.id.emailField), "Test@Email.com");
        solo.enterText((EditText) solo.getView(R.id.phoneField), "1234567890");
        solo.enterText((EditText) solo.getView(R.id.passwordField), "a");
        solo.clickOnButton("Sign Up");

        //Correct Log in Info
        solo.clearEditText((EditText) solo.getView(R.id.usernameText));
        solo.enterText((EditText) solo.getView(R.id.usernameText), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.passwordText), "a");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);
        assertTrue(solo.waitForActivity(WelcomeActivity.class));

        solo.waitForActivity(WelcomeActivity.class);
        solo.assertCurrentActivity("Wrong", WelcomeActivity.class);

        TabLayout tabLayout =  (TabLayout)solo.getView(R.id.tabs_bar);
        tabLayout.getTabAt(0);
        solo.waitForText("Tasks");


    }
}
