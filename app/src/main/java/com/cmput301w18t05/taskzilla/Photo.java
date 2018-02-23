package com.cmput301w18t05.taskzilla;

/**
 * Created by wyatt on 22/02/18.
 */

public class Photo {

    private byte image[];

    public Photo(byte image[]){
        this.image = image;
    }
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void deleteImage(){

    }
}
