/*PhotoTest
 *
 * Version 1.0
 *
 * Feb 23, 2018
 *
 * Copyright 2018 Team05, CMPUT301, University of Alberta - All Rights Reserved
 *
 */
package com.cmput301w18t05.taskzilla;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Colin on 2018-02-23.
 */

public class PhotoTest extends ActivityInstrumentationTestCase2 {
    public PhotoTest(){
        super(MainActivity.class);
    }

    public void testGetPhoto(){
        byte picture[] = {1,0,0,1,1,0};
        Photo photo = new Photo(picture);
        assertTrue(photo.getImage().equals(picture));
    }
    public void testSetPhoto(){
        byte picture[] = {1,0,0,1,1,0};
        Photo photo = new Photo(picture);
        byte picture2[] = {1,1,1,1,1,1};
        photo.setImage(picture2);
        assertTrue(photo.getImage().equals(picture2));
    }


}
