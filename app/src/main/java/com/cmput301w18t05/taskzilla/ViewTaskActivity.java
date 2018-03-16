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


import android.database.DataSetObserver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;



public class ViewTaskActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        ImageButton EditButton = findViewById(R.id.EditButton);
        ImageButton DeleteButton = findViewById(R.id.DeleteButton);
        ImageButton ProviderPicture = findViewById(R.id.ProviderPicture);
        ImageButton RequesterPicture = findViewById(R.id.RequesterPicture);
        TextView ProviderName =  findViewById(R.id.ProviderName);
        TextView DescriptionView = findViewById(R.id.Description);
        TextView RequesterName = findViewById(R.id.RequesterName);
        int currentUserId = 5;                              //Dummy for Testing
        int taskUserId = 5;                                 //Dummy for Testing
        final String taskID = "5";
        String status = "assigned";                         //Dummy
        String taskStatus = "requested";                    //DUMMY
        final String Description = "test\ntest\ntest\ntest\ntest\ntest\ntest\ntest\ntest\ntest\ntest\ntest\ntest\n";
        final String TaskRequester = "user1";
        final String TaskProvider = "user2";

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
                intent.putExtra("user", TaskProvider);
                startActivity(intent);
            }

        });

        //Requester Profile
        RequesterPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                intent.putExtra("user", TaskRequester);
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
                startActivityForResult(intent, 1);
            }
        });

        //Implement delete button
        DeleteButton.setOnClickListener(new View.OnClickListener() {
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


        Button buttonAtBottom = findViewById(R.id.button_at_bottom);

        ExpandableListView bidsListView = findViewById(R.id.bids_list_listview);

        // if this task's requester is the current logged in user
        //buttonAtBottom.setText("ACCEPT A BID");
        //otherwise
        buttonAtBottom.setText("PLACE BID");


        ArrayList<Bid> bidsList = new ArrayList<>();

        //ArrayAdapter<Bid> adapter = new ArrayAdapter<>(ViewTaskActivity.this, android.R.layout.simple_list_item_1, bidsList);
        //bidsListView.setAdapter(adapter);


        bidsList.add(new Bid(new User(), 1.0f));
        bidsList.add(new Bid(new User(), 1.0f));
        bidsList.add(new Bid(new User(), 1.0f));
        bidsList.add(new Bid(new User(), 1.0f));
        bidsList.add(new Bid(new User(), 1.0f));

    }


    /**
     * placeBid
     * upon pressing place button on task page
     * depending on if the user viewing the task is the owner of task or someone else
     * if they are the owner
     * show a dialog or something for the user to select a bid to accept
     * otherwise
     * prompts user to enter in a bid amount
     * if valid input, will add bid to task
     * <p>
     * notes
     * can probably add more stuff to dialog
     *
     * @author myapplestory
     */
    public void thePinkButton(android.view.View view) {

        // if this task's requester is the current logged in user
        // show a dialog or fragment where the requester can select which bid to accept
        // otherwise

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ViewTaskActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_place_bid, null);

        final EditText incomingBidText = mView.findViewById(R.id.place_bid_edittext);
        Button submitBidButton = mView.findViewById(R.id.submit_bid_button);

        submitBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float incomingBidFloat;
                try {
                    incomingBidFloat = Float.parseFloat(incomingBidText.getText().toString());
                    incomingBidFloat = (float) (Math.round(incomingBidFloat * 100.0) / 100.0);
                } catch (Exception exception) {
                    Toast.makeText(ViewTaskActivity.this,
                            "Please enter in a valid bid amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                // do stuff here to actually add bid


                Toast.makeText(ViewTaskActivity.this,
                        incomingBidFloat.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (1): {
                //code to add to ESC
                if (resultCode == RESULT_OK) {
                    String TaskName = data.getStringExtra("Task Name");
                    String Description = data.getStringExtra("Description");
                    TextView DescriptionView = (TextView) findViewById(R.id.Description);
                    TextView TaskNameView = (TextView) findViewById(R.id.TaskName);
                    TaskNameView.setText(TaskName);
                    if (Description.length() > 0) {
                        DescriptionView.setText(Description);
                    } else {
                        DescriptionView.setText("No Description");
                    }
                }
            }
        }
    }

}

