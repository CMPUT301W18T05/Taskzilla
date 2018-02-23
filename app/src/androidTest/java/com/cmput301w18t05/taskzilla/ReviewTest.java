package com.cmput301w18t05.taskzilla;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Colin on 2018-02-23.
 */

public class ReviewTest extends ActivityInstrumentationTestCase2 {
    public ReviewTest(){
        super(MainActivity.class);
    }


    public void testGetTitle(){
        String title = "Best Review Ever";
        int rating = 5;
        String description = "A++ Very Good";
        int tID = 1234;
        int rID = 4321;
        Review review = new Review(title, rating, description, tID, rID);
        assertTrue(review.getTitle().equals(title));
    }

    public void testSetTitle(){
        String title = "Best Review Ever";
        int rating = 5;
        String description = "A++ Very Good";
        int tID = 1234;
        int rID = 4321;
        Review review = new Review(title, rating, description, tID, rID);
        String title2 = "Worse Review Ever";
        review.setTitle(title2);
        assertTrue(review.getTitle().equals(title2));
    }

    public void testGetRating(){
        String title = "Best Review Ever";
        int rating = 5;
        String description = "A++ Very Good";
        int tID = 1234;
        int rID = 4321;
        Review review = new Review(title, rating, description, tID, rID);
        assertTrue(review.getRating()==rating);
    }

    public void testSetRating(){
        String title = "Best Review Ever";
        int rating = 5;
        String description = "A++ Very Good";
        int tID = 1234;
        int rID = 4321;
        Review review = new Review(title, rating, description, tID, rID);
        int rating2 = 1;
        review.setRating(rating2);
        assertTrue(review.getRating()==rating2);
    }

    public void testGetDescription(){
        String title = "Best Review Ever";
        int rating = 5;
        String description = "A++ Very Good";
        int tID = 1234;
        int rID = 4321;
        Review review = new Review(title, rating, description, tID, rID);
        assertTrue(review.getDescription().equals(description));
    }

    public void testSetDescription(){
        String title = "Best Review Ever";
        int rating = 5;
        String description = "A++ Very Good";
        int tID = 1234;
        int rID = 4321;
        Review review = new Review(title, rating, description, tID, rID);
        String description2 = "Hate this guy :(";
        review.setDescription(description2);
        assertTrue(review.getDescription().equals(description2));
    }


    public void testGetTID(){
        String title = "Best Review Ever";
        int rating = 5;
        String description = "A++ Very Good";
        int tID = 1234;
        int rID = 4321;
        Review review = new Review(title, rating, description, tID, rID);
        assertTrue(review.getTargetUserID()==tID);
    }

    public void testSetTID(){
        String title = "Best Review Ever";
        int rating = 5;
        String description = "A++ Very Good";
        int tID = 1234;
        int rID = 4321;
        Review review = new Review(title, rating, description, tID, rID);
        int tID2 = 1;
        review.setTargetUserID(tID2);
        assertTrue(review.getTargetUserID()==tID2);
    }


    public void testGetRID(){
        String title = "Best Review Ever";
        int rating = 5;
        String description = "A++ Very Good";
        int tID = 1234;
        int rID = 4321;
        Review review = new Review(title, rating, description, tID, rID);
        assertTrue(review.getReviewerID()==rID);
    }

    public void testSetRID(){
        String title = "Best Review Ever";
        int rating = 5;
        String description = "A++ Very Good";
        int tID = 1234;
        int rID = 4321;
        Review review = new Review(title, rating, description, tID, rID);
        int rID2 = 1;
        review.setReviewerID(rID2);
        assertTrue(review.getReviewerID()==rID2);
    }
}
