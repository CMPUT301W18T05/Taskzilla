package com.cmput301w18t05.taskzilla;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksProviderFragment extends Fragment {

    private ArrayList<Task> taskList;
    private ListView taskListView;
    private ArrayAdapter<Task> adapter;

    public TasksProviderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tasks_provider, container, false);
        taskList = new ArrayList<Task>();
        taskListView = (ListView)v.findViewById(R.id.ProviderTasksListView);
        adapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(adapter);
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());

        return v;
    }

}
