/*
 * Copyright 2018 (c) Andy Li, Colin Choi, James Sun, Jeremy Ng, Micheal Nguyen, Wyatt Praharenka
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.cmput301w18t05.taskzilla.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * Activity for editing a task
 */
public class EditTaskActivity extends AppCompatActivity {
    private Task task;
    private Context ctx;
    private Integer PICK_IMAGE = 5;
    private ImageView image_view;

    /**
     * Activity uses the activity_edit_task.xml layout
     * Initialize a task with edited fields
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        task = new Task();
        ctx = getApplicationContext();
        super.onCreate(savedInstanceState);
        setTitle("Edit Task");
        setContentView(R.layout.activity_edit_task);
        image_view = (ImageView) findViewById(R.id.imageView4);
        EditText TaskNameText = (EditText) findViewById(R.id.TaskName);
        EditText DescriptionText = (EditText) findViewById(R.id.Description);
        String taskName = getIntent().getStringExtra("task Name");
        String taskDescription = getIntent().getStringExtra("Description");
        task.setName(taskName); //Dummy
        task.setDescription(taskDescription); //Dummy
        TaskNameText.setText(task.getName());
        DescriptionText.setText(task.getDescription());

    }

    public void TaskCancelButton(View view) {
        finish();
    }

    /**
     * TaskSaveButton
     * Upon pressing the save button in the activity_edit_task.xml
     * check that the information that is inputted is within
     * the constraints set and return
     *
     * @param view
     * @author Micheal-Nguyen
     */
    public void TaskSaveButton(View view) {
        EditText TaskNameText = (EditText) findViewById(R.id.TaskName);
        EditText DescriptionText = (EditText) findViewById(R.id.Description);
        String TaskName = TaskNameText.getText().toString();
        String Description = DescriptionText.getText().toString();

        if (TaskName.length() > 55) {
            TaskNameText.requestFocus();
            TaskNameText.setError("Task Name exceeds 55 characters");
        } else if (TaskName.length() == 0) {
            TaskNameText.requestFocus();
            TaskNameText.setError("Task Name required");
        } else if (Description.length() > 500) {
            DescriptionText.requestFocus();
            DescriptionText.setError("Description length exceeds 500 characters");
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Task Name", TaskName);
            returnIntent.putExtra("Description", Description);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    public void AddPhotoButton(View view){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        // taken from https://stackoverflow.com/questions/38352148/get-image-from-the-gallery-and-show-in-imageview
        // 2018-04-03
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Log.i("test",selectedImage.toString());
                image_view.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(EditTaskActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(EditTaskActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
