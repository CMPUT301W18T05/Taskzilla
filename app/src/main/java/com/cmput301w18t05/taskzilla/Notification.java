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

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddNotificationRequest;

import io.searchbox.annotations.JestId;


/**
 *  Notification object containing info to be sent to another user
 *
 *  @author Andy
 *
 *  @see NotificationManager
 *  @see com.cmput301w18t05.taskzilla.controller.NotificationsController
 *  @see com.cmput301w18t05.taskzilla.fragment.NotificationsFragment
 *
 *  @version 1
 */

public class Notification {
    private User user;
    private String taskName;
    private String context;
    private String event;
    private String taskID;
    private boolean acknowledged = false;

    @JestId
    private String id;

    public Notification(String event, String taskID, String taskName, String context, User user) {
        this.event = event;
        this.taskID = taskID;
        this.taskName = taskName;
        this.user = user;
        this.context = context;
    }

     String getEvent() {
        return this.event;
    }

    public String getContext() {
        return this.context;
    }

    public String toString() {
          return "Event: " + event + "\nTask: " + taskName + "\nUser: " + user.getUsername();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String nid) {
        this.id = nid;
    }

    public String getTaskID() {
        return this.taskID;
    }

    public User getUser() {
        return this.user;
    }

    /**
     *  If the notification is new, it is set to acknowledge to next time there wont be a
     *  heads up notification shown.
     *
     *  @see NotificationManager
     */

    void acknowledge() {
        if (id == null)
            return;

        this.acknowledged = true;
        AddNotificationRequest task = new AddNotificationRequest(this);
        RequestManager.getInstance().invokeRequest(task);
    }

    boolean isAcknowledged() {
        return acknowledged;
    }
}

