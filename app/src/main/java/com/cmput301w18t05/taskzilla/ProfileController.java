package com.cmput301w18t05.taskzilla;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cmput301w18t05.taskzilla.User;

/**
 * Created by wyatt on 09/03/18.
 */

public class ProfileController {

    private User user;
    private ProfileFragment view;

    public ProfileController(User user, ProfileFragment view) {
        this.user = user;
        this.view = view;
    }

    public void updateProviderRating(double rating) {
        user.setProviderRating(rating);
        view.notifyChange();
    }

}
