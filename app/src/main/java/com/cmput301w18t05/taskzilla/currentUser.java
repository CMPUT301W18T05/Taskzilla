package com.cmput301w18t05.taskzilla;



import java.util.ArrayList;

/**
 * Created by Colin on 2018-03-13.
 */

public class currentUser extends User {

    private static currentUser user = null;

    private currentUser(){
        //Get the actual values from elastic search
        super("Wyatt P","wyatt","1",new PhoneNumber(7802493469L),new EmailAddress("praharen@ualberta.ca"),
                8.4, 2.2, 4, 6,new ArrayList<Photo>());
    }


    public static currentUser getInstance() {
        if (user == null) {
            user = new currentUser();
            user.setId("AWI1UerXTLbhBxRrCZea");
        }
        return user;
    }

}
