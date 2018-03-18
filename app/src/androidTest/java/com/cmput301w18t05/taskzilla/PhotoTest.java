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

    public void testDeletePhoto(){
        byte picture[] = {1,0,0,1,1,0};
        Photo photo = new Photo(picture);
        photo.deleteImage();
        assertTrue(photo.getImage()==null);
    }
}
