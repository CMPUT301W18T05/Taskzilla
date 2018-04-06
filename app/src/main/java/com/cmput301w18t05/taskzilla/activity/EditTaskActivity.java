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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.Photo;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.RecyclerViewAdapter;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.currentUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Activity for editing a task
 */
public class EditTaskActivity extends AppCompatActivity {
    private Task task;
    private Context ctx;
    private Integer PICK_IMAGE = 5;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private LinearLayout linearLayout;
    private User CurrentUser = currentUser.getInstance();
    private Integer maxSize;
    private ArrayList<Photo> photos;
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
        EditText TaskNameText = (EditText) findViewById(R.id.TaskName);
        EditText DescriptionText = (EditText) findViewById(R.id.Description);
        String taskName = getIntent().getStringExtra("task Name");
        String taskDescription = getIntent().getStringExtra("Description");
        task.setName(taskName); //Dummy
        task.setDescription(taskDescription); //Dummy
        TaskNameText.setText(task.getName());
        DescriptionText.setText(task.getDescription());
        photos = new ArrayList<Photo>();
        linearLayout = (LinearLayout) findViewById(R.id.Pictures);
        recyclerView = (RecyclerView) findViewById(R.id.listOfPhotos);
        recyclerViewLayoutManager = new LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(ctx, photos);
        recyclerView.setAdapter(recyclerViewAdapter);
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
                maxSize = 65536;
                Log.i("ACTUAL SIZE", String.valueOf(selectedImage.getByteCount()));
                Integer width = 1200;
                Integer height = 1200;
                Bitmap resizedImage;
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.JPEG,50,stream);
                Log.i("size",String.valueOf(stream.size()));
                while(stream.size()>maxSize){
                    width = width - 200;
                    height = height - 200;
                    stream = new ByteArrayOutputStream();
                    resizedImage = Bitmap.createScaledBitmap(selectedImage, width, height, false);
                    resizedImage.compress(Bitmap.CompressFormat.JPEG,50,stream);
                    Log.i("size",String.valueOf(stream.size()));
                }

                byte byteImage[];
                byteImage = stream.toByteArray();
                String image = Base64.encodeToString(byteImage, Base64.DEFAULT);
                photos.add(new Photo(image));
                Log.i("hi",String.valueOf(photos.size()));
                recyclerViewAdapter.notifyDataSetChanged();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(EditTaskActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(EditTaskActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

}
