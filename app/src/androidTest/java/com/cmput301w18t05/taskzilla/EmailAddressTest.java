/*
 * Copyright 2018 (c) Andy Li, Colin Choi, James Sun, Jeremy Ng, Micheal Nguyen, Wyatt Praharenka
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.cmput301w18t05.taskzilla;
import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t05.taskzilla.activity.MainActivity;

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

        boolean caught = false;
        try {
            mail.setEmail(invalidEmail);
        }
        catch (Exception e) {
            caught = true;
        }
        assertTrue(caught);
    }

}
