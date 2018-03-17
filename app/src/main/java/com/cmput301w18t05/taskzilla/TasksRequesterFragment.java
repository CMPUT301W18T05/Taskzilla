package com.cmput301w18t05.taskzilla;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByRequesterUsernameRequest;
import com.cmput301w18t05.taskzilla.request.command.SearchTaskRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksRequesterFragment extends Fragment {


    public TasksRequesterFragment() {
        // Required empty public constructor
    }

    private currentUser cUser = currentUser.getInstance();
    private ArrayList<Task> taskList;
    private ListView taskListView;
    private ArrayAdapter<Task> adapter;
    /*
    private ElasticSearchController.AddTask addTask = new ElasticSearchController.AddTask();
    private ElasticSearchController.SearchForTasks searchForTask = new ElasticSearchController.SearchForTasks();
    private ElasticSearchController.GetTask getTask = new ElasticSearchController.GetTask();
    */
    private RequestManager requestManager;
    private GetTasksByRequesterUsernameRequest requestTasks;
    private SearchTaskRequest newRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tasks_requester, container, false);
        FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTask();
            }
        });

        //Set up listview and adapter
        taskList = new ArrayList<Task>();
        taskListView = (ListView) v.findViewById(R.id.RequesterTasksListView);
        adapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(adapter);




        requestTasks = new GetTasksByRequesterUsernameRequest(cUser.getUsername());
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
    public void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();


    }

    public void viewTask(String id) {
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        intent.putExtra("TaskId",id);
        startActivity(intent);
    }

    public void newTask() {
        Intent intent = new Intent(getActivity(), NewTaskActivity.class);
        startActivity(intent);
    }

}
