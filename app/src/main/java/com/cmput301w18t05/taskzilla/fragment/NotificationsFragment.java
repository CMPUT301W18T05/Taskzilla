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

package com.cmput301w18t05.taskzilla.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cmput301w18t05.taskzilla.Notification;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.controller.NotificationsController;
import com.cmput301w18t05.taskzilla.currentUser;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class NotificationsFragment extends Fragment {

    private ArrayList<Notification> notificationList;
    private ListView notificationListView;
    private ArrayAdapter<Notification> adapter;
    private NotificationsController notificationsController;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_notifications, container, false);

        notificationList = new ArrayList<>();
        notificationListView = relativeLayout.findViewById(R.id.NotificationListView);
        notificationsController = new NotificationsController(this, getActivity(), currentUser.getInstance());

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, notificationList);
        notificationListView.setAdapter(adapter);

        // get all notifications currently available
        notificationsController.getNotificationsRequest();
        return relativeLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        //Add dummy notifications
        //notificationList.add("Notification Test 1");
        //notificationList.add("Notification Test 2");


    }

    public void notifyChange() {
        notificationList.clear();
        notificationList.addAll(notificationsController.getResults());
        adapter.notifyDataSetChanged();
    }

    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }


}
