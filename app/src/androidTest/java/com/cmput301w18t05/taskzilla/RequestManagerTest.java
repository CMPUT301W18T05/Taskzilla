package com.cmput301w18t05.taskzilla;

import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;

/**
 * Created by praharen on 2018-02-23.
 */

public class RequestManagerTest extends ActivityInstrumentationTestCase2 {
    public RequestManagerTest(){
        super(MainActivity.class);
    }

    public void testAddUserRequest() {
        User user = new User();
        user.setName("Tester12345");

        AddUserRequest request = new AddUserRequest(user);
        RequestManager.getInstance().invokeRequest(request);
    }

}
