package com.cmput301w18t05.taskzilla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class NewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("New Task");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }
}
