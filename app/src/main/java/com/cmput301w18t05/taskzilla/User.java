package com.cmput301w18t05.taskzilla;

import java.util.ArrayList;

/**
 * Created by wyatt on 22/02/18.
 *
 */

public class User {

    private String name;
    private String username;
    private String id;
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
        return this.name;
    }

    public boolean setName(String name) {
        // if name contains illegal characters, or is longer than 25 characters return false
        // otherwise set the user's name
        if (name.matches("[a-zA-Z_0-9][a-zA-Z0-9 ]") || (name.length() <= 25)) {
            this.name = name;
            return true;
        }
        return false;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean setUsername(String username) {
        if (username.matches("[a-zA-Z_0-9][a-zA-Z_0-9 ]") || (username.length() <= 25)) {
            this.username = username;
            return true;
        }
        return false;
    }

    public String getId() {
        return this.id;
    }

    public void setId(Integer id) {
        //dont need. generate random id for user at init
    }

    public PhoneNumber getPhone() {
        return this.phone;
    }

    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
    }

    public EmailAddress getEmail() {
        return this.email;
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public Float getProviderRating() {
        return this.providerRating;
    }

    public boolean setProviderRating(Float providerRating) {
        if (providerRating < 5.0f || providerRating > 0.0f) {
            this.providerRating = providerRating;
            return true;
        }
        return false;
    }

    public Float getRequesterRating() {
        return this.requesterRating;
    }

    public boolean setRequesterRating(Float requesterRating) {
        if (requesterRating < 5.0f || requesterRating > 0.0f) {
            this.requesterRating = requesterRating;
            return true;
        }
        return false;
    }

    public Integer getNumRequests() {
        return this.numRequests;
    }

    public void setNumRequests(Integer numRequests) {
        this.numRequests = numRequests;
    }

    public Integer getNumCompleteTasks() {
        return this.numCompleteTasks;
    }

    public void setNumCompleteTasks(Integer numCompleteTasks) {
        this.numCompleteTasks = numCompleteTasks;
    }

    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
}
