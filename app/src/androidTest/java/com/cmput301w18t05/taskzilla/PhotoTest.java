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
        byte picture[] = {1,2,3,2,1,};
        Photo photo = new Photo(picture);
        assertTrue(photo.getImage().equals(picture));
    }

}
