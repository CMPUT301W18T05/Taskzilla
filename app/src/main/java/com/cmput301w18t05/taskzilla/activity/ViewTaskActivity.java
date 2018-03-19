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

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import android.widget.EditText;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.ExpandableBidListAdapter;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.controller.ViewTaskController;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetBidsByTaskIdRequest;

import java.util.ArrayList;

/**
 * Activity for viewing a task
 */
public class ViewTaskActivity extends AppCompatActivity {
    private String taskID;
    private ViewTaskController viewTaskController;
    private Task task;
    private String taskStatus;
    private String currentUserId;
    private String taskUserId;
    private String description;
    private User TaskRequester;
    private User TaskProvider;
    private String taskName;
    private ArrayList<Bid> BidList;

    private TextView ProviderName;
    private TextView DescriptionView;
    private TextView RequesterName;
    private TextView TaskName;
    private TextView TaskStatus;

    private ImageButton EditButton;
    private ImageButton DeleteButton;
    private ImageButton ProviderPicture;
    private ImageButton RequesterPicture;

    private ExpandableListView BidslistView;
    private Button PinkButton;

    /**onCreate
     * Retrieve the task using the task id that was sent using
     * intent into the activity updating the information on the
     * activity_ViewTaskActivity while checking if the task has
     * a provider, what the status is and if the user viewing
     * is the owner of the task
     *
     * @param savedInstanceState
     * @author Micheal-Nguyen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        EditButton = findViewById(R.id.EditButton);
        DeleteButton = findViewById(R.id.DeleteButton);
        ProviderPicture = findViewById(R.id.ProviderPicture);
        RequesterPicture = findViewById(R.id.RequesterPicture);
        ProviderName = findViewById(R.id.ProviderName);
        DescriptionView = findViewById(R.id.Description);
        RequesterName = findViewById(R.id.RequesterName);
        TaskName = findViewById(R.id.TaskName);
        TaskStatus = findViewById(R.id.TaskStatus);
        BidslistView = findViewById(R.id.BidsListView);
        PinkButton = findViewById(R.id.PinkButton);

        this.viewTaskController = new ViewTaskController(this.findViewById(android.R.id.content),this);
        taskID = getIntent().getStringExtra("TaskId");

        viewTaskController.setTaskID(taskID);
        viewTaskController.getTaskRequest();
        task = viewTaskController.getTask();
        currentUserId = currentUser.getInstance().getId();
        taskUserId = task.getTaskRequester().getId();

        taskName = task.getName();
        taskStatus = task.getStatus();
        description = task.getDescription();

        TaskRequester = task.getTaskRequester();
        try {
            TaskProvider = task.getTaskProvider();
        }
        catch (Exception e){
            TaskProvider = new User();
        }
        RequesterName.setText(TaskRequester.getName());
        DescriptionView.setText(description);
        TaskName.setText(taskName);
        TaskStatus.setText(taskStatus);

        if (currentUserId.equals(taskUserId)) {
            DeleteButton.setVisibility(View.VISIBLE);
            PinkButton.setVisibility(View.INVISIBLE);
            if (taskStatus.equals("requested") || taskStatus.equals("bidded")) {
                EditButton.setVisibility(View.VISIBLE);
            } else {
                EditButton.setVisibility(View.INVISIBLE);
            }
        } else {
            DeleteButton.setVisibility(View.INVISIBLE);
            EditButton.setVisibility(View.INVISIBLE);
        }

        if (taskStatus.equals("assigned")) {
            ProviderPicture.setVisibility(View.VISIBLE);
            ProviderName.setVisibility(View.VISIBLE);
            ProviderName.setText(TaskProvider.getName());


//            LinearLayout.LayoutParams detailsLayout =
//            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT);
//            detailsLayout.setMargins(0,999,0,0);
//            DescriptionView.setLayoutParams(detailsLayout);

        }

        /**
         * ProviderPicture and RequesterPicture
         * when provider or requester picture clicked in
         * activity_view_task.xml pass user id through intent
         * and start the ProfileActivity
         *
         * @author Micheal-Nguyen
         */
        ProviderPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                    intent.putExtra("user id", TaskProvider.getId());
                    startActivity(intent);
                } catch (Exception e){
                }
            }

        });

        RequesterPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                    intent.putExtra("user id", TaskRequester.getId());
                    startActivity(intent);
                } catch (Exception e){
                }
            }
        });

        //Edit Task Button
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditTaskActivity.class);
                intent.putExtra("task Name", taskName);
                intent.putExtra("Description", description);
                startActivityForResult(intent, 1);
            }
        });

        /**
         * DeleteButton
         * in the activity_view_taskxml, when the delete button is pressed
         * prompt user with a confirmation dialog.
         * upon confirmation call vieTaskController to remove
         * the task through elastic search
         *
         * @author Micheal-Nguyen
         */
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewTaskActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete?");
                //DELETE CODE
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewTaskController.RemoveTaskRequest(task);
                        dialogInterface.dismiss();

                        Intent intent = new Intent();
                        intent.putExtra("result", true);
                        setResult(RESULT_OK,intent);

                        finish();
                    }
                });

                //DELETE CANCEL CODE
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
            }
        });

        // get all of this task's bids and pass it into expandable list to display
        // @author myapplestory
        BidList = new ArrayList<>();
        GetBidsByTaskIdRequest getBidsByTaskIdRequest = new GetBidsByTaskIdRequest(this.taskID);
        RequestManager.getInstance().invokeRequest(getBidsByTaskIdRequest);
        BidList.addAll(getBidsByTaskIdRequest.getResult());
        ExpandableListAdapter expandableListAdapter= new ExpandableBidListAdapter(this, BidList);
        BidslistView.setAdapter(expandableListAdapter);
    }


    /**
     *@param view pretty much the page it's on
     * @author myapplestory
     * thePinkButton
     * upon pressing place button on task page
     * prompts user to enter in a bid amount
     * if valid input, will add bid to task
     *
     * notes
     * can probably add more stuff to dialog
     */
    public void thePinkButton(android.view.View view) {
        final AlertDialog mBuilder = new AlertDialog.Builder(ViewTaskActivity.this).create();
        final View mView = getLayoutInflater().inflate(R.layout.dialog_place_bid,null);
        final EditText incomingBidText = mView.findViewById(R.id.place_bid_edittext);
        //bring up keyboard when user taps place bid
        InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        final Button submitBidButton = mView.findViewById(R.id.submit_bid_button);
        submitBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float incomingBidFloat;
                try {
                    incomingBidFloat = Float.parseFloat(incomingBidText.getText().toString());
                    incomingBidFloat = (float) (Math.round(incomingBidFloat * 100.0) / 100.0);
                } catch (Exception exception) {
                    Toast.makeText(ViewTaskActivity.this,
                            "Please enter in a valid bid amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                // do stuff here to actually add bid
                task.addBid(new Bid(currentUserId, taskID, incomingBidFloat));
                task.setStatus("bidded");
                TaskStatus.setText("bidded");

                Toast.makeText(ViewTaskActivity.this, "Bid placed", Toast.LENGTH_SHORT).show();

                // hide keyboard upon pressing button
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(submitBidButton.getWindowToken(), 0);

                mBuilder.dismiss();
            }
        });
        mBuilder.setView(mView);
        mBuilder.show();
    }


    /**
     * onActivityResult
     * upon return from EditTaskActivity update
     * the details of teh task and call viewTaskController
     * to update the details of the task
     * also update the activity_profile.xml to reflect
     * the changes in the task
     *
     * @author Micheal-Nguyen
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (1): {
                //code to add to ESC
                if (resultCode == RESULT_OK) {
                    taskName = data.getStringExtra("Task Name");
                    description = data.getStringExtra("Description");
                    task.setName(taskName);
                    task.setDescription(description);
                    viewTaskController.updateTaskRequest(task);
                    TextView DescriptionView = findViewById(R.id.Description);
                    TextView TaskNameView = findViewById(R.id.TaskName);
                    TaskNameView.setText(taskName);
                    if (description.length() > 0) {
                        DescriptionView.setText(description);
                    } else {
                        DescriptionView.setText("No Description");
                    }
                }
            }
        }
    }
}

