package com.cmput301w18t05.taskzilla.fragment;


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

import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.activity.NewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByRequesterUsernameRequest;
import com.cmput301w18t05.taskzilla.request.command.SearchTaskRequest;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksRequesterFragment extends Fragment {


    public TasksRequesterFragment() {
        // Required empty public constructor
    }

    private User cUser = currentUser.getInstance();
    private ArrayList<Task> taskList;
    private ListView taskListView;
    private ArrayAdapter<Task> adapter;
    private Task currentTask;

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
        taskList = new ArrayList<>();
        taskListView = v.findViewById(R.id.RequesterTasksListView);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(adapter);

        requestTasks = new GetTasksByRequesterUsernameRequest(cUser.getUsername());
        requestManager.getInstance().invokeRequest(getContext(), requestTasks);

        for (Task t : requestTasks.getResult()) {
            taskList.add(t);
        }

        adapter.notifyDataSetChanged();

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                currentTask = taskList.get(position);
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
        startActivityForResult(intent,1);
    }

    public void newTask() {
        Intent intent = new Intent(getActivity(), NewTaskActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        if(reqCode == 1) {
            if(resultCode == RESULT_OK) {
                Boolean result = data.getBooleanExtra("result", false);
                if(result)
                    taskList.remove(currentTask);
            }
        }

        if(reqCode == 2)
            if(resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");

                Log.i("Fuck this shit", "goksodks");

                GetTaskRequest request = new GetTaskRequest(result);
                RequestManager.getInstance().invokeRequest(getContext(), request);
                taskList.add(request.getResult());
            }
    }
}
