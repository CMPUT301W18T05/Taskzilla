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


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Represents a photo object in the app
 *
 * @author cechoi
 *
 * @version 1.0
 */
public class Photo {

    private String photo;

    /**
     * Constructs a  photo instance using the given parameters
     * @param photo
     */
    public Photo(String photo){
        // taken from https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
        // 2018-04-04
        this.photo = photo;
    }

    /**
     * Sets the image of the photo
     * @param photo
     */
    public void setPhoto(String photo) {
        this.photo=photo;
    }

    /**
     * Returns the image of the photo
     * @return
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Deletes the current image of the photo
     */
    public void deletePhoto(){
        photo = null;
    }

    public String toString() {
        return photo;
    }

    public Bitmap StringToBitmap(){
        try {
            byte [] encodeByte=Base64.decode(photo,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            Bitmap resizedImage = Bitmap.createScaledBitmap(bitmap,350,350, false);
            return resizedImage;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }





}
