package com.cmput301w18t05.taskzilla;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by James on 2/23/2018.
 *
 *
 */

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest() {
        super(MainActivity.class);
    }


    public void testSetName() {
        User user = new User();

        String name = "James Sun";
        assertTrue(user.setName(name));

        name = "C0lin Ch0i";
        assertTrue(user.setName(name));

        name = "4ndy_l1";
        assertTrue(user.setName(name));

        name = "~Jeremy Ng~";
        assertFalse(user.setName(name));

        name = "";
        assertFalse(user.setName(name));

        name = "    Wyatt Praharen";
        assertFalse(user.setName(name));

        name = "Michael Nguyen Michael Nguyen Michael Nguyen Michael Nguyen ";
        assertFalse(user.setName(name));
    }

    public void testGetEverything() {
        User user = new User();

        String name = "Asd Dsa";
        user.setName(name);
        assertEquals(user.getName(), name);

        user.setUsername(name);
        assertEquals(user.getUsername(), name);

        PhoneNumber phone = new PhoneNumber();
        user.setPhone(phone);
        assertEquals(user.getPhone(), phone);

        user.setRequesterRating(2.3f);
        assertEquals(user.getRequesterRating(), 2.3f);

        user.setProviderRating(2.3f);
        assertEquals(user.getProviderRating(), 2.3f);

        EmailAddress mail = new EmailAddress("asd@dsa.f");
        user.setEmail(mail);
        assertEquals(user.getEmail(), mail);

        user.setNumRequests(1);
        assertEquals(user.getNumRequests(),1d);

        user.setNumCompleteTasks(1);
        assertEquals(user.getNumCompleteTasks(), 1d);
    }


}
