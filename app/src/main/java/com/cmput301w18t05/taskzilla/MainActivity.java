package com.cmput301w18t05.taskzilla;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.google.gson.Gson;

/**
 * main activity includes the login screen
 *
 * todo:
 * 1. user needs to be connected the first time to login
 *  1.1 user credentials get saved
 *  1.2 user can login offline if they have logged in before
 *
 * 2. user needs connectivity to register
 *
 * 3. save previously logged in user credentials to local file
 *  3.1 check if credentials have been seen before on this device
 *      on login.
 * 4. save 'loggedIn' state to file.
 *  4.1 if loggedIn state is true then go straight to welcome
 *      screen
 */

public class MainActivity extends AppCompatActivity {

    private TextView usernameView;
    private Button loginButton;
    private TextView signupButton;
    private currentUser user;
    private MainActivityController mainActivityController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Controller*/
        mainActivityController = new MainActivityController(this);

        mainActivityController.checkLoggedIn(new Gson());
        /*initial singleton current user*/
        user.getInstance();


        /* setup view vars */
        loginButton = findViewById(R.id.logInButton);
        signupButton = findViewById(R.id.SignUp);

        /* login action */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* TODO: implement username checking */
                mainActivityController.logIn();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivityController.signUp();
            }
        });

        /* setup request manager */
        IntentFilter connectionFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getApplicationContext().registerReceiver(RequestManager.getInstance(), connectionFilter);


        
    }

}
