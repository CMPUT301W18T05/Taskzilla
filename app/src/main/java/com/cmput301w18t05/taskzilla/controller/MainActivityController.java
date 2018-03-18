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

import android.content.Intent;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.activity.SignUpActivity;
import com.cmput301w18t05.taskzilla.activity.WelcomeActivity;


/**
 * Created by Colin on 2018-03-14.
 */

public class MainActivityController {
    private MainActivity view;

    public MainActivityController(MainActivity view) {
        this.view = view;
    }

    public void signUp(){
        Intent signupIntent = new Intent(view, SignUpActivity.class);
        view.startActivity(signupIntent);
    }
    public void logIn(){
        Intent loginIntent = new Intent(view, WelcomeActivity.class);
        view.startActivity(loginIntent);
    }

    public boolean checkLoggedIn(){
        return false;
    }





}
