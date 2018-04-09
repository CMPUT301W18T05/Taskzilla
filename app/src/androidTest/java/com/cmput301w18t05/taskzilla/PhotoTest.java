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

public class PhotoTest extends ActivityInstrumentationTestCase2 {
    public PhotoTest(){
        super(MainActivity.class);
    }

    public void testGetPhoto(){
        String photoString = "Testing photo";
        Photo photo = new Photo(photoString);
        assertTrue(photo.getPhoto().equals(photoString));
    }
    public void testSetPhoto(){
        String photoString1 = "Testing photo1";
        String photoString2 = "Testing photo2";
        Photo photo1 = new Photo(photoString1);
        Photo photo2 = new Photo(photoString2);
        photo1.setPhoto(photoString2);
        assertTrue(photo1.getPhoto().equals(photoString2));
    }

    public void testDeletePhoto(){
        String photoString = "Testing photo3";
        Photo photo = new Photo(photoString);
        photo.deletePhoto();
        assertTrue(photo.getPhoto()==null);
    }

}
