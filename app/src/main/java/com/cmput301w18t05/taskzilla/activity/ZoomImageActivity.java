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
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.cmput301w18t05.taskzilla.AppColors;
import com.cmput301w18t05.taskzilla.Photo;
import com.cmput301w18t05.taskzilla.R;

/**
 * Created by kio22 on 2018-04-06.
 */

public class ZoomImageActivity extends AppCompatActivity{
    private ScaleGestureDetector mScaleDetector;
    private Photo photo;
    private ImageView ZoomedImageView;
    private Matrix matrix = new Matrix();
    private Float scale = 1f;
    private ScaleGestureDetector dector;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);
        String stringOfImage = getIntent().getStringExtra("Photo");

        AppColors appColors = AppColors.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(appColors.getActionBarColor())));
        actionBar.setTitle(Html.fromHtml("<font color='"+ appColors.getActionBarTextColor() +
                "'>Taskzilla</font>"));

        photo = new Photo(stringOfImage);
        ZoomedImageView = findViewById(R.id.ZoomedImage);
        ZoomedImageView.setImageBitmap(photo.StringToBitmap());
        dector = new ScaleGestureDetector(this, new ScaleListener());

    }

    public boolean onTouchEvent(MotionEvent event) {
        dector.onTouchEvent(event);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            scale = scale *  scaleGestureDetector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 10.0f));
            ZoomedImageView.setScaleX(scale);
            ZoomedImageView.setScaleY(scale);
            return true;
        }
    }
}
