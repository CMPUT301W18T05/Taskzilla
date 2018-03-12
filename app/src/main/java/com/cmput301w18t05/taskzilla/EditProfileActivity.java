package com.cmput301w18t05.taskzilla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        User user = new User();
        user.setName("Ken Wong");
        user.setEmail(new EmailAddress("kennyw@ualberta.ca"));
        user.setPhone(new PhoneNumber(7807553455L));
        EditText Name = (EditText) findViewById(R.id.Name);
        EditText Email = (EditText) findViewById(R.id.Email);
        EditText Phone = (EditText) findViewById(R.id.Phone);
        Name.setText(user.getName());
        Email.setText(user.getEmail().toString());
        Phone.setText(user.getPhone().toString());


    }
}
