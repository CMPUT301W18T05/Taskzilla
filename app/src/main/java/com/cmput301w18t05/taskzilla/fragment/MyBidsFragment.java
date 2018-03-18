package com.cmput301w18t05.taskzilla.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.controller.GetBidByUserIdController;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.RemoveBidRequest;

import java.util.ArrayList;



public class MyBidsFragment extends Fragment {

    private ArrayList<Bid> bidList;
    private ListView taskListView;
    private ArrayAdapter<Bid> adapter;
    private GetBidByUserIdController bidController;

    public MyBidsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_bids, container, false);
        taskListView = view.findViewById(R.id.MyBidsListView);
        return view;
    }

    /**
     * @param view states the current view
     * @param savedInstanceState idk what this does
     * @author myapplestory
     *
     * initialize variables as well as set up adapter and onlongclick
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bidList = new ArrayList<>();
        bidController = new GetBidByUserIdController(getContext(), currentUser.getInstance());
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, bidList);
        taskListView.setAdapter(adapter);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                viewTask(bidList.get(position));
            }
        });

        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Delete");
                alert.setMessage("Are you sure you want to delete this bid?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RemoveBidRequest removeRequest = new RemoveBidRequest(bidList.get(position));
                        RequestManager.getInstance().invokeRequest(removeRequest);
                        bidList.remove(position);
                        adapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });
    }

    /**
     * whenever the user switches to this fragment
     * fetch all current user's bids and display them on the page
     * @author myapplestory
     */
    @Override
    public void onStart() {
        super.onStart();
        getUpdatedBids();
    }

    public void getUpdatedBids(){
        bidList.clear();
        bidController.doTaskRequest();
        bidList.addAll(bidController.getResultBidList());
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "dwadwad", Toast.LENGTH_SHORT).show();
    }


    /**
     * Switch activity to ViewTaskActivity when a task is clicked
     * @param bid the bid that was tapped on
     */
    public void viewTask(Bid bid) {
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        intent.putExtra("TaskId", bid.getTaskId());
        startActivity(intent);
    }
}
