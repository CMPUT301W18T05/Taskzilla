/* EmailAddressTest
 *
 * Version 1.0
 *
 * Feb 23, 2018
 *
 * Copyright 2018 Team05, CMPUT301, University of Alberta - All Rights Reserved
 *
 */

package com.cmput301w18t05.taskzilla;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by andyl on 2/23/2018.
 */

public class EmailAddressTest extends ActivityInstrumentationTestCase2{

    public EmailAddressTest() {
        super(MainActivity.class);
    }

    /**
     *  Test to check if username matches
     */

    public void testGetEmailUsername() {
        String email = "Taskzilla@ualberta.ca";
        String username = "Taskzilla";

        EmailAddress mail = new EmailAddress(email);

        assertEquals(mail.getEmailUsername(),username);
    }

    /**
     * Test to check if domain matches
     */

    public void testGetEmailDomain() {
        String email = "Taskzilla@ualberta.ca";
        String domain = "ualberta.ca";

        EmailAddress mail = new EmailAddress(email);

        assertEquals(mail.getEmailDomain(),domain);
    }

    /**
     * Test to check if set new email works and if it does, check if exception is thrown when invalid email is inserted
     */

    public void testSetEmail() {
        String email = "Taskzilla@ualberta.ca";
        String invalidEmail = "Task@zilla@ualberta.ca";
        String newEmail = "Taskzilla2@hotmail.ca";

        String username = "Taskzilla2";
        String domain = "hotmail.ca";

        EmailAddress mail = new EmailAddress(email);
        mail.setEmail(newEmail);

        assertEquals(mail.getEmailUsername(), username);
        assertEquals(mail.getEmailDomain(), domain);

        mail.setEmail(invalidEmail);
    }

}
