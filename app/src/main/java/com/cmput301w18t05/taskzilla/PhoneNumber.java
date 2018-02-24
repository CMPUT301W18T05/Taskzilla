/* PhoneNumber
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
 * Created by wyatt on 22/02/18.
 */

public class PhoneNumber {

    private long phone;
    public PhoneNumber(long phone) {
        this.phone = phone;
    }

    public PhoneNumber(){

    }

    /**
     * set phone number
     * @param phone
     * @author Micheal-Nguyen
     */

    public void setPhoneNumber(long phone){
        this.phone = phone;
    }

    /**
     * get the phone number and return it
     * @return
     */
    public long getPhoneNumber(){
        return phone;
    }


    /**
     * convert phone number from long to string
     * @return
     * @author Micheal-Nguyen
     */
    public String toString() {
        return Long.toString(phone);
    }


}




