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

package com.cmput301w18t05.taskzilla.request;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;


import java.util.ArrayList;


/**
 * RequestManager SINGLETON
 *
 * Created by wyatt on 07/03/18.
 *
 * Abstraction for handling request objects
 * i.e backlog requests when app is not connected to
 * ElasticSearch server.
 *
 * @author praharen wyatt
 */

public class RequestManager extends BroadcastReceiver {

    private static final RequestManager instance = new RequestManager();
    private boolean isConnected = false; // be careful when testing...
    private static ArrayList<Request> requestQueue = new ArrayList<Request>();
    private Context ctx;

    private RequestManager() {
    }

    /* singleton class */
    public static RequestManager getInstance() {
        return instance;
    }

    /**
     * activities should call this method when wanting to
     * execute a new request. request manager will handle the
     * logic of offline/online connectivity.
     * @param request the request object with instructions on
     *                how to execute the request.
     */
    public void invokeRequest(Request request) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnected()) {
            request.execute();
        }
        else {
            // todo: implement execute offline here!!
            Log.i("IMPORTANT", "DEVICE IS OFFLINE!!!!");
            requestQueue.add(request);
        }
    }
    public void invokeRequest(Context ctx, Request request) {
        this.invokeRequest(request);
    }

    /**
     * onReceive,
     *
     * called when connectivity status changes.
     * This method then either executes waiting jobs if
     * now online or ...
     *
     * @param context context when connectivity changes
     * @param intent broadcast intent sent by android sys.
     */
    @Override
    public void onReceive(final Context context, final Intent intent) {
        System.out.println("Network status change:");

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni == null) {
            System.out.println("No ConnectivityManager in context.");
            this.isConnected = false;
        }
        else if (!ni.isConnected()) {
            System.out.println("Network interface is not connected.");
            this.isConnected = false;
        }
        else if (ni.isConnected()) {
            System.out.println("Network is active.");
            this.isConnected = true;
            if (!requestQueue.isEmpty()) {
                executeLogTask flushJobs = new executeLogTask();
            }
        }
    }

    /**
     * executeLogTask is a wrapper to execute jobs in the request queue
     * asynchronously.
     */
    public static class executeLogTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            while (!requestQueue.isEmpty()) {
                Request curRequest = popJob();
                curRequest.execute();
            }
            return null;
        }
    }

    private static Request popJob() {
        Request req = requestQueue.get(requestQueue.size()-1);
        requestQueue.remove(requestQueue.size()-1);
        return req;
    }

    public void setContext(Context ctx) {
        this.ctx = ctx;
    }

}
