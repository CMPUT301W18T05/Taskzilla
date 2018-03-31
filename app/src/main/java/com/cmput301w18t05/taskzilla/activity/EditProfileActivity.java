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

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.cmput301w18t05.taskzilla.EmailAddress;
import com.cmput301w18t05.taskzilla.PhoneNumber;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity {

    private EditText NameText;
    private EditText EmailText;
    private EditText PhoneText;

    private User user = new User(); //dummy
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        NameText = (EditText) findViewById(R.id.NameField);
        EmailText = (EditText) findViewById(R.id.EmailField);
        PhoneText = (EditText) findViewById(R.id.Phone);
        String userName = getIntent().getStringExtra("Name");
        String userEmail = getIntent().getStringExtra("Email");
        String userPhone = getIntent().getStringExtra("Phone");
        user.setName(userName);
        user.setEmail(new EmailAddress(userEmail));
        user.setPhone(new PhoneNumber(userPhone));
        NameText.setText(user.getName());
        EmailText.setText(user.getEmail().toString());
        PhoneText.setText(user.getPhone().toString());
    }

    public void ProfileCancelButton(View view){
        finish();
    }

    public void ProfileSaveButton(View view){
        /** Add Save Code when ESC and controllers get figured out **/

        /**                                         **/
        if(validateInformation()){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Name", user.getName());
            returnIntent.putExtra("Email", user.getEmail().toString());
            returnIntent.putExtra("Phone",user.getPhone().toString());
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    public boolean validateInformation() {

        // Taken from https://stackoverflow.com/questions/18463848/how-to-tell-if-a-random-string-is-an-email-address-or-something-else
        // 2018/03/18


        if(TextUtils.isEmpty(NameText.getText())) {
            NameText.requestFocus();
            NameText.setError("Name required!");
            return false;
        } else if(NameText.getText().length() > 30) {
            NameText.requestFocus();
            NameText.setError("Name too long!");
            return false;
        } else {
            String nameTemp = NameText.getText().toString();
            Pattern nameConstraint = Pattern.compile("[^a-zA-Z ]");
            boolean hasChar = nameConstraint.matcher(nameTemp).find();
            if(hasChar == true) {
                NameText.requestFocus();
                NameText.setError("Name contains illegal character!");
                return false;
            }
        }

        if(TextUtils.isEmpty(EmailText.getText())) {
            EmailText.requestFocus();
            EmailText.setError("Email required!");
            return false;
        } else if(EmailText.getText().length() > 30) {
            EmailText.requestFocus();
            EmailText.setError("Email too long!");
            return false;
        } else {
            String emailTemp = EmailText.getText().toString();

            Pattern p = Pattern.compile("[a-zA-z0-9._%+-]{1,}+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]{1,}");
            Matcher m = p.matcher(emailTemp);

            boolean matchFound = m.matches();
            if(matchFound) {
                // valid email do nothing
            }
            else {
                EmailText.requestFocus();
                EmailText.setError("Email invalid!");
                return false;
            }
        }
        if(TextUtils.isEmpty(PhoneText.getText())) {
            PhoneText.requestFocus();
            PhoneText.setError("Phone number required!");
            return false;
        } else if(PhoneText.getText().length() != 10) {
            PhoneText.requestFocus();
            PhoneText.setError("Phone number not of length 10!");
            return false;
        } else {
            String phoneTemp  = PhoneText.getText().toString();
            Pattern phoneConstraint = Pattern.compile("[^0-9]");
            boolean hasChar = phoneConstraint.matcher(phoneTemp).find();
            if(hasChar == true) {
                PhoneText.requestFocus();
                PhoneText.setError("Phone contains illegal character!");
                return false;
            }
        }


        return true; // todo
    }
}