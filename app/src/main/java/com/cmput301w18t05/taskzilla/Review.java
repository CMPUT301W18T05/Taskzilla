package com.cmput301w18t05.taskzilla;

/**
 * Created by Colin on 2018-02-23.
 */

public class Review {

    private String title;
    private int rating;
    private String description;
    private int targetUserID;
    private int reviewerID;

    public Review(String title, int rating, String description, int targetUserID, int reviewerID){
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.targetUserID = targetUserID;
        this.reviewerID = reviewerID;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTargetUserID() {
        return targetUserID;
    }

    public void setTargetUserID(int targetUserID) {
        this.targetUserID = targetUserID;
    }

    public int getReviewerID() {
        return reviewerID;
    }

    public void setReviewerID(int reviewerID) {
        this.reviewerID = reviewerID;
    }



}
