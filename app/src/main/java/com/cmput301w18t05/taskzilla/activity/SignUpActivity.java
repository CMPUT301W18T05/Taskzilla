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

package com.cmput301w18t05.taskzilla.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmput301w18t05.taskzilla.EmailAddress;
import com.cmput301w18t05.taskzilla.PhoneNumber;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;
import com.cmput301w18t05.taskzilla.request.command.GetUserByUsernameRequest;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main screen that deals with creating a user.
 *
 * @author Wyatt Praharenka
 * @version 1
 */

public class SignUpActivity extends AppCompatActivity {
    public TextView username;
    public TextView name;
    public TextView email;
    public TextView phone;
    public Button signUp;

    public User newUser;

    /**
     * Activity uses the activity_sign_up.xml layout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /* get views */
        username = findViewById(R.id.usernameField);
        name = findViewById(R.id.nameField);
        email = findViewById(R.id.emailField);
        phone = findViewById(R.id.phoneField);
        signUp = findViewById(R.id.SignUpButton);

        /**
         * Check for valid inputs then create a new user
         */
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInformation()) {
                    convertToUserObject();
                    System.out.println(addUserToDB());
                    finish();
                }
            }
        });
    }

    /**
     * Returns false if any of the information the user entered is invalid.
     */

    public boolean validateInformation() {
        if(TextUtils.isEmpty(username.getText())) {
            showError("Username required!");
            return false;
        } else if(username.getText().length() > 30) {
            showError("Username too long!");
            return false;
        } else {
            String usernameTemp = username.getText().toString();
            Pattern usernameConstraint = Pattern.compile("[^a-zA-Z0-9_]");
            boolean hasChar = usernameConstraint.matcher(usernameTemp).find();
            if(hasChar == true) {
                showError("Username contains illegal character!");
                return false;
            }
        }

        // Taken from https://stackoverflow.com/questions/18463848/how-to-tell-if-a-random-string-is-an-email-address-or-something-else
        // 2018/03/18

        if(TextUtils.isEmpty(email.getText())) {
            showError("Email required!");
            return false;
        } else if(email.getText().length() > 30) {
            showError("Email too long!");
            return false;
        } else {
            String emailTemp = email.getText().toString();

            Pattern p = Pattern.compile("[a-zA-z0-9._%+-]{1,}+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]{1,}");
            Matcher m = p.matcher(emailTemp);

            boolean matchFound = m.matches();
            if(matchFound) {
                // valid email do nothing
            }
            else {
                showError("Email invalid!");
                return false;
            }
        }
        if(TextUtils.isEmpty(phone.getText())) {
            showError("Phone number required!");
            return false;
        } else if(phone.getText().length() != 10) {
            showError("Phone number not of length 10!");
            return false;
        } else {
            String phoneTemp  = phone.getText().toString();
            Pattern phoneConstraint = Pattern.compile("[^0-9]");
            boolean hasChar = phoneConstraint.matcher(phoneTemp).find();
            if(hasChar == true) {
                showError("Phone contains illegal character!");
                return false;
            }
        }
        if(TextUtils.isEmpty(name.getText())) {
            showError("Name required!");
            return false;
        } else if(name.getText().length() > 30) {
            showError("Name too long!");
            return false;
        } else {
            String nameTemp = name.getText().toString();
            Pattern nameConstraint = Pattern.compile("[^a-zA-Z ]");
            boolean hasChar = nameConstraint.matcher(nameTemp).find();
            if(hasChar == true) {
                showError("Name contains illegal character!");
                return false;
            }
        }

        return true; // todo
    }

    /**
     * Takes info that was already validated and converts it to the proper type.
     * It is then used as parameters when creating a new user.
     *
     * @see User
     */

    public void convertToUserObject() {
        String name = this.name.getText().toString();
        String username = this.username.getText().toString();
        String email = this.email.getText().toString();
        String phone = this.phone.getText().toString();


        newUser = new User();
        newUser.setName(name);
        newUser.setEmail(new EmailAddress(email));
        newUser.setUsername(username);
        newUser.setPhone(new PhoneNumber(phone));
    }

    /**
     * This method takes the user object created and adds it to the elastic search
     * database.
     * <p>
     * This method returns false if the username the user is trying to use is already in the databse.
     *
     * @return  boolean value to determine if user was successfully added or not
     * @see     AddUserRequest
     * @see     RequestManager
     */

    public boolean addUserToDB() {
        RequestManager requestManager = RequestManager.getInstance();

        AddUserRequest addUserRequest = new AddUserRequest(newUser);

        // check if user exists
        GetUserByUsernameRequest getUserByUsernameRequest = new GetUserByUsernameRequest(newUser.getUsername());
        requestManager.invokeRequest(getUserByUsernameRequest);

        if (getUserByUsernameRequest.getResult() == null) {
            requestManager.invokeRequest(addUserRequest);
            return addUserRequest.getResult();
        }
        else {
            showError("This username is already is use. Please try another.");
            return false;
        }
    }

    /**
     * This method prints the error at the bottom of the activity.
     *
     * @param err   string representation of the error that occured
     */

    public void showError(String err) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.SignUpPage),err,Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
