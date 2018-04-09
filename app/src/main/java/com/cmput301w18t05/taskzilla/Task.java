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


import android.support.annotation.NonNull;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddBidRequest;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.GetBidsByTaskIdRequest;
import com.cmput301w18t05.taskzilla.request.command.GetUserRequest;
import com.cmput301w18t05.taskzilla.request.command.RemoveBidRequest;
import com.google.android.gms.maps.model.LatLng;



import java.util.ArrayList;

import io.searchbox.annotations.JestId;


/**
 * Task
 *
 * Represents a task object in the app
 * @author wyatt
 */
public class Task implements Comparable<Task> {

    private String name;

    @JestId
    private String Id;

    private String providerId;
    private String requesterId;
    private String bestBidder;

    private String status;
    private String description;
    private LatLng location;
    private Float bestBid;
    private ArrayList<Photo> photos;

    public Task() {
        photos = new ArrayList<>();
        name = "TEST TASK";
    }

    /**
     * Constructs a Task instance using the given parameters
     * @param name Name of the task
     * @param TaskRequester Name of the user requesting the task
     * @param description Description of the task
     */
    public Task(String name, User TaskRequester, String description) {
        photos = new ArrayList<>();
        this.name = name;
        this.requesterId = TaskRequester.getId();
        this.status = "requested";
        this.description = description;
        this.bestBid = -1.0f;
        this.bestBidder = "";
    }

    /**
     * Constructs a Task instance using the given parameters
     * @param name Name of the task
     * @param TaskRequester Name of the user requesting the task
     * @param description Description of the task
     * @param location Location of the task
     */
    public Task(String name, User TaskRequester, String description, LatLng location) {
        photos = new ArrayList<>();
        this.name = name;
        this.requesterId = TaskRequester.getId();
        this.status = "requested";
        this.description = description;
        this.location = location;
        this.bestBid = -1.0f;
        this.bestBidder = "";
    }

    /**
     * Constructs a Task instance using the given parameters
     * @param name Name of the task
     * @param TaskRequester Name of the user requesting the task
     * @param description Description of the task
     * @param location Location of the task
     * @param photos List of photos related to the task
     */
    public Task(String name, User TaskRequester, String description, LatLng location, ArrayList<Photo> photos) {
        this.name = name;
        this.requesterId = TaskRequester.getId();
        this.status = "requested";
        this.description = description;
        this.location = location;
        this.photos = photos;
        this.bestBid = -1.0f;
        this.bestBidder = "";
    }

    /**
     * addBid
     *
     * @author praharen
     */
    public void addBid(Bid newbid) {
        System.out.println("Adding bid: "+newbid);
        AddBidRequest addBidRequest = new AddBidRequest(newbid);
        RequestManager.getInstance().invokeRequest(addBidRequest);

        String temp = "Your task '" + this.getName() +"' has been bidded on by " + currentUser.getInstance().getUsername() + " for $" + newbid.getBidAmount();

        Notification notification = new Notification("New Bid", this.Id, this.name, temp, currentUser.getInstance());
        NotificationManager.getInstance().sendNotification(notification);
    }

    /**
     * removeAllBids
     * remove all bids under this task
     * @author myapplestory
     */
    private void removeAllBids(){
        GetBidsByTaskIdRequest getbidrequest = new GetBidsByTaskIdRequest(this.Id);
        RequestManager.getInstance().invokeRequest(getbidrequest);
        ArrayList<Bid> bidlist = getbidrequest.getResult();
        for (Bid bid : bidlist) {
            RemoveBidRequest removerequest = new RemoveBidRequest(bid);
            RequestManager.getInstance().invokeRequest(removerequest);

            String temp = "Your bid has been declined!";

            Notification notification = new Notification("Bid Declined", this.Id,
                    this.getName(), temp, currentUser.getInstance());
            NotificationManager.getInstance().sendNotification(notification);
        }
    }

    private void removeHighestBid(){
        this.bestBid = -1.0f;
        this.bestBidder = "";
    }

    /**
     * getPhoto
     * get photo at index i
     *
     * @param i index of the photo in list
     * @return Photo at index i
     */
    public Photo getPhoto(int i) {
        return this.photos.get(i);
    }

    /**
     * Gets a list of all photos
     * @return List of photos
     */
    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    /**
     * Returns a list of all the bids
     * @return Bid
     */
    public ArrayList<Bid> getBids() {
        return retrieveBids();
    }

    /**
     * Gets the name of the task
     * @return name of task
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task
     * @param name that user wants task to be named
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * contact elastic search and return a user object matching
     * the requester id
     * @return User
     */
    public User getTaskRequester() {
        return userRequest(this.requesterId);
    }

    public String getProviderId() {
        return providerId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    /**
     * contact elastic search and return a user object matching
     * the provider id
     * @return User
     */
    public User getTaskProvider() {
        return userRequest(this.providerId);
    }

    public void setTaskProvider(User taskProvider) {
        this.providerId = taskProvider.getId();
    }

    public String getStatus() {
        return this.status;
    }

    /**
     * changes task status to bidded if it is previously requested
     * or changes it to requested if there are no longer any bids
     * @param newStatus incoming new status that may be the new task status
     */
    public void setStatus(String newStatus) {
        if ((this.status.equals("requested") && newStatus.equals("bidded")) ||
            ((this.status.equals("bidded") && newStatus.equals("requested")) &&
                    this.getBids().size() == 1) ||
                (this.status.equals("bidded") && newStatus.equals("assigned"))) {
            this.status = newStatus;
            updateThis();
        }
        // if newStatus is assigned delete all bids under this task
        if (newStatus.equals("assigned")) {
            removeAllBids();
            removeHighestBid();
            updateThis();

            String temp = "Your bid has been accepted!";

            Notification notification = new Notification("Bid Accepted", this.Id, this.name,
                    temp, currentUser.getInstance());
            NotificationManager.getInstance().sendNotification(notification);
        }
    }

    /**
     * get description of the task
     * @return description of task
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description of the task
     * @param description of task
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public LatLng getLocation() {
        return this.location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    /**
     * get the id of the task, which is set by ElasticSearch usually
     * @return id of the task
     */
    public String getId() {
        return Id;
    }

    /**
     * set the id of the task
     * @param id of the task
     */
    public void setId(String id) {
        Id = id;
    }

    /**
     * gets the best bid on the task
     * @return best bid on the task
     */
    public Float getBestBid() {
        return bestBid;
    }

    public void setBestBid(Float bid) {
        this.bestBid = bid;
    }

    public String getBestBidder() {
        return bestBidder;
    }

    public void setBestBidder(String bestBidder) {
        this.bestBidder = bestBidder;
    }

    /**
     * converts the task object to a string
     * @return String form of task
     */
    public String toString(){
        return "Name: " + name + "\nStatus: " + status;
    }

    /**
     * @param uid the user id
     * @return user object
     */
    private User userRequest(String uid) {
        GetUserRequest getUser = new GetUserRequest(uid);
        RequestManager.getInstance().invokeRequest(getUser);

        User user = getUser.getResult();
        if (user == null) // maybe connection is lost
            return new User();
        return user;
    }

    /**
     * get bids from elastic search
     *
     * @return a list of bids
     */
    private ArrayList<Bid> retrieveBids() {
        GetBidsByTaskIdRequest getBids = new GetBidsByTaskIdRequest(this.getId());
        RequestManager.getInstance().invokeRequest(getBids);
        return getBids.getResult();
    }

    public int compareTo(@NonNull Task task) {
        return this.getId().compareTo(task.getId());
    }

    public void updateThis() {
        AddTaskRequest req = new AddTaskRequest(this);
        req.setUpdate(true);
        RequestManager.getInstance().invokeRequest(req);
    }

    public void unassignProvider() {
        providerId = null;
        this.status = "requested";
        updateThis();
    }

    public void completeTask() {
        this.status = "Completed";
        updateThis();

        String temp = this.getName() + " has been completed!";

        Notification notification = new Notification("Task Completed", this.Id, this.name, temp, currentUser.getInstance());
        NotificationManager.getInstance().sendNotification(notification);
    }

    public boolean isComplete() {
        return this.status.equals("Completed");
    }
}
