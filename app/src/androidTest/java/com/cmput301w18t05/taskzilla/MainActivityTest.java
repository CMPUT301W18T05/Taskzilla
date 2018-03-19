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
import android.widget.EditText;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.activity.SignUpActivity;
import com.cmput301w18t05.taskzilla.activity.WelcomeActivity;
import com.cmput301w18t05.taskzilla.fragment.TasksRequesterFragment;
import com.robotium.solo.Solo;

/**
 * Created by Micheal-Nguyen on 2018-03-18.
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2{
    private Solo solo;
    private TasksRequesterFragment fragment;


    public MainActivityTest(){
        super(MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }




    public void testSignUp(){
        MainActivity activity = (MainActivity)solo.getCurrentActivity();
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnText("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        //Wrong Info - Long username
        solo.enterText((EditText) solo.getView(R.id.usernameField), "wowowowowowowowowowdkwodkwodwkodwkdowodkwdoksqsqw");
        solo.enterText((EditText) solo.getView(R.id.nameField), "TestName");
        solo.enterText((EditText) solo.getView(R.id.emailField), "Test@Email.com");
        solo.enterText((EditText) solo.getView(R.id.phoneField), "1234567890");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        //Wrong Info - Illegal character in username
        solo.clearEditText((EditText) solo.getView(R.id.usernameField));
        solo.enterText((EditText) solo.getView(R.id.usernameField), "Test%^User");
        solo.sleep(1000);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        //Wrong Info - Long name
        solo.clearEditText((EditText) solo.getView(R.id.usernameField));
        solo.enterText((EditText) solo.getView(R.id.usernameField), "TestUser");
        solo.enterText((EditText) solo.getView(R.id.nameField), "TestNameTestNameTestNameTestNameTestNameTestNameTestNameTestNameTestName");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        //Wrong Info - Illegal character in name
        solo.clearEditText((EditText) solo.getView(R.id.nameField));
        solo.enterText((EditText) solo.getView(R.id.nameField), "$%**@(@)!)");
        solo.sleep(1000);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        //Wrong Info - Email Invalid
        solo.clearEditText((EditText) solo.getView(R.id.nameField));
        solo.clearEditText((EditText) solo.getView(R.id.emailField));
        solo.enterText((EditText) solo.getView(R.id.nameField), "TestName");
        solo.enterText((EditText) solo.getView(R.id.emailField), "TestEmaillcom");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        //Wrong Info - Email Too Long
        solo.clearEditText((EditText) solo.getView(R.id.emailField));
        solo.enterText((EditText) solo.getView(R.id.emailField), "TestEmailcomTestEmailcomTestEmailcomTestEmailcomTestEmailcomTestEmailcomTestEmailcom");
        solo.sleep(1000);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        //Wrong Info - Incorrect Phone Number
        solo.clearEditText((EditText) solo.getView(R.id.emailField));
        solo.enterText((EditText) solo.getView(R.id.emailField), "Test@Email.com");
        solo.enterText((EditText) solo.getView(R.id.phoneField), "1290");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        //Valid Information
        solo.clearEditText((EditText) solo.getView(R.id.phoneField));
        solo.enterText((EditText) solo.getView(R.id.phoneField), "1234567890");
        solo.sleep(1000);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
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
        assertTrue(solo.waitForText("Tasks"));
    }




}