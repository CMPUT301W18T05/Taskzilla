package com.cmput301w18t05.taskzilla;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

public class ViewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);

        Button buttonAtBottom = findViewById(R.id.button_at_bottom);
        TextView detailsText = findViewById(R.id.task_details_textview);
        ExpandableListView bidsListView = findViewById(R.id.bids_list_listview);

        // if this task's requester is the current logged in user
        //buttonAtBottom.setText("ACCEPT A BID");
        //otherwise
        buttonAtBottom.setText("PLACE BID");

        detailsText.setText("s\ns\ns\ns\ns\ns\ns\ns\ns\ns\ns\ns\ns\ns\n");

        ArrayList<Bid> bidsList = new ArrayList<>();

        //ArrayAdapter<Bid> adapter = new ArrayAdapter<>(ViewTaskActivity.this, android.R.layout.simple_list_item_1, bidsList);
        //bidsListView.setAdapter(adapter);



        bidsList.add(new Bid(new User(), 1.0f));
        bidsList.add(new Bid(new User(), 1.0f));
        bidsList.add(new Bid(new User(), 1.0f));
        bidsList.add(new Bid(new User(), 1.0f));
        bidsList.add(new Bid(new User(), 1.0f));


    }

    public void viewProfile(android.view.View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
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
     *
     * notes
     * can probably add more stuff to dialog
     *
     * @author myapplestory
     */
    public void thePinkButton(android.view.View view){

        // if this task's requester is the current logged in user
        // show a dialog or fragment where the requester can select which bid to accept
        // otherwise

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ViewTaskActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_place_bid, null);

        final EditText incomingBidText = mView.findViewById(R.id.place_bid_edittext);
        Button submitBidButton =  mView.findViewById(R.id.submit_bid_button);

        submitBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float incomingBidFloat;
                try {
                    incomingBidFloat = Float.parseFloat(incomingBidText.getText().toString());
                    incomingBidFloat = (float)(Math.round(incomingBidFloat * 100.0) / 100.0);
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
        AlertDialog dialog  = mBuilder.create();
        dialog.show();

    }


}
