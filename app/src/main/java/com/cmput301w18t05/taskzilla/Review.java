/*Review
 *
 * Version 1.0
 *
 * Feb 23, 2018
 *
 * Copyright 2018 Team05, CMPUT301, University of Alberta - All Rights Reserved
 *
 */

package com.cmput301w18t05.taskzilla;

/**
 * Represents a review object in the app
 *
 * @author cechoi
 *
 * @version 1.0
 */
public class Review {

    private String title;
    private int rating;
    private String description;
    private int targetUserID;
    private int reviewerID;

    /**
     * Constructs a review instance using the given parameters
     * @param title
     * @param rating
     * @param description
     * @param targetUserID
     * @param reviewerID
     */
    public Review(String title, int rating, String description, int targetUserID, int reviewerID){
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.targetUserID = targetUserID;
        this.reviewerID = reviewerID;
    }

    /**
     * Gets the title of the review
     * @return
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
     * @return
     */
    public int getRating() {
        return rating;
    }

    /**
     * Set the rating of the review
     * @param rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Get the description of the review
     * @return
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
     * @return
     */
    public int getTargetUserID() {
        return targetUserID;
    }

    /**
     * Set the ID for the target of the review
     * @param targetUserID
     */
    public void setTargetUserID(int targetUserID) {
        this.targetUserID = targetUserID;
    }

    /**
     * Get the ID for the Reviewer of the review
     * @return
     */
    public int getReviewerID() {
        return reviewerID;
    }

    /**
     * Set the ID for the review of the review
     * @param reviewerID
     */
    public void setReviewerID(int reviewerID) {
        this.reviewerID = reviewerID;
    }



}
