package com.cmput301w18t05.taskzilla;

import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t05.taskzilla.activity.MainActivity;

/**
 * Created by James on 2/23/2018.
 *
 *
 */

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest() {
        super(MainActivity.class);
    }

    /**
     * Test for setting the name of user
     */
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

    /**
     * Test for setting the name of username
     */
    public void testSetUsername() {
        User user = new User();

        String username = "James Sun";
        assertFalse(user.setUsername(username));

        username = "C0lin Ch0i";
        assertFalse(user.setUsername(username));

        username = "4ndy_l1";
        assertTrue(user.setUsername(username));

        username = "~Jeremy Ng~";
        assertFalse(user.setUsername(username));

        username = "";
        assertFalse(user.setUsername(username));

        username = "    Wyatt Praharen";
        assertFalse(user.setUsername(username));

        username = "Michael Nguyen Michael Nguyen Michael Nguyen Michael Nguyen ";
        assertFalse(user.setUsername(username));
    }

    /**
     * Test for getting everything
     */
    public void testGetEverything() {
        User user = new User();

        String name = "Asd Dsa";
        String username = "hax04";
        user.setName(name);
        assertEquals(user.getName(), name);

        user.setUsername(username);
        assertEquals(user.getUsername(), username);

        PhoneNumber phone = new PhoneNumber("1234567890");
        user.setPhone(phone);
        assertEquals(user.getPhone(), phone);

        user.setRequesterRating(2.3f);
        assertEquals(user.getRequesterRating(), 2.3f);

        user.setProviderRating(2.3f);
        assertEquals(user.getProviderRating(), 2.3f);

        EmailAddress mail = new EmailAddress("asd@dsa.f");
        user.setEmail(mail);
        assertEquals(user.getEmail(), mail);
    }
}
