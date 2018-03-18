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

import android.location.Location;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.controller.GetBidByUserIdController;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddBidRequest;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.GetBidsByTaskIdRequest;
import com.cmput301w18t05.taskzilla.request.command.GetUserRequest;
import com.cmput301w18t05.taskzilla.request.command.RemoveBidRequest;
import com.cmput301w18t05.taskzilla.request.command.UpdateTaskRequest;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Created by wyatt on 22/02/18.
 *
 * @// TODO: 23/02/18 AddPhoto implementation
 */

public class Task {

    private String name;

    @JestId
    private String Id;

    private String providerId;
    private String providerUsername;
    private String requesterId;
    private String requesterUsername;

    private String status;
    private String description;
    private Location location;
    private Bid bestBid;
    private ArrayList<Bid> bids;
    private ArrayList<Photo> photos;

    public Task() {
        photos = new ArrayList<>();
        name = "TEST TASK";
        //retrieveBids();
    }

    public Task(String name, User TaskRequester, String description) {
        photos = new ArrayList<>();
        this.name = name;
        this.requesterId = TaskRequester.getId();
        this.requesterUsername = TaskRequester.getUsername();
        this.status = "requested";
        this.description = description;
    }

    public Task(String name, User TaskRequester, String description, Location location) {
        photos = new ArrayList<>();
        this.name = name;
        this.requesterId = TaskRequester.getId();
        this.requesterUsername = TaskRequester.getUsername();
        this.status = "requested";
        this.description = description;
        this.location = location;
    }
    public Task(String name, User TaskRequester, String description, Location location, ArrayList<Photo> photos) {
        photos = new ArrayList<>();
        this.name = name;
        this.requesterId = TaskRequester.getId();
        this.requesterUsername = TaskRequester.getUsername();
        this.status = "requested";
        this.description = description;
        this.location = location;
        this.photos = photos;
    }
    /**
     * addBid
     * Insert into sorted bid list
     *
     * @author praharen
     *
     */
    public void addBid(Bid newbid) {
        GetBidsByTaskIdRequest getbidrequest = new GetBidsByTaskIdRequest(this.Id);
        RequestManager.getInstance().invokeRequest(getbidrequest);
        ArrayList<Bid> bidlist = getbidrequest.getResult();
        for (Bid bid : bidlist) {
            if (bid.getUserId().equals(newbid.getUserId())) {
                RemoveBidRequest removerequest = new RemoveBidRequest(bid);
                RequestManager.getInstance().invokeRequest(removerequest);
                break;
            }
        }

        AddBidRequest addBidRequest = new AddBidRequest(newbid);
        RequestManager.getInstance().invokeRequest(addBidRequest);
    }

    /**
     * addPhoto
     * Add photo to photo list
     *
     * @param photo
     */
    public void addPhoto(Photo photo) {

    }

    /**
     * getPhoto
     * get photo at index i
     *
     * @param i
     * @return Photo
     */
    public Photo getPhoto(int i) {
        return this.photos.get(i);
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void removePhoto(int i) {
        this.photos.remove(i);
    }

    public int numPhotos() {
        return this.photos.size();
    }

    public int numBids() {
        return bids.size();
    }

    public Bid getBid(int i) {
        return bids.get(i);
    }

    public ArrayList<Bid> getBids() {
        return retrieveBids();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getTaskRequester() {
        return userRequest(this.requesterId);
    }

    public void setTaskRequester(User taskRequester) {
        this.requesterId = taskRequester.getId();
        this.requesterUsername = taskRequester.getUsername();
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public User getTaskProvider() {
        return userRequest(this.requesterId);
    }

    public void setTaskProvider(User taskProvider) {
        this.providerId = taskProvider.getId();
        this.providerUsername = taskProvider.getUsername();
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
                (this.status.equals("bidded") && newStatus.equals("requested") && this.retrieveBids().isEmpty())) {
            this.status = newStatus;
            AddTaskRequest request = new AddTaskRequest(this);
            RequestManager.getInstance().invokeRequest(request);
        }
        // if newStatus == "assigned" delete all bids under this task
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Bid getBestBid() {
        return bestBid;
    }

    public String toString(){
        return "Name: " + name + "\nStatus: " + status;
    }

    private User userRequest(String uid) {
        GetUserRequest getUser = new GetUserRequest(uid);
        RequestManager.getInstance().invokeRequest(getUser);

        return getUser.getResult();
    }

    /**
     * get bids from elastic search
     */
    private ArrayList<Bid> retrieveBids() {
        GetBidsByTaskIdRequest getBids = new GetBidsByTaskIdRequest(this.getId());
        RequestManager.getInstance().invokeRequest(getBids);
        return getBids.getResult();
    }
}
