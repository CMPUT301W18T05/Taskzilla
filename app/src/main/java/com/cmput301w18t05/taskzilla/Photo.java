/*Photo
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
 * Represents a photo object in the app
 *
 * @author cechoi
 *
 * @version 1.0
 */
public class Photo {

    private byte image[];

    /**
     * Constructs a  photo instance using the given parameters
     * @param image
     */
    public Photo(byte image[]){
        this.image = image;
    }

    /**
     * Returns the image of the photo
     * @return
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets the image of the photo
     * @param image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Deletes the current image of the photo
     * @param index
     */
    public void deleteImage(int index){
        image = null;
    }
}
