package com.cmput301w18t05.taskzilla;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetUserRequest;


import java.util.ArrayList;

import javax.crypto.SecretKeyFactory;

/**
 * Created by myapplestory on 3/18/2018.
 *
 * this class is responsible for the expandable bids list in view task activity
 *
 */

public class ExpandableBidListAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> groupName;
    private Context context;
    private ArrayList<Bid> bidList;

    public ExpandableBidListAdapter(Context context, ArrayList<Bid> bidList) {
        this.context = context;
        this.groupName = new ArrayList<>();
        this.groupName.add("Existing Bids");
        this.bidList = new ArrayList<>();
        this.bidList.addAll(bidList);
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.bidList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return bidList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return bidList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /*** @param groupPosition which group was clicked
     * @param isExpanded if it is currently expanded
     * @param view the listview parent in context
     * @param parent the parent group this group belongs to (none)
     * @return the textview which is to be displayed
     * @author myapplestory
     * just display a textview
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(this.groupName.get(groupPosition));
        textView.setTextColor(Color.rgb(255,64,129));
        textView.setPadding(96, 0,0,0);
        textView.setTextSize(24);
        return textView;
    }

    /**
     * @param groupPosition which group was clicked
     * @param childPosition which child was clicked
     * @param isLastChild if it the last element in the list
     * @param view the listview parent in context
     * @param parent the parent group this group belongs to
     * @return the textview to be displayed
     * @author myapplestory
     *
     * gets bids on this task as well as the bid amount and big owner name
     * and displays it
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        Bid currentBid = this.bidList.get(childPosition);
        GetUserRequest getUserRequest = new GetUserRequest(currentBid.getUserId());
        RequestManager.getInstance().invokeRequest(getUserRequest);
        User BidOwner = getUserRequest.getResult();

        TextView textView = new TextView(context);
        String output = "$" + Float.toString(currentBid.getBidAmount())
                + "    By user: " + BidOwner.getName();

        textView.setText(output);
        textView.setTextColor(0xffff8800);
        textView.setTextSize(18);
        textView.setPadding(144, 0,0,0);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Secret developer settings enabled", Toast.LENGTH_SHORT).show();
            }
        });
        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
