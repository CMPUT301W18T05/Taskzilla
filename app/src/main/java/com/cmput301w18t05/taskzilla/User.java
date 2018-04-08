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

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByRequesterUsernameRequest;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Represents a user object in the app
 *
 * @author wyatt
 * @version 1.0
 */
public class User implements Comparable<User> {

    protected String name;
    protected String username;
    protected String password;

    @JestId
    protected String id;

    protected PhoneNumber phone;
    protected EmailAddress email;
    protected Float providerRating;
    protected Float requesterRating;
    protected Integer numRequests;
    protected Integer numCompleteTasks;
    protected Integer numReviewsAsRequester;
    protected Integer numReviewsAsProvider;
    protected Photo photo = new Photo("/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABALDA4MChAODQ4SERATGCgaGBYWGDEjJR0oOjM9PDkz ODdASFxOQERXRTc4UG1RV19iZ2hnPk1xeXBkeFxlZ2P/2wBDARESEhgVGC8aGi9jQjhCY2NjY2Nj Y2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2P/wAARCADwAPADASIA AhEBAxEB/8QAGgABAAMBAQEAAAAAAAAAAAAAAAIDBAEFB//EACoQAQACAQEGBQUBAQAAAAAAAAAB AgMRBCExM1FxEiIyQaETQoGRsVJh/8QAFAEBAAAAAAAAAAAAAAAAAAAAAP/EABQRAQAAAAAAAAAA AAAAAAAAAAD/2gAMAwEAAhEDEQA/APoAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAAAAAAAAAAAAja1axraYiFNtqrHpiZ+AaBkna7e1YI2u3vWAaxnrtVJ9UTX5 XVtFo1rMTAJAAAAAAAAAAAAAAAAAAAAAAM+baIp5ab7fx3aMvgjw19U/DGDtrTadbTrLgAAAO1tN Z1rOkuANmHPF/Lbdb+r3mNmz5fHXS3qj5BeAAAAAAAAAAAAAAAAAAja0VrMzwhJRtdtMcR1kGS1p tabTxlwAAAAAAAHaWml4tHs4A9KJi0RMcJdUbLbXFp0nReAAAAAAAAAAAAAAAAAybZPmrH/Gtk2y N9Z7gzgAAAAAAAAA07H98dmpl2OPXPZqAAAAAAAAAAAAAAAAAUbVXXFr0le5MRaJieEg80SvWaXm s+yIAAAAAAAJY6Te8Vj8g17NXw4onrvXORGkaQ6AAAAAAAAAAAAAAAAAACnPi+pXWPVHBimNJ0nd L01OXDGTfwt1BiEr47Y580flEAAAEqY7ZJ8sfn2ByImZ0jfMtuDF9Ou/1TxMWGuONeNuq0AAAAAA AAAAAAAAAAAAAAAAHJjWN6q2z47cI07LJtWONojvKM5scffAKp2SPa8/ojZI97z+ln18X+/g+vi/ 2Dldnx19te62N3BCM2OfvhKLVnhaJ7SCQAAAAAAAAAAAAAAAAAAIXvXHXW0gmqvnpTdrrPSGbLnt fdHlr0hUC+21Xn0xFflVbJe3G0z+UQAAAAAAEq5L14WlbXarx6oifhQA3Uz0vuidJ6SteYtxZ7U3 T5qg3CFL1vXWs6wmAAAAAAAAAAAACF7xSs2lhvecltbfros2m/iyeGOFf6pAAAAAAAAAAAAAABPH knHbWPzHVupaL1i0cJecv2XJ4b+CeE/0GwAAAAAAAAABG06VmekapK83Jv2Bg113gAAAAAAAAAAA AAAAETpMTHtvAHpROsRLqGLl17QmAAAAAAAAArzcm/ZYrzcm/YGAAAAAAAAAAAAAAAAAAHoYuVXt CaGLlV7QmAAAAAAAAArzcm/ZYrzcm/YGAAAAAAAAAAAAAAAAAAHoYuVXtCaGLlV7QmAAAAAAAAAr zcm/ZYrzcm/YGAAAAAAAAAAAAAAAAAAHoYuVXtCaGLlV7QmAAAAD/9k= ");

    public User() {
        username = "Unknown username";
        name = "Unknown name";
        email = new EmailAddress("Unknown@email.com");
        providerRating = 0.0f;
        requesterRating = 0.0f;
        numRequests = 0;
        numCompleteTasks = 0;
    }

    /**
     * Constructs a user instance using the given parameters
     * @param name Name of the user
     * @param username Unique username of the user
     * @param password The password of the user
     * @param id Unique id of the user
     * @param phone Phone number of the user
     * @param email Email address of the user
     * @param providerRating The provider rating of the user
     * @param requesterRating The requester rating of the user
     * @param numRequests The total number of request made by the user
     * @param numCompleteTasks The number of tasks the user has completed for others
     * @param photo Profile picture of the user
     */
    public User(String name, String username, String password, String id,
                PhoneNumber phone, EmailAddress email, double providerRating,
                double requesterRating, Integer numRequests, Integer numCompleteTasks, Photo photo){
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.providerRating = (float) providerRating;
        this.requesterRating = (float) requesterRating;
        this.numRequests = numRequests;
        this.numCompleteTasks = numCompleteTasks;
        this.photo = photo;
    }

    /**
     * gets the name of the user
     * @return name of the user
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the user
     * @param name
     * @return boolean whether name valid or not
     */
    public boolean setName(String name) {
        // if name contains illegal characters, or is longer than 25 characters return false
        // otherwise set the user's name
        if (name.matches("[a-zA-Z_0-9][a-zA-Z0-9 ]") || (name.length() <= 30)) {
            this.name = name;
            return true;
        }
        return false;
    }

    /**
     * gets the username of the user
     * @return username of the user object
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
        if (username.matches("[a-zA-Z_0-9][a-zA-Z_0-9 ]") || (username.length() <= 30)) {
            this.username = username;
            return true;
        }
        return false;
    }

    /**
     * Sets the password of the user
     * Error checks the password
     * @param password
     * @return boolean
     */
    public boolean setPassword(String password) {
        if (password.matches("[a-zA-Z_0-9][a-zA-Z_0-9 ]") || (password.length() <= 30)) {
            this.password = password;
            return true;
        }
        return false;
    }

    /**
     * Gets the user password
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the id of the user
     * @return id of the user
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the id of the user
     * @param id of the user which is generated by ESC
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the phone number of the user
     * @return phone object of the user
     */
    public PhoneNumber getPhone() {
        return this.phone;
    }

    /**
     * Sets the phone number of the user
     * @param phone number of the user
     */
    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
    }

    /**
     * Returns the email address of the user
     * @return email of the user
     */
    public EmailAddress getEmail() {
        return this.email;
    }

    /**
     * Sets the email address of the user
     * @param email object of the user
     */
    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    /**
     * Returns the provider rating of the user
     * @return provider rating of the user
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
        if (providerRating <= 5.0f || providerRating > 0.0f) {
            this.providerRating = new Float(providerRating);
            return true;
        }
        return false;
    }

    /**
     * Returns the requester rating of the user
     * @return requester rating of the user
     */
    public Float getRequesterRating() {
        return this.requesterRating;
    }

    /**
     * Sets the Requester rating of the user
     * @param requesterRating
     * @return boolean whether requester rating that is attempting to be set valid
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
     * @return number of requests made by user
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
     * @return the number of tasks completed by the user
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

    public Integer getNumReviewsAsRequester() {
        return numReviewsAsRequester;
    }

    public void addNumRequesterReviews() {
        this.numReviewsAsRequester++;
    }

    public Integer getNumReviewsAsProvider() {
        return numReviewsAsProvider;
    }

    public void addNumProviderReviews() {
        this.numReviewsAsProvider++;
    }

    /**
     * Returns the users profile picture
     * @return profile picture of user
     */
    public Photo getPhoto() {
        return this.photo;
    }

    /**
     * Sets a profile picture for the user
     * @param photo
     */
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    /**
     * Returns a string of the users name and id to be displayed
     * @return string version of user object
     */
    public String toString() {
        return this.name+" "+this.id;
    }

    /**
     * Get a list of tasks that this user has requested.
     * @return ArrayList
     */
    public ArrayList<Task> getTasksRequested() {
        ArrayList<Task> res = new ArrayList<>();
        ArrayList<Task> temp;

        GetTasksByRequesterUsernameRequest requestTasks = new GetTasksByRequesterUsernameRequest(this.getUsername());
        RequestManager.getInstance().invokeRequest(requestTasks);
        temp = requestTasks.getResult();

        while (temp != null && !temp.isEmpty()) {
            res.addAll(temp);
            RequestManager.getInstance().invokeRequest(requestTasks);
            temp = requestTasks.getResult();
        }
        return res;
    }

    public int compareTo(User user) {
        return this.getUsername().compareTo(user.getUsername());
    }
}
