package com.cmput301w18t05.taskzilla;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wyatt on 22/02/18.
 */

public class EmailAddress {

    private String emailUsername;
    private String emailDomain;

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

    public String getEmailUsername() {                          // Returns email username
         return this.emailUsername;
    }

    public String getEmailDomain() {                            // Returns email domain
        return this.emailDomain;
    }

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

    public Boolean isValid(String email) {
        int periodCount = 0;
        int atCount = 0;

        Pattern period = Pattern.compile("([.])");
        Pattern at = Pattern.compile("([@])");

        Matcher periodMatcher = period.matcher(email);
        Matcher atMatcher = at.matcher(email);

        while(periodMatcher.find()) periodCount++;
        while(atMatcher.find()) atCount++;

        if(periodCount == 1 && atCount == 1)
            return true;

        return false;
    }

    public String toString() {
        return emailUsername + "@" + emailDomain;
    }

}
