package com.cmput301w18t05.taskzilla.request;

/**
 * Created by wyatt on 07/03/18.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

/**
 * RequestManager Singleton
 *
 * Abstraction for handling request objects
 * i.e backlog requests when app is not connected to
 * ElasticSearch server.
 *
 */

public class RequestManager extends BroadcastReceiver {

    private static final RequestManager instance = new RequestManager();
    private Queue<Request> requestQueue;
    private boolean isConnected = false;

    private RequestManager() {
    }

    public static RequestManager getInstance() {
        return instance;
    }

    public void invokeRequest(Request request) {
        if (isConnected) {
            request.execute();
        }
        else {
            requestQueue.add(request);
        }
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        System.out.println("Network status change:");

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni == null) {
            System.out.println("No ConnectivityManager in context.");
            this.isConnected = false;
        }
        else if (ni.isConnected()) {
            System.out.println("Network interface is not connected.");
            this.isConnected = true;
        }
        else if (!ni.isConnected()) {
            System.out.println("Network is active.");
            this.isConnected = false;
        }
    }

    public void executeBacklog() {
        /* needs to be async process */
    }

        /*final PendingResult pendingResult = goAsync();
        AsyncTask<String, Integer, String> asyncTask = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                // Must call finish() so the BroadcastReceiver can be recycled.
                //pendingResult.finish();
                return "test";
            }
        };
        asyncTask.execute();*/
}
