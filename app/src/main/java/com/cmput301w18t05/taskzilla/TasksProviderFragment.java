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

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByProviderUsernameRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByRequesterUsernameRequest;
import com.cmput301w18t05.taskzilla.request.command.SearchTaskRequest;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksProviderFragment extends Fragment {

    // List of Tasks
    private ArrayList<Task> taskList;
    private ListView taskListView;
    private ArrayAdapter<Task> adapter;

    private RequestManager requestManager;
    private GetTasksByProviderUsernameRequest requestTasks;
    private SearchTaskRequest newRequest;
    private currentUser cUser = currentUser.getInstance();


    public TasksProviderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Set up listview and adapter
        View v = inflater.inflate(R.layout.fragment_tasks_provider, container, false);
        taskList = new ArrayList<Task>();
        taskListView = (ListView)v.findViewById(R.id.ProviderTasksListView);
        adapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(adapter);


        requestTasks = new GetTasksByProviderUsernameRequest(cUser.getUsername());
        requestManager.getInstance().invokeRequest(getContext(), requestTasks);

        for (Task t : requestTasks.getResult()) {
            taskList.add(t);
        }

        adapter.notifyDataSetChanged();

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                viewTask(taskList.get(position).getId());
            }
        });

        return v;
    }


    public void viewTask(String id){
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        intent.putExtra("TaskId",id);
        startActivity(intent);
    }

}
