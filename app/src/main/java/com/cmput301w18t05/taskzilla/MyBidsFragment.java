package com.cmput301w18t05.taskzilla;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyBidsFragment extends Fragment {

    private ArrayList<Task> taskList;
    private ListView taskListView;
    private ArrayAdapter<Task> adapter;

    public MyBidsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_bids, container, false);

        //Set up listview and adapater
        taskList = new ArrayList<Task>();
        taskListView = (ListView)v.findViewById(R.id.MyBidsListView);
        adapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(adapter);

        //Dummy Tasks for testing. Remove these and get the tasks from elastic search
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                viewTask();
            }
        });

        return v;
    }

    /**
     * Swtich activity to ViewTaskActivity when a task is clicked
     */
    public void viewTask(){
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        startActivity(intent);
    }
}
