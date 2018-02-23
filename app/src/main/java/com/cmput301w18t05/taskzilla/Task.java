package com.cmput301w18t05.taskzilla;

import android.location.Location;

import java.util.ArrayList;

/**
 * Created by wyatt on 22/02/18.
 */

public class Task {

    private String name;
    private User TaskRequester;
    private User TaskProvider;
    private TaskStatus status;
    private String description;
    private Location location;
    private ArrayList<Bid> bids;

    public Task() {
        bids = new ArrayList<Bid>();
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

    public int numBids() {
        return bids.size();
    }

    public Bid getBid(int i) {
        return bids.get(i);
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
        TaskRequester = taskRequester;
    }

    public User getTaskProvider() {
        return TaskProvider;
    }

    public void setTaskProvider(User taskProvider) {
        TaskProvider = taskProvider;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
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
}
