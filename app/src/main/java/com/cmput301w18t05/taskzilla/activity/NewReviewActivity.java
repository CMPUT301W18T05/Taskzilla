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

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.AppColors;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Review;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;
import com.cmput301w18t05.taskzilla.request.command.GetUserRequest;

/**
 * Created by James on 4/7/2018.
 *
 */

public class NewReviewActivity extends AppCompatActivity {

    private Button SaveButton;
    private Button CancelButton;
    private EditText TitleText;
    private EditText DescriptionText;
    private RatingBar RatingBar;
    private TextView nameTextView;

    private String revieweeType;
    private String targetUserId;
    private String targetUserName;
    private String currentUserId;
    private User targetUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);
        AppColors appColors = AppColors.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(appColors.getActionBarColor())));
        actionBar.setTitle(Html.fromHtml("<font color='"+ appColors.getActionBarTextColor() +
                "'>Taskzilla</font>"));

        findViews();
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor(appColors.getActionBarTextColor()), PorterDuff.Mode.SRC_ATOP);
        setValues();
    }


    public void findViews() {
        SaveButton = findViewById(R.id.saveReviewButton);
        CancelButton = findViewById(R.id.cancelReviewButton);
        TitleText = findViewById(R.id.reviewTitle);
        DescriptionText = findViewById(R.id.reviewDescription);
        RatingBar = findViewById(R.id.ratingBar);
        nameTextView = findViewById(R.id.ReviewTextView);
    }

    public void setValues() {
        revieweeType = getIntent().getStringExtra("who");
        targetUserId = getIntent().getStringExtra("id");
        currentUserId = currentUser.getInstance().getId();

        GetUserRequest request = new GetUserRequest(targetUserId);
        request.execute();
        targetUser = request.getResult();
        targetUserName = targetUser.getName();
        nameTextView.setText("Review for " + targetUserName);
    }


    public void reviewSaveButton(android.view.View view) {
        String reviewTitle = TitleText.getText().toString();
        String reviewDescription = DescriptionText.getText().toString();
        Float reviewRating = RatingBar.getRating();

        if (reviewTitle.equals("") || reviewRating == 0.0f) {
            Toast.makeText(this, "Please fill out the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Review review = new Review(reviewTitle, reviewRating, reviewDescription,
                targetUserId, currentUserId, revieweeType);

        Float newRating;
        if (revieweeType.equals("r")) {
            newRating = ((float) (targetUser.getNumReviewsAsRequester()) *
                    targetUser.getRequesterRating() + reviewRating) /
                    (targetUser.getNumReviewsAsRequester() + 1);
            targetUser.setRequesterRating(newRating);
            targetUser.addNumRequesterReviews();

        } else {
            newRating = ((float) targetUser.getNumReviewsAsProvider() *
                    targetUser.getProviderRating() + reviewRating) /
                    (targetUser.getNumReviewsAsProvider() + 1);
            targetUser.setProviderRating(newRating);
            targetUser.addNumProviderReviews();

        }

        AddUserRequest request = new AddUserRequest(targetUser);
        RequestManager.getInstance().invokeRequest(this, request);

        review.updateThis(); // send to ES

        Toast.makeText(this, "Review created for " + targetUserName, Toast.LENGTH_SHORT).show();
        finish();
    }


    public void reviewCancelButton(android.view.View view) {
        finish();
    }

}
