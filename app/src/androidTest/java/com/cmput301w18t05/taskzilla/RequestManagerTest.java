package com.cmput301w18t05.taskzilla;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by praharen on 2018-02-23.
 */

public class RequestManagerTest extends ActivityInstrumentationTestCase2 {
    Context ctx;

    public RequestManagerTest(){
        super(MainActivity.class);
        ctx = InstrumentationRegistry.getTargetContext();   // for passing into request manager.
    }

    public void testAddUserRequest() {
        User user = new User();
        user.setName("test name");
        user.setUsername("myuniqueUN");
        user.setRequesterRating(10.0);
        user.setProviderRating(10.0);
        user.setEmail(new EmailAddress("test3@cmput301.com"));
        user.setPhone(new PhoneNumber(1111));

        AddUserRequest test2 = new AddUserRequest(user);
        RequestManager.getInstance().invokeRequest(ctx, test2);

        assertTrue(test2.getResult());
    }

}
