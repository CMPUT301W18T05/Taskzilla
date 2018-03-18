package com.cmput301w18t05.taskzilla.fragment;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.controller.GetBidByUserIdController;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.RemoveBidRequest;

import java.util.ArrayList;


public class MyBidsFragment extends Fragment {

    private ArrayList<Bid> bidList;
    private ListView taskListView;
    private ArrayAdapter<Bid> adapter;
    private GetBidByUserIdController bidController;

    public MyBidsFragment() {}

    /**
     * Inflate the layout for this fragment
     * @param inflater idk what this does
     * @param container same
     * @param savedInstanceState too
     * @return view
     * @author myapplestory
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bids, container, false);
        taskListView = view.findViewById(R.id.MyBidsListView);
        return view;
    }

    /**
     * initialize variables as well as set up adapter and onlongclick
     * @param view states the current view
     * @param savedInstanceState idk what this does
     * @author myapplestory
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bidList = new ArrayList<>();
        bidController = new GetBidByUserIdController(getContext(), currentUser.getInstance());
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, bidList);
        taskListView.setAdapter(adapter);

        // goes to the respective task when a bid is tapped on
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            viewTask(bidList.get(position));
            }
        });

        // prompts to delete bid when held
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            alert.setTitle("Delete");
            alert.setMessage("Are you sure you want to delete this bid?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // remove bid
                    RemoveBidRequest removeRequest = new RemoveBidRequest(bidList.get(position));
                    RequestManager.getInstance().invokeRequest(removeRequest);
                    bidList.remove(position);
                    // change status of task
                    //GetTaskRequest getTaskRequest = new GetTaskRequest(bidList.get(position).getTaskId());
                    //RequestManager.getInstance().invokeRequest(getTaskRequest);
                    //Task temptask = getTaskRequest.getResult();
                    //temptask.setStatus("requested");

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

    /**
     * updates the listView with the updates bids
     * @author myapplestory
     */
    public void getUpdatedBids(){
        bidList.clear();
        bidController.doTaskRequest();
        bidList.addAll(bidController.getResultBidList());
        adapter.notifyDataSetChanged();
    }

    /**
     * Switch activity to ViewTaskActivity when a task is clicked
     * @param bid the bid that was tapped on
     * @author myapplestory
     */
    public void viewTask(Bid bid) {
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        intent.putExtra("TaskId", bid.getTaskId());
        startActivity(intent);
    }
}
