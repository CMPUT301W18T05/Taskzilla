package com.cmput301w18t05.taskzilla;

import android.content.Intent;


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
