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

package com.cmput301w18t05.taskzilla.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.controller.SearchController;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetAllTasksRequest;
import com.cmput301w18t05.taskzilla.request.command.SearchTaskRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;

/**
 * Activity for viewing maps
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GetAllTasksRequest getAllTasksRequest;
    private ArrayList<Task> tasks;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location myLocation;
    private double lon;
    private double lat;
    /**
     * Activity uses the activity_map.xml layout
     * Initializes a map fragment
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.TaskLocation);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        getAllTasksRequest = new GetAllTasksRequest();
        RequestManager.getInstance().invokeRequest(getApplicationContext(),getAllTasksRequest);

        tasks = new ArrayList<>();

        while(getAllTasksRequest.getResult().size()>0){
            tasks.addAll(getAllTasksRequest.getResult());
            RequestManager.getInstance().invokeRequest(getApplicationContext(),getAllTasksRequest);
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        getLocation();
        mMap = googleMap;

        myLocation = new Location("You");
        myLocation.setLatitude(lat);
        myLocation.setLongitude(lon);
        myLocation.setTime(new Date().getTime());

        // Add a marker to your location and move the camera
        LatLng yourLocation = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(yourLocation).title("Your Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(yourLocation));
        moveToCurrentLocation(yourLocation);

        mMap.addCircle(new CircleOptions()
                .center(yourLocation)
                .radius(5000).strokeColor(Color.RED).strokeWidth((float) 5.0));

        Location taskLocation;
        //*Add locations of tasks*//
        for(Task t : tasks){

            if (t.getLocation()!=null) {
                taskLocation = new Location("Task");
                taskLocation.setLatitude(t.getLocation().latitude);
                taskLocation.setLongitude(t.getLocation().longitude);
                taskLocation.setTime(new Date().getTime()); //Set time as current Date

                if (myLocation.distanceTo(taskLocation) <= 5000) {
                    LatLng taskslonlat = new LatLng(t.getLocation().latitude, t.getLocation().longitude);
                    if(t.getStatus().equalsIgnoreCase("requested")) {
                        mMap.addMarker(new MarkerOptions().position(taskslonlat).title(t.getName()).snippet("No Bids")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTag(t.getId());
                    }
                    if(t.getStatus().equalsIgnoreCase("bidded")) {
                        mMap.addMarker(new MarkerOptions().position(taskslonlat).title(t.getName()).snippet("Best Bid: $"+t.getBestBid().toString())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).setTag(t.getId());
                    }
                }
            }
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                if(marker.getTag()!=null){
                    Intent intent = new Intent(getApplicationContext(), ViewTaskActivity.class);
                    intent.putExtra("TaskId",marker.getTag().toString());
                    startActivity(intent);
                }

            }
        });
    }

    void getLocation() {
        if( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


            if (location != null){
                lat = location.getLatitude();
                lon = location.getLongitude();
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                getLocation();
                break;
        }
    }

    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

    }
}

