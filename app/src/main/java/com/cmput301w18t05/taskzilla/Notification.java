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

import android.content.Intent;

import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.request.Request;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddNotificationRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.GetUserRequest;

import io.searchbox.annotations.JestId;

/**
 * Created by Andy on 4/4/2018.
 */

public class Notification {
    private String title;
    private String context;
    private Intent intent;
    //private String providerId;
    //private String requesterId;
    private User user;
    private String taskName;

    private String senderID;
    private String receiverID;
    private String event;
    private String taskID;
    private boolean acknowledged = false;

    @JestId
    private String id;
/*
    public Notification(String nTitle, String nContext, Intent nIntent, String nSenderId, String nRecieverId, User nUser) {
        this.title = nTitle;
        this.context = nContext;
        this.intent = nIntent;
        this.senderID = nSenderId;
        this.receiverID = nRecieverId;
        this.user = nUser;
        this.taskID = new String();
        this.event = new String();
    }
*/
    public Notification(String event, String senderID, String receiverID, String taskID, String taskName, User user) {
        this.event = event;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.taskID = taskID;
        this.taskName = taskName;
        this.user = user;
    }

    public String getEvent() {
        return this.event;
    }

    /*
    public String getTitle() {
        return this.title;
    }
*/
    public String getContext() {
        return this.context;
    }

    public Intent getNotificationIntent() {
        return this.intent;
    }

    public String getReceiverID() {
        return this.receiverID;
    }

    public String getSenderID() {
        return this.senderID;
    }

    public String toString() {
        //return "ID: " + id + " " + event + " " + senderID + " " + receiverID;

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

    public void acknowledge() {
        if (id == null)
            return;

        this.acknowledged = true;
        AddNotificationRequest task = new AddNotificationRequest(this);
        RequestManager.getInstance().invokeRequest(task);
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public int compareTo(Notification notification) {
        return this.getId().compareTo(notification.getId());
    }
}

