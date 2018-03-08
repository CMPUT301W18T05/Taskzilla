package com.cmput301w18t05.taskzilla;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
    }

    public void viewProfile(android.view.View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
