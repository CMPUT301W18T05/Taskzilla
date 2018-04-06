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

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


import android.widget.EditText;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.ExpandableBidListAdapter;
import com.cmput301w18t05.taskzilla.Notification;
import com.cmput301w18t05.taskzilla.NotificationManager;
import com.cmput301w18t05.taskzilla.Photo;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.controller.ProfileController;
import com.cmput301w18t05.taskzilla.controller.ViewTaskController;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.fragment.NotificationsFragment;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddNotificationRequest;
import com.cmput301w18t05.taskzilla.request.command.GetBidsByTaskIdRequest;
import com.cmput301w18t05.taskzilla.request.command.GetUserRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for viewing a task
 */
public class ViewTaskActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Task task;
    private String taskName;
    private String taskID;
    private String currentUserId;
    private String taskUserId;
    private String description;
    private User TaskRequester;
    private User TaskProvider;
    private ArrayList<Bid> BidList;
    private Bid selectedBid;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private ViewTaskController viewTaskController;

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
    private Button BlueButton;
    private Button YellowButton;
    private Button PinkButton;
    private ScrollView scrollView;


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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        actionBar.setTitle(Html.fromHtml("<font color='#00e5ee'>Taskzilla</font>"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.TaskLocation);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        findViews();

        // starts the activity at the very top
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        // gets the task id
        this.viewTaskController = new ViewTaskController(this.findViewById(android.R.id.content), this);
        taskID = getIntent().getStringExtra("TaskId");

        setValues();
        setRequesterField();
        setProviderField();

        // taken from https://stackoverflow.com/questions/3465841/how-to-change-visibility-of-layout-programmatically
        // 2018-03-14
        if (currentUserId.equals(taskUserId)) {
            DeleteButton.setVisibility(View.VISIBLE);
            BlueButton.setVisibility(View.INVISIBLE);
            if (task.getStatus().equals("requested")) {
                EditButton.setVisibility(View.VISIBLE);
            } else {
                EditButton.setVisibility(View.INVISIBLE);
            }
        } else {
            DeleteButton.setVisibility(View.INVISIBLE);
            EditButton.setVisibility(View.INVISIBLE);
            YellowButton.setVisibility(View.INVISIBLE);
            PinkButton.setVisibility(View.INVISIBLE);
        }
        if (task.getStatus().equals("assigned")) {
            YellowButton.setVisibility(View.INVISIBLE);
            PinkButton.setVisibility(View.INVISIBLE);
        }

//            LinearLayout.LayoutParams detailsLayout =
//            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT);
//            detailsLayout.setMargins(0,999,0,0);
//            DescriptionView.setLayoutParams(detailsLayout);



        /*
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
                    if (task.getStatus().equals("bidded")) {
                        intent.putExtra("user id", task.getBestBidder());
                    } else {
                        intent.putExtra("user id", TaskProvider.getId());
                    }
                    startActivity(intent);
                } catch (Exception e) {}
            }

        });

        RequesterPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                    intent.putExtra("user id", TaskRequester.getId());
                    startActivity(intent);
                } catch (Exception e) {}
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

        /*
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
                // taken from https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
                // 2018-03-16
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
                        setResult(RESULT_OK, intent);

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
        BidList.clear();
        GetBidsByTaskIdRequest getBidsByTaskIdRequest = new GetBidsByTaskIdRequest(this.taskID);
        RequestManager.getInstance().invokeRequest(getBidsByTaskIdRequest);
        BidList.addAll(getBidsByTaskIdRequest.getResult());
        ExpandableListAdapter expandableListAdapter = new ExpandableBidListAdapter(this, BidList);
        BidslistView.setAdapter(expandableListAdapter);
    }


    /**
     * @param view pretty much the page it's on
     * @author myapplestory
     * theBlueButton
     * upon pressing place button on task page
     * prompts user to enter in a bid amount
     * if valid input, will add bid to task
     *
     * notes
     * can probably add more stuff to dialog
     */
    public void theBlueButton(android.view.View view) {
        final AlertDialog mBuilder = new AlertDialog.Builder(ViewTaskActivity.this).create();
        final View mView = getLayoutInflater().inflate(R.layout.dialog_place_bid,null);
        final EditText incomingBidText = mView.findViewById(R.id.place_bid_edittext);
        // Taken from https://gist.github.com/gaara87/3607765
        // 2018-03-19
        // Limits the number of decimals allowed in input
        incomingBidText.setFilters(new InputFilter[] {
            new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    StringBuilder builder = new StringBuilder(dest);
                    builder.insert(dstart, source);
                    String temp=builder.toString();
                    if (temp.contains(".")) {
                        temp = temp.substring(temp.indexOf(".") + 1);
                        if (temp.length() > 2) {return "";}
                    }
                    return super.filter(source, start, end, dest, dstart, dend);
                }
            }
        });

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
                if (task.getBestBid() > incomingBidFloat || task.getBestBid() == -1.0f) {
                    task.setBestBidder(currentUserId);
                    task.setBestBid(incomingBidFloat);
                } else if (task.getBestBid().equals(incomingBidFloat)) {
                    Toast.makeText(ViewTaskActivity.this,
                            "A similar bid already exists. Please bid another value",
                            Toast.LENGTH_SHORT).show();
                    return;
                } else if (task.getBestBid() < incomingBidFloat && task.getBestBidder().equals(currentUserId)) {
                    task.updateBestBid();
                }
                task.setStatus("bidded");
                TaskStatus.setText("Bidded");
                setProviderField();


                Notification notification = new Notification("bidded", "hi", getIntent(), currentUser.getInstance().getId(), task.getRequesterId());
                //AddNotificationRequest request = new AddNotificationRequest(notification);
                //RequestManager.getInstance().invokeRequest(getApplicationContext(), request);
                NotificationManager.getInstance().createNotification(notification);


                Toast.makeText(ViewTaskActivity.this, "Bid placed", Toast.LENGTH_SHORT).show();

                // hide keyboard upon pressing button
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(submitBidButton.getWindowToken(), 0);

                mBuilder.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateBidsList();
                    }
                }, 1500);
            }
        });
        mBuilder.setView(mView);
        mBuilder.show();
    }


    public void theYellowButton(android.view.View view) {
        final AlertDialog mBuilder = new AlertDialog.Builder(ViewTaskActivity.this).create();
        final View mView = getLayoutInflater().inflate(R.layout.dialog_accept_bid,null);
        final ListView acceptBidListView = mView.findViewById(R.id.AcceptBidList);
        final Button acceptBidButton = mView.findViewById(R.id.AcceptBidButton);
        ArrayList<String> tempList = new ArrayList<>();

        if (BidList.isEmpty()) {
            tempList.add("No bids :'(");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, tempList);
            acceptBidListView.setAdapter(adapter);
            acceptBidButton.setText("SAD");
            acceptBidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBuilder.dismiss();
                }
            });
        } else {
            for (Bid bid : BidList) {
                ProfileController controller = new ProfileController(mView, getBaseContext());
                controller.setUserID(bid.getUserId());
                controller.getUserRequest();
                tempList.add("Best bidder: " + controller.getUser().getName() + "\nBid Amount: " +
                        bid.getBidAmount());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_single_choice, tempList);
            acceptBidListView.setAdapter(adapter);
            acceptBidListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            acceptBidButton.setText("ACCEPT BID");

            acceptBidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedBid = BidList.get(i);
                }
            });

            acceptBidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProfileController controller = new ProfileController(mView, getBaseContext());
                    controller.setUserID(selectedBid.getUserId());
                    controller.getUserRequest();
                    TaskProvider = controller.getUser();
                    task.setTaskProvider(TaskProvider);
                    task.setStatus("assigned");
                    TaskStatus.setText("Assigned");
                    updateBidsList();
                    EditButton.setVisibility(View.INVISIBLE);
                    setProviderField();
                    mBuilder.dismiss();
                }
            });
        }
        mBuilder.setView(mView);
        mBuilder.show();
    }


    public void thePinkButton(android.view.View view) {
        Toast.makeText(this, "dawdwadwwadwad", Toast.LENGTH_SHORT).show();
    }

    public void setRequesterField() {
        String text = "Requester: " + TaskRequester.getName();
        RequesterName.setText(text);
        try {
            RequesterPicture.setImageBitmap(TaskRequester.getPhoto().StringToBitmap());
        } catch (Exception e) {
            Photo defaultPhoto = new Photo("");
            RequesterPicture.setImageBitmap(defaultPhoto.StringToBitmap());
        }
    }


    public void setProviderField() {
        if (task.getStatus().equals("requested")) {
            Photo defaultPhoto = new Photo("");
            ProviderPicture.setImageBitmap(defaultPhoto.StringToBitmap());
            ProviderName.setText("No bidders :'(");
        } else if (task.getStatus().equals("bidded")) {
            ProfileController profileController = new ProfileController(this.findViewById(android.R.id.content),this);
            profileController.setUserID(task.getBestBidder());
            profileController.getUserRequest();
            User tempUser = profileController.getUser();
            String text = "Best bidder: " + tempUser.getName() + "\nBid amount: " + Float.toString(task.getBestBid());
            ProviderName.setText(text);
            try {
                ProviderPicture.setImageBitmap(tempUser.getPhoto().StringToBitmap());
            } catch (Exception e) {
                Photo defaultPhoto = new Photo("");
                ProviderPicture.setImageBitmap(defaultPhoto.StringToBitmap());
            }
        } else if (task.getStatus().equals("assigned")) {
            String text = "Provider: " + TaskProvider.getName();
            ProviderName.setText(text);
            try {
                ProviderPicture.setImageBitmap(TaskProvider.getPhoto().StringToBitmap());
            } catch (Exception e) {
                Photo defaultPhoto = new Photo("");
                ProviderPicture.setImageBitmap(defaultPhoto.StringToBitmap());
            }
        }
    }


    public void findViews(){
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
        scrollView = findViewById(R.id.ViewTaskScrollView);
        BlueButton = findViewById(R.id.BlueButton);
        YellowButton = findViewById(R.id.YellowButton);
        PinkButton = findViewById(R.id.PinkButton);
    }

    public void setValues(){
        viewTaskController.setTaskID(taskID);
        viewTaskController.getTaskRequest();
        task = viewTaskController.getTask();
        currentUserId = currentUser.getInstance().getId();
        taskUserId = task.getTaskRequester().getId();
        taskName = task.getName();
        description = task.getDescription();
        TaskRequester = task.getTaskRequester();
        TaskProvider = task.getTaskProvider();
        BidList = new ArrayList<>();
        TaskName.setText(taskName);
        TaskStatus.setText(task.getStatus());
        DescriptionView.setText(description);
    }

    public void updateBidsList(){
        BidList.clear();
        GetBidsByTaskIdRequest getBidsByTaskIdRequest = new GetBidsByTaskIdRequest(this.taskID);
        RequestManager.getInstance().invokeRequest(getBidsByTaskIdRequest);
        BidList.addAll(getBidsByTaskIdRequest.getResult());
        ExpandableListAdapter expandableListAdapter= new ExpandableBidListAdapter(this, BidList);
        BidslistView.setAdapter(expandableListAdapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker to a location and move the camera
        LatLng taskLocation = task.getLocation();
        mMap.addMarker(new MarkerOptions().position(taskLocation).title("Task Name"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(taskLocation));
        moveToCurrentLocation(taskLocation);

    }

    private void moveToCurrentLocation(LatLng currentLocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

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

