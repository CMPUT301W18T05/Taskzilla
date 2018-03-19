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

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Represents a user object in the app
 *
 * @author wyatt
 * @version 1.0
 */
public class User {

    protected String name;
    protected String username;

    @JestId
    protected String id;

    protected PhoneNumber phone;
    protected EmailAddress email;
    protected Float providerRating;
    protected Float requesterRating;
    protected Integer numRequests;
    protected Integer numCompleteTasks;
    protected ArrayList<Photo> photos;

    public User() {
    }

    /**
     * Constructs a user instance using the given parameters
     * @param name Name of the user
     * @param username Unique username of the user
     * @param id Unique id of the user
     * @param phone Phone number of the user
     * @param email Email address of the user
     * @param providerRating The provider rating of the user
     * @param requesterRating The requester rating of the user
     * @param numRequests The total number of request made by the user
     * @param numCompleteTasks The number of tasks the user has completed for others
     * @param photos Profile picture of the user
     */
    public User(String name, String username, String id,
                PhoneNumber phone, EmailAddress email,
                double providerRating, double requesterRating,
                Integer numRequests, Integer numCompleteTasks, ArrayList<Photo> photos){
        this.name = name;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.providerRating = (float) providerRating;
        this.requesterRating = (float) requesterRating;
        this.numRequests = numRequests;
        this.numCompleteTasks = numCompleteTasks;
        this.photos = photos;
    }

    /**
     * Returns the name of the user
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the user
     * @param name
     * @return
     */
    public boolean setName(String name) {
        // if name contains illegal characters, or is longer than 25 characters return false
        // otherwise set the user's name
        if (name.matches("[a-zA-Z_0-9][a-zA-Z0-9 ]") || (name.length() <= 25)) {
            this.name = name;
            return true;
        }
        return false;
    }

    /**
     * Returns the username of the user
     * @return
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username of the user.
     * Error checks the user name
     * @param username
     * @return Returns True is username was successfully set. False if the username fails the check
     */
    public boolean setUsername(String username) {
        if (username.matches("[a-zA-Z_0-9][a-zA-Z_0-9 ]") || (username.length() <= 25)) {
            this.username = username;
            return true;
        }
        return false;
    }

    /**
     * Returns the id of the user
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id of the user
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the phone number of the user
     * @return
     */
    public PhoneNumber getPhone() {
        return this.phone;
    }

    /**
     * Sets the phone number of the user
     * @param phone
     */
    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
    }

    /**
     * Returns the email address of the user
     * @return
     */
    public EmailAddress getEmail() {
        return this.email;
    }

    /**
     * Sets the email address of the user
     * @param email
     */
    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    /**
     * Returns the provider rating of the user
     * @return
     */
    public Float getProviderRating() {
        return this.providerRating;
    }

    /**
     * Sets the provider rating of the user.
     * Checks that the rating is a float between 0 and 5
     * @param providerRating
     * @return Returns true if rating was successfully set. False if setting fails
     */
    public boolean setProviderRating(Float providerRating) {
        if (providerRating < 5.0f || providerRating > 0.0f) {
            this.providerRating = providerRating;
            return true;
        }
        return false;
    }

    /**
     * Sets the provider rating of the user.
     * Checks that the rating is a double between 0 and 5
     * @param providerRating
     * @return Returns true if rating was successfully set. False if setting fails
     */
    public boolean setProviderRating(double providerRating) {
        if (providerRating < 5.0f || providerRating > 0.0f) {
            this.providerRating = new Float(providerRating);
            return true;
        }
        return false;
    }

    /**
     * Returns the requester rating of the user
     * @return
     */
    public Float getRequesterRating() {
        return this.requesterRating;
    }

    /**
     * Sets the Requester rating of the user
     * @param requesterRating
     * @return
     */
    public boolean setRequesterRating(double requesterRating) {
        return this.setRequesterRating(new Float(requesterRating));
    }

    /**
     * Sets the requester rating of the user.
     * Checks that the rating is a float between 0 and 5
     * @param requesterRating
     * @return Returns true if rating was successfully set. False if setting fails
     */
    public boolean setRequesterRating(Float requesterRating) {
        if (requesterRating < 5.0f || requesterRating > 0.0f) {
            this.requesterRating = requesterRating;
            return true;
        }
        return false;
    }

    /**
     * Returns the total number of requests made by the user
     * @return
     */
    public Integer getNumRequests() {
        return this.numRequests;
    }

    /**
     * Set the total number of requests made by the user
     * @param numRequests
     */
    public void setNumRequests(Integer numRequests) {
        this.numRequests = numRequests;
    }

    /**
     * Returns the number of task completed by the user
     * @return
     */
    public Integer getNumCompleteTasks() {
        return this.numCompleteTasks;
    }

    /**
     * sets the number of tasks completed by the buser
     * @param numCompleteTasks
     */
    public void setNumCompleteTasks(Integer numCompleteTasks) {
        this.numCompleteTasks = numCompleteTasks;
    }

    /**
     * Returns the users profile picture
     * @return
     */
    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }

    /**
     * Sets a profile picture for the user
     * @param photos
     */
    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    /**
     * Returns a string of the users name and id to be displayed
     * @return
     */
    public String toString() {
        return this.name+" "+this.id;
    }
}
