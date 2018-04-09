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

import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t05.taskzilla.activity.MainActivity;

/**
 * Created by Colin on 2018-02-23.
 */

public class ReviewTest extends ActivityInstrumentationTestCase2 {
    public ReviewTest(){
        super(MainActivity.class);
    }

    /**
     * Test Getting the review title
     */
    public void testGetTitle(){
        String title = "Best Review Ever";
        Float rating = 5.0f;
        String description = "A++ Very Good";
        String tID = "1234";
        String rID = "4321";
        Review review = new Review(title, rating, description, tID, rID);
        assertTrue(review.getTitle().equals(title));
    }

    /**
     * Test setting a new review title
     */
    public void testSetTitle(){
        String title = "Best Review Ever";
        Float rating = 5.0f;
        String description = "A++ Very Good";
        String tID = "1234";
        String rID = "4321";
        Review review = new Review(title, rating, description, tID, rID);
        String title2 = "Worse Review Ever";
        review.setTitle(title2);
        assertTrue(review.getTitle().equals(title2));
    }

    /**
     * Test getting the review rating
     */
    public void testGetRating(){
        String title = "Best Review Ever";
        Float rating = 5.0f;
        String description = "A++ Very Good";
        String tID = "1234";
        String rID = "4321";
        Review review = new Review(title, rating, description, tID, rID);
        assertTrue(review.getRating()==rating);
    }

    /**
     * Test setting a new review rating
     */
    public void testSetRating(){
        String title = "Best Review Ever";
        Float rating = 5.0f;
        String description = "A++ Very Good";
        String tID = "1234";
        String rID = "4321";
        Review review = new Review(title, rating, description, tID, rID);
        Float rating2 = 1.0f;
        review.setRating(rating2);
        assertTrue(review.getRating()==rating2);
    }

    /**
     * Test getting the review description
     */
    public void testGetDescription(){
        String title = "Best Review Ever";
        Float rating = 5.0f;
        String description = "A++ Very Good";
        String tID = "1234";
        String rID = "4321";
        Review review = new Review(title, rating, description, tID, rID);
        assertTrue(review.getDescription().equals(description));
    }

    /**
     * Test setting a new review description
     */
    public void testSetDescription(){
        String title = "Best Review Ever";
        Float rating = 5.0f;
        String description = "A++ Very Good";
        String tID = "1234";
        String rID = "4321";
        Review review = new Review(title, rating, description, tID, rID);
        String description2 = "Hate this guy :(";
        review.setDescription(description2);
        assertTrue(review.getDescription().equals(description2));
    }

    /**
     * Test getting the target's ID
     */
    public void testGetTID(){
        String title = "Best Review Ever";
        Float rating = 5.0f;
        String description = "A++ Very Good";
        String tID = "1234";
        String rID = "4321";
        Review review = new Review(title, rating, description, tID, rID);
        assertTrue(review.getTargetUserID()==tID);
    }

    /**
     * Test setting a new target's ID
     */
    public void testSetTID(){
        String title = "Best Review Ever";
        Float rating = 5.0f;
        String description = "A++ Very Good";
        String tID = "1234";
        String rID = "4321";
        Review review = new Review(title, rating, description, tID, rID);
        String tID2 = "1";
        review.setTargetUserID(tID2);
        assertTrue(review.getTargetUserID()==tID2);
    }

    /**
     * Test getting the Reviewer's ID
     */
    public void testGetRID(){
        String title = "Best Review Ever";
        Float rating = 5.0f;
        String description = "A++ Very Good";
        String tID = "1234";
        String rID = "4321";
        Review review = new Review(title, rating, description, tID, rID);
        assertTrue(review.getReviewerID()==rID);
    }

    /**
     * Test setting a new Reviewer's ID
     */
    public void testSetRID(){
        String title = "Best Review Ever";
        Float rating = 5.0f;
        String description = "A++ Very Good";
        String tID = "1234";
        String rID = "4321";
        Review review = new Review(title, rating, description, tID, rID);
        String rID2 = "1";
        review.setReviewerID(rID2);
        assertTrue(review.getReviewerID()==rID2);
    }
}
