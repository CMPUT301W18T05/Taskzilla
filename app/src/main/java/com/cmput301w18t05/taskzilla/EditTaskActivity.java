package com.cmput301w18t05.taskzilla;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditTaskActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        EditText TaskNameText = (EditText) findViewById(R.id.TaskName);
        EditText DescriptionText = (EditText) findViewById(R.id.Description);
        Task task = new Task(); //Dummy Task
        task.setName("testTask"); //Dummy
        task.setDescription("testDescription"); //Dummy
        TaskNameText.setText(task.getName());
        DescriptionText.setText(task.getDescription());

    }

    public void TaskCancelButton(View view){
        finish();
    }

    public void TaskSaveButton(View view){
        EditText TaskNameText = (EditText) findViewById(R.id.TaskName);
        EditText DescriptionText = (EditText) findViewById(R.id.Description);
        String TaskName = TaskNameText.getText().toString();
        String Description = DescriptionText.getText().toString();

        if(TaskName.length() >25){
            TaskNameText.requestFocus();
            TaskNameText.setError("Title length exceeds 25 characters");
        }
        else if(TaskName.length() == 0){
            TaskNameText.requestFocus();
            TaskNameText.setError("Title required");
        }
        else if(Description.length()>280){
            DescriptionText.requestFocus();
            DescriptionText.setError("Description length exceeds 280 characters");
        }


        /** Add Save Code when ESC and controllers get figured out **/

        /**                                         **/

        else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Task Name", TaskName);
            returnIntent.putExtra("Description", Description);
            setResult(RESULT_OK,returnIntent);
            finish();
        }
    }
}
