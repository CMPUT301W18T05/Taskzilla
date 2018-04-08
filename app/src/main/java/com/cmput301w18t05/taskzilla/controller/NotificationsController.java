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

package com.cmput301w18t05.taskzilla.controller;

import android.content.Context;

import com.cmput301w18t05.taskzilla.Notification;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.fragment.NotificationsFragment;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddNotificationRequest;
import com.cmput301w18t05.taskzilla.request.command.GetNotificationsByUserIdRequest;
import com.cmput301w18t05.taskzilla.request.command.RemoveNotificationRequest;

import java.util.ArrayList;

/**
 * Created by Andy on 4/5/2018.
 */

public class NotificationsController {
    private ArrayList<Notification> notificationList;
    private NotificationsFragment view;
    private Context ctx;
    private User cUser;

    public NotificationsController(NotificationsFragment notificationsFragment, Context context, User user) {
        this.notificationList = new ArrayList<>();
        this.ctx = context;
        this.cUser = user;
        this.view = notificationsFragment;
    }

    public void setNotifications(Notification notification) {
        this.notificationList.add(notification);
    }

    public void clearNotifications() {
        notificationList.clear();
        view.notifyChange();
    }

    public ArrayList<Notification> getResults() {
        return this.notificationList;
    }

    public void getNotificationsRequest() {
        //GetNotificationsByUserIdRequest request = new GetNotificationsByUserIdRequest(cUser.getId());
        //RequestManager.getInstance().invokeRequest(ctx, request);

        notificationList.clear();

        ArrayList<Notification> temp;
        //temp = request.getResult();

        //for(Notification n : temp)
        //    notificationList.add(n);

        view.notifyChange();
    }

    // not sure if needed, since notification fragment only deals with deleting notifications and getting available ones
    /*
    public void addNotificationRequest(Notification notification) {
        AddNotificationRequest request = new AddNotificationRequest(notification);
        RequestManager.getInstance().invokeRequest(ctx, request);
    }
    */

    public void removeNotificationRequest(String id, Integer pos) {
        RemoveNotificationRequest request = new RemoveNotificationRequest(id);
        RequestManager.getInstance().invokeRequest(ctx, request);

        notificationList.remove(pos);

        view.notifyChange();
    }
}
