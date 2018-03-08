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

import java.util.Queue;

/**
 * RequestManager Singleton
 *
 * Abstraction for handling request objects
 * i.e backlog requests when app is not connected to
 * ElasticSearch server.
 *
 */

public class RequestManager {

    RequestManager instance = new RequestManager();
    private Queue<Request> requestQueue;
    private ConnectivityManager connectivityManager;
    private Context context;

    private RequestManager() {

        // watch for connection change
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    public RequestManager getInstance() {
        return instance;
    }

    public void invokeRequest(Request request) {
        request.execute();
    }

    public void setConnectivityManager(ConnectivityManager cm) {
        this.connectivityManager = cm;
    }

    public void setContext(Context c) {
        context = c;
    }

}
