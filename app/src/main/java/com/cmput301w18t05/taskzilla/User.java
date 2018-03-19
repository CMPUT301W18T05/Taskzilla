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
     * @param numCompleteTasks
     * @param photos
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

    public void setId(String id) {
        this.id = id;
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

    public boolean setProviderRating(double providerRating) {
        if (providerRating < 5.0f || providerRating > 0.0f) {
            this.providerRating = new Float(providerRating);
            return true;
        }
        return false;
    }

    public Float getRequesterRating() {
        return this.requesterRating;
    }

    public boolean setRequesterRating(double requesterRating) {
        return this.setRequesterRating(new Float(requesterRating));
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

    public String toString() {
        return this.name+" "+this.id;
    }
}
