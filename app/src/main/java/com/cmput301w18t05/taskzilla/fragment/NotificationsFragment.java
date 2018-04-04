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
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private ArrayList<String> notificationList;
    private ListView notificationListView;
    private ArrayAdapter<String> adapter;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        notificationList = new ArrayList<>();
        notificationListView = view.findViewById(R.id.NotificationListView);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, notificationList);
        notificationListView.setAdapter(adapter);

        //Add dummy notifications
        notificationList.add("Notification Test 1");
        notificationList.add("Notification Test 2");


    }


}
