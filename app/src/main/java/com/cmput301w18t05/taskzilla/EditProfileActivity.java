package com.cmput301w18t05.taskzilla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditProfileActivity extends AppCompatActivity {


    private User user = new User(); //dummy
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        EditText NameText = (EditText) findViewById(R.id.Name);
        EditText EmailText = (EditText) findViewById(R.id.Email);
        EditText PhoneText = (EditText) findViewById(R.id.Phone);
        user.setName("Ken Wong"); //dummy
        user.setEmail(new EmailAddress("kennyw@ualberta.ca")); //dummy
        user.setPhone(new PhoneNumber(7807553455L)); //dummy
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
        EditText NameText = (EditText) findViewById(R.id.Name);
        EditText EmailText = (EditText) findViewById(R.id.Email);
        EditText PhoneText = (EditText) findViewById(R.id.Phone);
        String email = EmailText.getText().toString();
        int Errors = 0;
        try{
          user.setEmail(new EmailAddress(email));
        } catch (IllegalArgumentException e){
          EmailText.requestFocus();
          EmailText.setError("Invalid Email");
          Errors = 1;
        }
        if(Errors != 1) {
            finish();
        }
    }
}
