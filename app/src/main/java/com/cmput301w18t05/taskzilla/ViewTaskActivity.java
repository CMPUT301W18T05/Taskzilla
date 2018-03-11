package com.cmput301w18t05.taskzilla;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class ViewTaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        ImageButton EditButton = (ImageButton) findViewById(R.id.EditButton);
        ImageButton DeleteButton = (ImageButton) findViewById(R.id.DeleteButton);
        int currentUserId = 5;                              //Dummy for Testing
        int taskUserId = 5;                                 //Dummy for Testing
        String taskStatus = "requested";
        if(currentUserId == taskUserId && taskStatus == "requested" ){
            EditButton.setVisibility(View.VISIBLE);
        } else {
            EditButton.setVisibility(View.INVISIBLE);
        }
        if(currentUserId == taskUserId){
            DeleteButton.setVisibility(View.VISIBLE);
        } else{
            DeleteButton.setVisibility(View.INVISIBLE);
        }


    }

    public void viewProfile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void editTask(View view){
        Intent intent = new Intent(this, EditTaskActivity.class);
        startActivity(intent);
    }

    public void DeleteTask(View view){

    }
}
