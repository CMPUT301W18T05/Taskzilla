package com.cmput301w18t05.taskzilla;

import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.controller.ProfileController;
import com.cmput301w18t05.taskzilla.fragment.ProfileFragment;

/**
 * Created by Jeremy on 2018-02-23.
 */

public class UserProfileActivityTest extends ActivityInstrumentationTestCase2 {
    public UserProfileActivityTest(){
        super(MainActivity.class);
    }

    public void testProviderRatingUpdate() {
        ProfileFragment profileFragment = new ProfileFragment();
        User user = new User();
        ProfileController profileController = new ProfileController(user, profileFragment);
        profileController.updateProviderRating(10.0);
        assertEquals("10.0", profileFragment.getProviderRatingField().getText());
    }

}
