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

/**
 * Custom listView adapter for displaying tasks
 * Shows the task title, requester, status, and lowest bid (if any)
 *
 * @author Jeremy, myapplestory
 */
public class TaskCustomAdapter extends ArrayAdapter<Task> {

    public TaskCustomAdapter(Context context, int layoutResource, ArrayList<Task> taskList) {
        super(context, layoutResource, taskList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);
        assert task != null;
        User user = task.getTaskRequester();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tasks_list_view2,
                    parent, false);
        }
        TextView taskTitleView = convertView.findViewById(R.id.taskTitle);
        TextView requesterUsernameView = convertView.findViewById(R.id.requesterUsername);
        TextView taskStatusView = convertView.findViewById(R.id.taskStatus);
        TextView lowestBidView = convertView.findViewById(R.id.lowestBid);
//        ImageView requesterImage = convertView.findViewById(R.id.SearchListRequesterPicture);

        // Set the values for all the views
        taskTitleView.setText(task.getName());
        taskTitleView.setTextColor(0xff3f3f3f);
        taskTitleView.setTextSize(20);
        String requesterName = "Requester: " + user.getName();
        requesterUsernameView.setText(requesterName);
        requesterUsernameView.setTextColor(0xFF323232);
        String taskStatus = "Status: " + task.getStatus();
        taskStatusView.setText(taskStatus);

//        try {
//            requesterImage.setImageBitmap(user.getPhoto().StringToBitmap());
//        }
//        catch (Exception e){
//            Photo defaultPhoto = new Photo("");
//            requesterImage.setImageBitmap(defaultPhoto.StringToBitmap());
//        }

        // Check if the best bid is null or <= 0
        try {
            Float bestBid = task.getBestBid();
            if (bestBid < 0) {
                lowestBidView.setText("Lowest Bid: None");
            } else {
                lowestBidView.setText("Best Bid: " + "$" + String.format("%.2f",bestBid));
            }
        } catch (Exception e){
            lowestBidView.setText("Lowest Bid: None");
        }

        return convertView;
    }
}
