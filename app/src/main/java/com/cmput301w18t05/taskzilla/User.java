package com.cmput301w18t05.taskzilla;

import java.util.ArrayList;

/**
 * Created by wyatt on 22/02/18.
 */

public class User {

    private String name;
    private String username;
    private Integer id;
    private PhoneNumber phone;
    private EmailAddress email;
    private Float providerRating;
    private Float requesterRating;
    private Integer numRequests;
    private Integer numCompleteTasks;
    private ArrayList<Photo> photos;

    public User() {

    }

}
