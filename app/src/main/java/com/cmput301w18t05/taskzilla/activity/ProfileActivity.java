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

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmput301w18t05.taskzilla.AppColors;
import com.cmput301w18t05.taskzilla.Photo;
import com.cmput301w18t05.taskzilla.Review;
import com.cmput301w18t05.taskzilla.ReviewCustomAdapter;
import com.cmput301w18t05.taskzilla.controller.ProfileController;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetReviewsByUserIdRequest;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Activity for viewing user profile
 */
public class ProfileActivity extends AppCompatActivity {
    private ProfileController profileController;
    private String userID;
    private User user;

    private TextView nameField;
    private TextView emailField;
    private TextView phoneField;
    private TextView numRequestsField;
    private TextView numTasksDoneField;
    private TextView providerRatingField;
    private TextView requesterRatingField;
    private ImageView profilePicture;

    private String name;
    private String email;
    private String phone;
    private String numRequests;
    private String numTasksDone;
    private AppColors appColors;

    /**
     * onCreate
     * Set fields in the activity_profile.xml
     * using ProfileController to retrieve the data of
     * the user through Elastic Search
     *
     * @author Micheal-Nguyen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        appColors = AppColors.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(appColors.getActionBarColor())));
        actionBar.setTitle(Html.fromHtml("<font color='"+ appColors.getActionBarTextColor() +
                "'>Taskzilla</font>"));

        findViews();
        setValues();

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilePictureClicked();
            }
        });
        providerRatingField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                providerRatingOnClick();
            }
        });
        requesterRatingField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requesterRatingOnClick();
            }
        });
    }

    public void ProfilePictureClicked(){
        Intent intent = new Intent(this,ZoomImageActivity.class);
        intent.putExtra("Photo", user.getPhoto().toString());
        startActivity(intent);
    }


    public void findViews(){
        nameField = findViewById(R.id.nameField2);
        emailField = findViewById(R.id.emailField2);
        phoneField = findViewById(R.id.phoneField2);
        numRequestsField = findViewById(R.id.numRequestsField);
        numTasksDoneField = findViewById(R.id.numTasksDoneField);
        providerRatingField = findViewById(R.id.providerRatingField);
        requesterRatingField = findViewById(R.id.requesterRatingField);
        profilePicture = findViewById(R.id.profilePictureView);
    }


    public void setValues(){
        userID = getIntent().getStringExtra("user id");
        this.profileController = new ProfileController(this.findViewById(android.R.id.content),this);
        profileController.setUserID(userID);
        profileController.getUserRequest();
        user = profileController.getUser();

        name = user.getName();
        try {
            email = user.getEmail().toString();
            phone = user.getPhone().toString();
        }
        catch(Exception e){
            email = "No Email";
            phone = "No Number";
        }

        numRequests = profileController.getNumberOfRequests(user.getUsername());
        numTasksDone = profileController.getNumberOfTasksDone(user.getUsername());

        nameField.setText(name);
        emailField.setText(email);
        phoneField.setText(phone);
        numRequestsField.setText(numRequests);
        numTasksDoneField.setText(numTasksDone);
        providerRatingField.setText(String.format(Locale.CANADA,
                "%.1f", user.getProviderRating()));
        requesterRatingField.setText(String.format(Locale.CANADA,
                "%.1f", user.getRequesterRating()));

        try {
            profilePicture.setImageBitmap(user.getPhoto().StringToBitmap());
        }
        catch (Exception e){
            Photo defaultPhoto = new Photo("");
            profilePicture.setImageBitmap(defaultPhoto.StringToBitmap());
        }
    }


    public void providerRatingOnClick() {
        final AlertDialog mBuilder = new AlertDialog.Builder(this).create();
        final View mView = getLayoutInflater().inflate(R.layout.dialog_review_list,null);
        final ListView ReviewsListView = mView.findViewById(R.id.ReviewsListView);
        final TextView ReviewBannerTextView = mView.findViewById(R.id.ReviewsBannerTextView);

        GetReviewsByUserIdRequest request = new GetReviewsByUserIdRequest(user.getId());
        RequestManager.getInstance().invokeRequest(request);
        ArrayList<Review> ReviewsList = request.getResult();

        for (Review review : ReviewsList) {
            if (review.getReviewType().equals("r")) {
                ReviewsList.remove(review);
            }
        }

        if (ReviewsList.isEmpty()) {
            ArrayList<String> tempList = new ArrayList<>();
            tempList.add("No reviews yet :/");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, tempList);
            ReviewsListView.setAdapter(adapter);
        } else {
            ArrayAdapter<Review> adapter = new ReviewCustomAdapter(this,
                    R.layout.list_view_review, ReviewsList);
            ReviewsListView.setAdapter(adapter);
        }

        String text = "Reviews for " + currentUser.getInstance().getName() + " as a provider";
        ReviewBannerTextView.setText(text);

        mBuilder.setView(mView);
        mBuilder.show();
    }

    public void requesterRatingOnClick() {
        final AlertDialog mBuilder = new AlertDialog.Builder(this).create();
        final View mView = getLayoutInflater().inflate(R.layout.dialog_review_list,null);
        final ListView ReviewsListView = mView.findViewById(R.id.ReviewsListView);
        final TextView ReviewBannerTextView = mView.findViewById(R.id.ReviewsBannerTextView);

        GetReviewsByUserIdRequest request = new GetReviewsByUserIdRequest(user.getId());
        RequestManager.getInstance().invokeRequest(request);
        ArrayList<Review> ReviewsList = request.getResult();

        for (Review review : ReviewsList) {
            if (review.getReviewType().equals("p")) {
                ReviewsList.remove(review);
            }
        }

        if (ReviewsList.isEmpty()) {
            ArrayList<String> tempList = new ArrayList<>();
            tempList.add("No reviews yet :/");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, tempList);
            ReviewsListView.setAdapter(adapter);
        } else {
            ArrayAdapter<Review> adapter = new ReviewCustomAdapter(this,
                    R.layout.list_view_review, ReviewsList);
            ReviewsListView.setAdapter(adapter);
        }

        String text = "Reviews for " + currentUser.getInstance().getName() + " as a requester";
        ReviewBannerTextView.setText(text);

        mBuilder.setView(mView);
        mBuilder.show();
    }
}
