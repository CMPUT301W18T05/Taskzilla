package com.cmput301w18t05.taskzilla;



import java.util.ArrayList;

/**
 * Created by Colin
 */

public class currentUser extends User {
    private static currentUser instance = new currentUser();
    private static User current = null;

    private currentUser(){
        //Get the actual values from elastic search
        //super("Wyatt P","wyatt","1",new PhoneNumber("7802493469"),new EmailAddress("praharen@ualberta.ca"),
                //8.4, 2.2, 4, 6,new ArrayList<Photo>());
    }

    public static User getInstance() {
        return currentUser.current;
    }

    public static currentUser getRealInstance() {
        return instance;
    }

    public void setUser(User user) {
        currentUser.current = user;
    }
}
