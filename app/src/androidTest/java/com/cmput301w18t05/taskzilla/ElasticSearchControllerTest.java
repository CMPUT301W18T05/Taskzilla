package com.cmput301w18t05.taskzilla;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by praharen on 2018-02-23.
 */

public class ElasticSearchControllerTest extends ActivityInstrumentationTestCase2 {
    public ElasticSearchControllerTest(){
        super(MainActivity.class);
    }

    public void testAddUser() {
        User user = new User();
        user.setName("Tester");
        user.setPhone(new PhoneNumber());
        user.setEmail(new EmailAddress("test@301.com"));

        ElasticSearchController ESC = new ElasticSearchController();
        ESC.addUser(user);
    }

}
