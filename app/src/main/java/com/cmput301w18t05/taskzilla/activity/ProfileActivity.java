package com.cmput301w18t05.taskzilla.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cmput301w18t05.taskzilla.controller.ProfileController;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.User;

public class ProfileActivity extends AppCompatActivity {
    private ProfileController profileController;
    private String userID;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userID = getIntent().getStringExtra("user id");
        this.profileController = new ProfileController(this.findViewById(android.R.id.content),this);
        profileController.setUserID(userID);
        profileController.getUserRequest();
        user = profileController.getUser();
        String Name = "DOG";
        String Email = "test@hello.com";
        String Phone = "boom@eoo.com";
        String RequestCount = "5";
        String NumberRequests= "99";
        String NumberTasksDone = "29";

        TextView NameView = (TextView) findViewById(R.id.Name);
        TextView EmailView = (TextView) findViewById(R.id.EmailField);
        TextView PhoneView = (TextView) findViewById(R.id.PhoneField);
        TextView NumberRequestsView = (TextView) findViewById(R.id.NumRequestsField);
        TextView NumberTasksDoneView = (TextView) findViewById(R.id.NumTasksDoneField);
        NameView.setText(Name);
        EmailView.setText(Email);
        PhoneView.setText(Phone);
        NumberRequestsView.setText(NumberRequests);
        NumberTasksDoneView.setText(NumberTasksDone);

    }
}
