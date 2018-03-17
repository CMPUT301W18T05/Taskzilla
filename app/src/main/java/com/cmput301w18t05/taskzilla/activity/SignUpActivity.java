package com.cmput301w18t05.taskzilla.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmput301w18t05.taskzilla.EmailAddress;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;
import com.cmput301w18t05.taskzilla.request.command.GetUserByUsernameRequest;

public class SignUpActivity extends AppCompatActivity {
    public TextView username;
    public TextView name;
    public TextView email;
    public TextView phone;
    public Button signUp;

    public User newUser;

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

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInformation()) {
                    convertToUserObject();
                    System.out.println(addUserToDB());
                }
                else {
                    showError();
                }
            }
        });
    }

    public boolean validateInformation() {
        return true; // todo
    }

    public void showError() {
        // todo
    }

    public void convertToUserObject() {
        String name = this.name.getText().toString();
        String username = this.username.getText().toString();
        String email = this.email.getText().toString();
        String phone = this.phone.getText().toString();


        newUser = new User();
        newUser.setName(name);
        newUser.setEmail(new EmailAddress(email));
        newUser.setUsername(username);
    }

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
        return false;
    }
}
