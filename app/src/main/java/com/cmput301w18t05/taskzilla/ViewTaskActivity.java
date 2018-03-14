package com.cmput301w18t05.taskzilla;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
    }

    public void viewProfile(android.view.View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    /**
     * placeBid
     * upon pressing place button on task page
     * prompts user to enter in a bid amount
     * if valid input, will add bid to task
     *
     * notes
     * can probably add more stuff to dialog
     *
     * @author myapplestory
     */
    public void placeBid(android.view.View view){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ViewTaskActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_place_bid, null);

        final EditText incomingBidText = mView.findViewById(R.id.place_bid_edittext);
        Button submitBidButton =  mView.findViewById(R.id.submit_bid_button);

        submitBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double incomingBidDouble;
                try {
                    incomingBidDouble = Double.parseDouble(incomingBidText.getText().toString());
                    incomingBidDouble = Math.round(incomingBidDouble * 100.0) / 100.0;
                } catch (Exception exception) {
                    Toast.makeText(ViewTaskActivity.this,
                            "Please enter in a valid bid amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                // do stuff here to actually add bid
                Toast.makeText(ViewTaskActivity.this,
                        incomingBidDouble.toString(), Toast.LENGTH_SHORT).show();
            } 
        });
        mBuilder.setView(mView);
        AlertDialog dialog  = mBuilder.create();
        dialog.show();

    }


}
