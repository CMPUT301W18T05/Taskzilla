package com.cmput301w18t05.taskzilla;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ViewTaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        ImageButton EditButton = (ImageButton) findViewById(R.id.EditButton);
        ImageButton DeleteButton = (ImageButton) findViewById(R.id.DeleteButton);
        ImageButton ProviderPicture = (ImageButton) findViewById(R.id.ProviderPicture);
        ImageButton RequesterPicture = (ImageButton) findViewById(R.id.RequesterPicture);
        TextView ProviderName = (TextView) findViewById(R.id.ProviderName);
        TextView DescriptionView = (TextView) findViewById(R.id.Description);
        TextView RequesterName = (TextView) findViewById(R.id.RequesterName);
        int currentUserId = 5;                              //Dummy for Testing
        int taskUserId = 5;                                 //Dummy for Testing
        final String taskID = "5";
        String status = "assigned";                         //Dummy
        String taskStatus = "requested";                    //DUMMY
        final String Description = "test test test test test test test test test test test";
        String TaskRequester = "user1";
        String TaskProvider = "user2";

        RequesterName.setText(TaskRequester);
        DescriptionView.setText(Description);

        if (currentUserId == taskUserId && taskStatus == "requested") {
            EditButton.setVisibility(View.VISIBLE);
        } else {
            EditButton.setVisibility(View.INVISIBLE);
        }
        if (currentUserId == taskUserId) {
            DeleteButton.setVisibility(View.VISIBLE);
        } else {
            DeleteButton.setVisibility(View.INVISIBLE);
        }
        if (status == "assigned") {
            ProviderPicture.setVisibility(View.VISIBLE);
            ProviderName.setVisibility(View.VISIBLE);
            ProviderName.setText(TaskProvider);
        }

        //Provider Profile
        ProviderPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                startActivity(intent);
            }

        });

        //Requester Profile
        RequesterPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        //Edit Task Button
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditTaskActivity.class);
                intent.putExtra("taskID", taskID);
                intent.putExtra("Description", Description);
                startActivity(intent);
            }
        });

        //Implement delete button
        DeleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewTaskActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete?");
                //DELETE CODE
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                //DELETE CANCEL CODE
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
            }

        });
    }

}
