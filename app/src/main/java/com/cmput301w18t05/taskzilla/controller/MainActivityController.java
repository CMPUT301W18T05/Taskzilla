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

package com.cmput301w18t05.taskzilla.controller;

import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.activity.SignUpActivity;
import com.cmput301w18t05.taskzilla.activity.WelcomeActivity;


/**
 * Controller that handles login and signup functionality in the main activity
 * @author Colin
 * @version 1.0
 */
public class MainActivityController {
    private MainActivity view;

    /**
     * The view of the activity is passed into this controller
     * @param view
     */
    public MainActivityController(MainActivity view) {
        this.view = view;
    }

    /**
     * Switches to SignUpActivity.
     * This is where new accounts are made
     */
    public void signUp(){
        Intent signupIntent = new Intent(view, SignUpActivity.class);
        view.startActivity(signupIntent);
    }

    /**
     * Switches to WelcomeActivity.
     * This is how the users login to the app
     */
    public void logIn(){
        Intent loginIntent = new Intent(view, WelcomeActivity.class);
        view.startActivity(loginIntent);
    }

    /**
     * Check if the user was logged in.
     * This is used to implement automatic login
     * @return
     */
    public boolean checkLoggedIn(){
        return false;
    }

}
