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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EmailAddress
 *
 * Represents a email object in the app
 *
 * @author andy li
 * @version 1
 */
public class EmailAddress {

    private String emailUsername;
    private String emailDomain;

    /**
     * Constructor that takes in a string email address and first checks if its valid.
     * If it is valid, set username and domain accordingly.
     *
     * @param email a email address inserted by the user
     * @see   User
     */

    public EmailAddress(String email) {                         // Constructor used when a string is sent in as a parameter
        if(isValid(email)) {
            String tempArray[] = email.split("@");
            this.emailUsername = tempArray[0];
            this.emailDomain = tempArray[1];
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return  username of the email
     */

    String getEmailUsername() {                          // Returns email username
         return this.emailUsername;
    }

    /**
     * @return  domain of the email
     */

    String getEmailDomain() {                            // Returns email domain
        return this.emailDomain;
    }

    /**
     * @param email new email address specified by the user
     */

    public void setEmail(String email) {                        // Method used to change the email address of the current user
        if(isValid(email)) {
            String tempArray[] = email.split("@");
            this.emailUsername = tempArray[0];
            this.emailDomain = tempArray[1];
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // Taken from https://stackoverflow.com/questions/18463848/how-to-tell-if-a-random-string-is-an-email-address-or-something-else
    // 2018/03/18

    /**
     * This checks if the email is valid by checking if it contains exactly one period and one @.
     *
     * @param email email address specified by the user
     * @return      True if valid, else False
     */

    Boolean isValid(String email) {
        Pattern p = Pattern.compile("[a-zA-z0-9._%+-]{1,}+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]{1,}");
        Matcher m = p.matcher(email);

        boolean matchFound = m.matches();
        if(matchFound) { //valid email
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return  Full email address
     */

    public String toString() {
        return emailUsername + "@" + emailDomain;
    }
}
