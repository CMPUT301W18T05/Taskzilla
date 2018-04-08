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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cmput301w18t05.taskzilla.Notification;
import com.cmput301w18t05.taskzilla.NotificationManager;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.controller.NotificationsController;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetNotificationsByUserIdRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByProviderUsernameRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByRequesterUsernameRequest;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class NotificationsFragment extends Fragment {

    private ArrayList<Notification> notificationList;
    private ListView notificationListView;
    private ArrayAdapter<Notification> adapter;
    private NotificationsController notificationsController;
    private Notification currentNotification;
    private String taskId;
    private String notificationId;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_notifications, container, false);

        notificationList = new ArrayList<>();
        new NotificationsFragment.pollNotifications(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        notificationListView = relativeLayout.findViewById(R.id.NotificationListView);
        notificationsController = new NotificationsController(this, getActivity(), currentUser.getInstance());

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, notificationList);
        notificationListView.setAdapter(adapter);
        notificationListView.setClickable(true);

        // get all notifications for the user currently available
        notificationsController.getNotificationsRequest();

        notificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                taskId = notificationList.get(i).getTaskID();

                if(notificationsController.checkTaskExistRequest(taskId))
                    viewTask(taskId);
                else {
                    AlertDialog.Builder deleteNotifcationAlert = new AlertDialog.Builder(getContext());
                    deleteNotifcationAlert.setTitle("Task no longer exists!");
                    deleteNotifcationAlert.setMessage("Do you want to remove this notification?");
                    deleteNotifcationAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            notificationsController.removeNotificationRequest(taskId, i);
                            dialogInterface.dismiss();
                        }
                    });
                    deleteNotifcationAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    deleteNotifcationAlert.show();
                }
            }
        });

        notificationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                currentNotification = notificationList.get(position);
                notificationId = currentNotification.getId();
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Remove Notification?");
                alert.setMessage("Are you sure you want to delete this notification?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // remove notification
                        notificationsController.removeNotificationRequest(notificationId, position);
                        dialogInterface.dismiss();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });

        return relativeLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    public void viewTask(String taskId){
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        intent.putExtra("TaskId", taskId);
        startActivityForResult(intent, 1);
    }

    public void notifyChange() {
        notificationList.clear();
        notificationList.addAll(notificationsController.getResults());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            notificationsController.getNotificationsRequest();
        }
    }

    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public static class pollNotifications extends AsyncTask<Void, Void, Void> {
        NotificationsFragment listener;
        public pollNotifications(NotificationsFragment listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                while (true) {
                    Thread.sleep(5000);
                    listener.notificationsController.getNotificationsRequest();
                }
            }
            catch (Exception e) {
                System.out.println("Something went wrong with the poll notifications");
            }
            return null;
        }
    }
}
