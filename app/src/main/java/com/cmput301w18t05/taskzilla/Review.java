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
import com.cmput301w18t05.taskzilla.request.command.AddReviewRequest;

import io.searchbox.annotations.JestId;

/**
 * Represents a review object in the app
 *
 * @author cechoi
 *
 * @version 1.0
 */
public class Review {

    @JestId
    private String id;

    private String title;
    private Float rating;
    private String description;
    private String targetUserID;
    private String reviewerID;
    private String reviewType;

    /**
     * Constructs a review instance using the given parameters
     * @param title title of the review
     * @param rating
     * @param description
     * @param targetUserID
     * @param reviewerID
     */
    public Review(String title, Float rating, String description, String targetUserID,
                  String reviewerID, String type){
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.targetUserID = targetUserID;
        this.reviewerID = reviewerID;
        this.reviewType = type;
    }

    /**
     * Gets the title of the review
     * @return title of the review
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title if the review
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the rating of the review
     * @return rating of the user
     */
    public Float getRating() {
        return rating;
    }

    /**
     * Set the rating of the review
     * @param rating
     */
    public void setRating(Float rating) {
        this.rating = rating;
    }

    /**
     * Get the description of the review
     * @return the description of the review
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the review
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the ID for the target of the review
     * @return the ID of the user that is being reviewed
     */
    public String getTargetUserID() {
        return targetUserID;
    }

    /**
     * Set the ID for the target of the review
     * @param targetUserID
     */
    public void setTargetUserID(String targetUserID) {
        this.targetUserID = targetUserID;
    }

    /**
     * Get the ID for the Reviewer of the review
     * @return the ID of the user that created the review
     */
    public String getReviewerID() {
        return reviewerID;
    }

    /**
     * Set the ID for the review of the review
     * @param reviewerID
     */
    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

    /**
     * returns the review type
     * @return the review type
     */
    public String getReviewType() {
        return reviewType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void updateThis() {
        AddReviewRequest task = new AddReviewRequest(this);
        RequestManager.getInstance().invokeRequest(task);
    }

    public String toString(){
        return this.getTitle() + " " + this.getDescription() + " " + this.getReviewType();
    }
}
