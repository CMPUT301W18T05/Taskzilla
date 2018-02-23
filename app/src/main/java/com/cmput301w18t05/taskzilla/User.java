package com.cmput301w18t05.taskzilla;

import java.util.ArrayList;

/**
 * Created by wyatt on 22/02/18.
 */

public class User {

    private String name;
    private String username;
    private Integer ID;
    private PhoneNumber phone;
    private EmailAddress email;
    private Float providerRating;
    private Float requesterRating;
    private Integer numRequests;
    private Integer numCompleteTasks;
    private ArrayList<Photo> photos;

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public PhoneNumber getPhone() {
        return phone;
    }

    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public Float getProviderRating() {
        return providerRating;
    }

    public void setProviderRating(Float providerRating) {
        this.providerRating = providerRating;
    }

    public Float getRequesterRating() {
        return requesterRating;
    }

    public void setRequesterRating(Float requesterRating) {
        this.requesterRating = requesterRating;
    }

    public Integer getNumRequests() {
        return numRequests;
    }

    public void setNumRequests(Integer numRequests) {
        this.numRequests = numRequests;
    }

    public Integer getNumCompleteTasks() {
        return numCompleteTasks;
    }

    public void setNumCompleteTasks(Integer numCompleteTasks) {
        this.numCompleteTasks = numCompleteTasks;
    }
}
