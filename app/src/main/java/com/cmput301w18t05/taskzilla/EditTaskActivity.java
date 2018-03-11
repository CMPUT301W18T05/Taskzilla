package com.cmput301w18t05.taskzilla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        Task task = new Task(); //Dummy Task
        task.setName("testTask"); //Dummy
        task.setDescription("testDescription"); //Dummy
        EditText TaskName = (EditText) findViewById(R.id.TaskName);
        EditText Description = (EditText) findViewById(R.id.Description);
        TaskName.setText(task.getName());
        Description.setText(task.getDescription());

    }

    public void cancelButton(View view){
        finish();
    }

    public void saveButton(View view){
        /** Add Save Code when ESC and controllers get figured out **/

        /**                                         **/
        finish();

    }
}
