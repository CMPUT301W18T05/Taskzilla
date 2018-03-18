package com.cmput301w18t05.taskzilla.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyBidsFragment extends Fragment {

    private ArrayList<Bid> bidList;
    private ListView taskListView;
    private ArrayAdapter<Bid> adapter;
    private GetBidByUserIdController bidController;

    public MyBidsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_bids, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // controller stuff
        bidList = new ArrayList<>();
        bidController = new GetBidByUserIdController(getContext(), currentUser.getInstance());
        bidController.doTaskRequest();
        bidList = bidController.getResultBidList();

        //Set up listview and adapater
        taskListView = view.findViewById(R.id.MyBidsListView);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, bidList);
        taskListView.setAdapter(adapter);

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                viewTask(bidList.get(position));
            }
        });
    }

    /**
     * Swtich activity to ViewTaskActivity when a task is clicked
     */
    public void viewTask(Bid bid){
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        intent.putExtra("TaskId",bid.getTaskId());
        startActivity(intent);
    }
}
