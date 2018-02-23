package com.cmput301w18t05.taskzilla;

/**
 * Created by wyatt on 22/02/18.
 */

public class PhoneNumber {

    private long phone;
    public PhoneNumber(long phone) {
        this.phone = phone;
    }

    public PhoneNumber(){

    }

    public void setPhoneNumber(long phone){
        this.phone = phone;
    }

    public long getPhoneNumber(){
        return phone;
    }


    public String toString() {
        return Long.toString(phone);
    }


}




