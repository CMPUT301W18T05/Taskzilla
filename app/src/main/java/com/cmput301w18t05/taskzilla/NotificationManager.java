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

import android.annotation.TargetApi;
import android.content.ContextWrapper;
import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Random;

/**
 * Created by Andy on 4/4/2018.
 */

/**
 * NotificationManager
 *
 * Singleton object that deals with notifications of the current user.
 *
 * @author Andy Li
 * @version 1.0
 */

public class NotificationManager extends ContextWrapper {

    // Taken from https://code.tutsplus.com/tutorials/android-o-how-to-use-notification-channels--cms-28616
    // 2018/04/04

    //public static final String CHANNEL_ID = "com.cmput301w18t05.taskzilla.ANDROID";
    public static final String CHANNEL_ID = "com.cmput301w18t05.taskzilla";
    public static final String ANDROID_CHANNEL_NAME = "Android Channel";
    private int importance = android.app.NotificationManager.IMPORTANCE_DEFAULT;
    private android.app.NotificationManager mManager;
    private static NotificationManager instance = null;

    protected NotificationManager(Context context) {
        super(context);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels(context);
        }
    }

    public static NotificationManager getInstance(Context context) {
        if(instance == null) {
            instance = new NotificationManager(context);
        }
        return instance;
    }

    public static NotificationManager getInstance() {
        return instance;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannels(Context context) {
        NotificationChannel androidChannel = new NotificationChannel(CHANNEL_ID, ANDROID_CHANNEL_NAME, importance);
        androidChannel.enableLights(true);
        androidChannel.enableVibration(true);
        androidChannel.setLightColor(Color.BLUE);

        androidChannel.setLockscreenVisibility(android.app.Notification.VISIBILITY_PRIVATE);

        mManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mManager.createNotificationChannel(androidChannel);
    }

    public void createNotification(Notification notification) {
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notification.getNotificationIntent(), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getContext())
                .setLights(Color.BLUE, 300, 100)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        //.setContentIntent(pendingIntent);

        Random rand = new Random();
        int id = rand.nextInt(1000)+1;

        mManager.notify(id, mBuilder.build());
    }

    public static class pollNotifications extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
