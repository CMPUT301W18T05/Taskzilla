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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.cmput301w18t05.taskzilla.R;

import java.util.Calendar;

/**
 * Created by James on 4/7/2018.
 *
 */

public class NewReviewActivity extends AppCompatActivity {

    private Button SaveButton;
    private Button CancelButton;
    private EditText TitleText;
    private EditText DescriptionText;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        actionBar.setTitle(Html.fromHtml("<font color='#00e5ee'>Taskzilla</font>"));

        findViews();

    }







    public void findViews(){
        SaveButton = findViewById(R.id.saveReviewButton);
        CancelButton = findViewById(R.id.cancelReviewButton);
        TitleText = findViewById(R.id.reviewTitle);
        DescriptionText = findViewById(R.id.reviewDescription);
        ratingBar = findViewById(R.id.ratingBar);
    }

    public void setValues(){

    }





}
