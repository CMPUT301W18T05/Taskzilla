package com.cmput301w18t05.taskzilla;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * main activity includes the login screen
 */

public class MainActivity extends AppCompatActivity {

    private TextView usernameView;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* setup view vars */
        loginButton = findViewById(R.id.logInButton);

        /* login action */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* TODO: implement username checking */

                Intent loginIntent = new Intent(view.getContext(), WelcomeActivity.class);
                startActivity(loginIntent);
            }
        });

    }
}
