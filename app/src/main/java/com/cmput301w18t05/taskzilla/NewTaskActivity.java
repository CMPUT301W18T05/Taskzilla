package com.cmput301w18t05.taskzilla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewTaskActivity extends AppCompatActivity {

    private NewTaskController newTaskController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Add a Task");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        newTaskController =  new NewTaskController(this, getApplicationContext());
        Button cancel = (Button) findViewById(R.id.CancelButton);
        Button addTask = (Button) findViewById(R.id.addTaskButton);
        final EditText taskName = (EditText) findViewById(R.id.TaskName);
        final EditText taskDescription = (EditText) findViewById(R.id.Description);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskController.cancelTask();
            }
        });

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTaskController.addTask(taskName.getText().toString(),new User(), taskDescription.getText().toString());
            }
        });

    }


}
