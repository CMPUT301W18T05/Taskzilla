package com.cmput301w18t05.taskzilla;

import java.util.ArrayList;

/**
 * Created by Colin on 2018-03-13.
 */

public class currentUser extends User {

    private static currentUser user = null;

    private currentUser(){
        //Get the actual values from elastic search
        super("tom","myuniqueUN","1",new PhoneNumber(),new EmailAddress("Tom1@gmail.com"),
                8.4, 2.2, 4, 6,new ArrayList<Photo>());
    }


    public static currentUser getInstance() {
        if (user == null) {
            user = new currentUser();
        }
        return user;
    }

}
