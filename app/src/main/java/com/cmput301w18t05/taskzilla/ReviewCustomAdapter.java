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

package com.cmput301w18t05.taskzilla;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * Custom list view adapter for displaying reviews
 * shows review title, rating, and description
 *
 * @author myapplestory
 *
 */

public class ReviewCustomAdapter extends ArrayAdapter<Review> {

    public ReviewCustomAdapter(Context context, int layoutResource, ArrayList<Review> reviewList) {
        super(context, layoutResource, reviewList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Review review = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_review,
                    parent, false);
        }

        TextView listReviewTitle = convertView.findViewById(R.id.listReviewTitle);
        TextView listReviewRating = convertView.findViewById(R.id.listReviewRating);
        TextView listReviewDescription = convertView.findViewById(R.id.listReviewDescription);

        assert review != null;
        listReviewTitle.setText(review.getTitle());
        listReviewTitle.setTextColor(0xff3f3f3f);
        listReviewTitle.setTextSize(20);
        listReviewRating.setText(String.format(Locale.CANADA, "%.1f", review.getRating()));
        listReviewRating.setTextColor(0xFF323232);
        if (review.getDescription().equals("")){
            final String text = "No Description";
            listReviewDescription.setText(text);
        } else {
            listReviewDescription.setText(review.getDescription());
        }
        return convertView;
    }
}

