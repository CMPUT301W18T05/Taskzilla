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

import android.app.ActionBar;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cmput301w18t05.taskzilla.activity.EditProfileActivity;
import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.activity.NewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.SignUpActivity;
import com.cmput301w18t05.taskzilla.activity.WelcomeActivity;
import com.cmput301w18t05.taskzilla.controller.ProfileController;
import com.cmput301w18t05.taskzilla.fragment.ProfileFragment;
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

/*
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
*/
    public void testProfile() {

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
    }


}
