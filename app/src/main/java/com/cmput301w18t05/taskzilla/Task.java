package com.cmput301w18t05.taskzilla;

import android.location.Location;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetBidsByTaskIdRequest;

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

    private User TaskRequester;
    private User TaskProvider;
    private String status;
    private String description;
    private Location location;
    private Bid bestBid;
    private ArrayList<Bid> bids;
    private ArrayList<Photo> photos;

    public Task() {
        photos = new ArrayList<Photo>();
        name = "TEST TASK";
        //retrieveBids();
    }

    public Task(String name, User TaskRequester, String description) {
        photos = new ArrayList<Photo>();
        this.name = name;
        this.TaskRequester = TaskRequester;
        this.status = "requested";
        this.description = description;
    }

    public Task(String name, User TaskRequester, String description, Location location) {
        photos = new ArrayList<Photo>();
        this.name = name;
        this.TaskRequester = TaskRequester;
        this.status = "requested";
        this.description = description;
        this.location = location;
    }
    public Task(String name, User TaskRequester, String description, Location location, ArrayList<Photo> photos) {
        photos = new ArrayList<Photo>();
        this.name = name;
        this.TaskRequester = TaskRequester;
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
     * @param bid
     */
    public void addBid(Bid bid) {
        int i;
        for (i = 0; i < bids.size() && bid.compareTo(bids.get(i)) > 0; i++);
        bids.add(i, bid);
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
        return TaskRequester;
    }

    public void setTaskRequester(User taskRequester) {
        this.TaskRequester = taskRequester;
    }

    public User getTaskProvider() {
        return TaskProvider;
    }

    public void setTaskProvider(User taskProvider) {
        this.TaskProvider = taskProvider;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return name;
    }

    /**
     * get bids from elastic search
     */
    private ArrayList<Bid> retrieveBids() {
        if (this.getId() == null) {
            return null;
        }

        GetBidsByTaskIdRequest getBids = new GetBidsByTaskIdRequest(this.getId());
        RequestManager.getInstance().invokeRequest(getBids);

        return getBids.getResult();
    }
}
