package com.cmput301w18t05.taskzilla;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Colin on 2018-03-13.
 */

public class currentUser extends User {

    private static currentUser user = null;
    private final String FILENAME = "currentUser.sav";

    private currentUser(User u){
        super("tom","myuniqueUN","1",new PhoneNumber(),new EmailAddress("Tom1@gmail.com"),
                8.4, 2.2, 4, 6,new ArrayList<Photo>());
    }

    public void initUser(User u){
        if (user == null) {
            user = new currentUser(u);
        }
    }
    public static currentUser getInstance() {
        return user;
    }

}
