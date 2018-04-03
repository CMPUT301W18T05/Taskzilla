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

package com.cmput301w18t05.taskzilla.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.controller.MainActivityController;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetUserByUsernameRequest;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

/**
 * main activity includes the login screen
 * <p>
 * todo:
 * 1. user needs to be connected the first time to login
 * 1.1 user credentials get saved
 * 1.2 user can login offline if they have logged in before
 * <p>
 * 2. user needs connectivity to register
 * <p>
 * 3. save previously logged in user credentials to local file
 * 3.1 check if credentials have been seen before on this device
 * on login.
 * 4. save 'loggedIn' state to file.
 * 4.1 if loggedIn state is true then go straight to welcome
 * screen
 */

public class MainActivity extends AppCompatActivity {

    private TextView usernameView;
    private Button loginButton;
    private TextView signupButton;
    private currentUser user;
    private MainActivityController mainActivityController;
    private static final String FILENAME = "currentUser.sav";
    private User foundUser;

    /**
     * Activity uses the activity_main.xml layout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Controller*/
        mainActivityController = new MainActivityController(this);

        /*initial singleton current user*/
        user.getInstance();

        /* initalize request manager */
        RequestManager.getInstance().setContext(getApplicationContext());
        IntentFilter connectionFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getApplicationContext().registerReceiver(RequestManager.getInstance(), connectionFilter);

        /* setup view vars */
        loginButton = findViewById(R.id.logInButton);
        signupButton = findViewById(R.id.SignUp);
        usernameView = findViewById(R.id.usernameText);

        /* login action */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* TODO: implement username checking */

                // hide keyboard upon pressing button
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(loginButton.getWindowToken(), 0);

                /* check if user exists */
                foundUser = getUser(usernameView.getText().toString());
                if (foundUser != null) {
                    currentUser.getRealInstance().setUser(foundUser);
                    saveLogin();
                    mainActivityController.logIn();
                    finish();
                }
                else {
                    showError("Username does not exist. Please sign up.");
                }

            }
        });

        /*Sign up button*/
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityController.signUp();
            }
        });

        /*Auto Login*/
        loadLogin();
        if(foundUser!=null){
            currentUser.getRealInstance().setUser(foundUser);
            mainActivityController.logIn();
            finish();
        }
    }

    /**
     * Get the user from the user name
     * @param username The username of the user
     * @return User
     */
    public User getUser(String username) {
        GetUserByUsernameRequest getUserByUsernameRequest = new GetUserByUsernameRequest(username);
        RequestManager.getInstance().invokeRequest(getUserByUsernameRequest);
        return getUserByUsernameRequest.getResult();
    }

    /**
     * Display an error snackbar
     * @param err The error
     */
    public void showError(String err) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.MainActivityPage), err,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void loadLogin() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            foundUser = gson.fromJson(in, User.class);
        } catch (FileNotFoundException e) {
            foundUser = null;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Save user Login info
     */
    private void saveLogin() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(currentUser.getInstance(), out);
            out.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
