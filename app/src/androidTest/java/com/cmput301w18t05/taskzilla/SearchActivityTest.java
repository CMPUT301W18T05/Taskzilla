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
import android.widget.SearchView;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.activity.SignUpActivity;
import com.cmput301w18t05.taskzilla.activity.WelcomeActivity;
import com.cmput301w18t05.taskzilla.fragment.TasksRequesterFragment;
import com.robotium.solo.Solo;

import io.searchbox.params.SearchType;

/**
 * Created by Colin on 2018-03-19.
 */
public class SearchActivityTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private TasksRequesterFragment fragment;


    public SearchActivityTest(){
        super(MainActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }


    public void testSearch(){

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

        //Correct Log in Info
        solo.clearEditText((EditText) solo.getView(R.id.usernameText));
        solo.enterText((EditText) solo.getView(R.id.usernameText), "TestUser");
        solo.clickOnButton("Log In");
        solo.assertCurrentActivity("Wrong Activity", WelcomeActivity.class);

        solo.waitForText("Search");
        solo.clickOnText("Search");


        solo.clickOnView((SearchView) solo.getView(R.id.searchView));
        solo.sleep(9000);
        //solo.enterText((EditText) solo.getView(R.id.searchView),"Task");
        //solo.text

    }
}