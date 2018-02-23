package com.cmput301w18t05.taskzilla;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by andyl on 2/23/2018.
 */

public class EmailAddressTest extends ActivityInstrumentationTestCase2{

    public EmailAddressTest() {
        super(MainActivity.class);
    }

    public void testGetEmailUsername() {
        String email = "Taskzilla@ualberta.ca";
        String username = "Taskzilla";

        EmailAddress mail = new EmailAddress(email);

        assertEquals(mail.getEmailUsername(),username);
    }

    public void testGetEmailDomain() {
        String email = "Taskzilla@ualberta.ca";
        String domain = "ualberta.ca";

        EmailAddress mail = new EmailAddress(email);

        assertEquals(mail.getEmailDomain(),domain);
    }

    public void testSetEmail() {
        String invalidEmail = "Task@zilla@ualberta.ca";

        EmailAddress mail = new EmailAddress(invalidEmail);
    }
}
