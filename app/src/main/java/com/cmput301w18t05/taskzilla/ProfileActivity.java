package com.cmput301w18t05.taskzilla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String userid = "5";
        String Name = "Micheal Nguyen";
        String Email = "mlnguyen@ualberta.ca";
        String Phone = "911";
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
