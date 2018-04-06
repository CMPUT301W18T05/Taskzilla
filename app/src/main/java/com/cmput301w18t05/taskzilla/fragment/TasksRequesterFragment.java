/*
 * Copyright 2018 (c) Andy Li, Colin Choi, James Sun, Jeremy Ng, Micheal Nguyen, Wyatt Praharenka
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.cmput301w18t05.taskzilla.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.activity.NewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.controller.ViewTaskController;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetTaskRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByProviderUsernameRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByRequesterUsernameRequest;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * Child fragment of TasksFragment
 * Tasks that the user is requesting appear here
 *
 * @author Colin, myapplestory
 * @version 1.0
 */
public class TasksRequesterFragment extends Fragment {
    private User cUser = currentUser.getInstance();
    private ArrayList<Task> taskList;
    private ListView taskListView;
    private ArrayAdapter<Task> adapter;
    private GetTasksByRequesterUsernameRequest requestTasks;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    private Spinner spinner;
    public TasksRequesterFragment() {
        // Required empty public constructor
    }

    /**
     * oncreate initialize task list and request and adapter for listview
     * as well as fetch the current user's tasks and putting it in listview
     * @param savedInstanceState the state of every fragment on the parent activity
     * @author myapplestory
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set up listView and adapter
        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, taskList);

        //gets all of current user's tasks
        requestTasks = new GetTasksByRequesterUsernameRequest(cUser.getUsername());
        RequestManager.getInstance().invokeRequest(getContext(), requestTasks);
        taskList.addAll(requestTasks.getResult());
    }

    /**
     * @param inflater the layout
     * @param container the container that houses the view
     * @param savedInstanceState the state of every fragment on the parent activity
     * @return returns the fragment view to display
     * @author myapplestory
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks_requester, container, false);
    }

    /**
     * @param view the fragment that is displayed
     * @param savedInstanceState the state of every fragment on the parent activity
     * @author myapplestory
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterS = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_options, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapterS);

        taskListView = view.findViewById(R.id.RequesterTasksListView);
        taskListView.setAdapter(adapter);
        mySwipeRefreshLayout = view.findViewById(R.id.swiperefresh);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTask();
            }
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                viewTask(taskList.get(position).getId());
            }
        });

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        String spinnerItem = spinner.getSelectedItem().toString();
                        if(spinnerItem.equalsIgnoreCase("All")){
                            updateRList();
                        }
                        if(spinnerItem.equalsIgnoreCase("Requested")){
                            updateRequested();
                        }
                        if(spinnerItem.equalsIgnoreCase("Bidded")){
                            updateBidded();
                        }
                        if(spinnerItem.equalsIgnoreCase("Assigned")){
                            updateAssigned();
                        }
                        if(spinnerItem.equalsIgnoreCase("Completed")){
                            updateCompleted();
                        }

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(mySwipeRefreshLayout.isRefreshing()) {
                                    mySwipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }, 1000);
                    }
                }
        );

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("All")) {
                    updateRList();
                }
                if(selectedItem.equals("Requested")) {
                    updateRequested();
                }
                if(selectedItem.equals("Bidded")) {
                    updateBidded();
                }
                if(selectedItem.equals("Assigned")) {
                    updateAssigned();
                }
                if(selectedItem.equals("Completed")) {
                    updateCompleted();
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        //Add a delay for elastic search to update
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String spinnerItem = spinner.getSelectedItem().toString();
                if(spinnerItem.equalsIgnoreCase("All")){
                    updateRList();
                }
                if(spinnerItem.equalsIgnoreCase("Requested")){
                    updateRequested();
                }
                if(spinnerItem.equalsIgnoreCase("Bidded")){
                    updateBidded();
                }
                if(spinnerItem.equalsIgnoreCase("Assigned")){
                    updateAssigned();
                }
                if(spinnerItem.equalsIgnoreCase("Completed")){
                    updateCompleted();
                }
            }
        }, 300);

        //Add a longer delay after in case they have bad internet
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String spinnerItem = spinner.getSelectedItem().toString();
                if(spinnerItem.equalsIgnoreCase("All")){
                    updateRList();
                }
                if(spinnerItem.equalsIgnoreCase("Requested")){
                    updateRequested();
                }
                if(spinnerItem.equalsIgnoreCase("Bidded")){
                    updateBidded();
                }
                if(spinnerItem.equalsIgnoreCase("Assigned")){
                    updateAssigned();
                }
                if(spinnerItem.equalsIgnoreCase("Completed")){
                    updateCompleted();
                }
            }
        }, 1500);
    }

    public void updateRList(){
        requestTasks = new GetTasksByRequesterUsernameRequest(cUser.getUsername());
        RequestManager.getInstance().invokeRequest(getContext(), requestTasks);
        taskList.clear();
        taskList.addAll(requestTasks.getResult());
        adapter.notifyDataSetChanged();
    }

    public void updateRequested(){
        requestTasks = new GetTasksByRequesterUsernameRequest(cUser.getUsername());
        RequestManager.getInstance().invokeRequest(getContext(), requestTasks);
        taskList.clear();
        for(Task t:requestTasks.getResult()){
            if(t.getStatus().equalsIgnoreCase("requested")) {
                taskList.add(t);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void updateBidded(){
        requestTasks = new GetTasksByRequesterUsernameRequest(cUser.getUsername());
        RequestManager.getInstance().invokeRequest(getContext(), requestTasks);
        taskList.clear();
        for(Task t:requestTasks.getResult()){
            if(t.getStatus().equalsIgnoreCase("bidded")) {
                taskList.add(t);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void updateAssigned(){
        requestTasks = new GetTasksByRequesterUsernameRequest(cUser.getUsername());
        RequestManager.getInstance().invokeRequest(getContext(), requestTasks);
        taskList.clear();
        for(Task t:requestTasks.getResult()){
            if(t.getStatus().equalsIgnoreCase("assigned")) {
                taskList.add(t);
            }
        }
        adapter.notifyDataSetChanged();
    }
    public void updateCompleted(){
        requestTasks = new GetTasksByRequesterUsernameRequest(cUser.getUsername());
        RequestManager.getInstance().invokeRequest(getContext(), requestTasks);
        taskList.clear();
        for(Task t:requestTasks.getResult()){
            if(t.getStatus().equalsIgnoreCase("completed")) {
                taskList.add(t);
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * upon pressing a task on the listview, switches to ViewTaskActivity
     * @param id id of the task to be view is passed in as a String
     * @author Colin
     */
    public void viewTask(String id) {
        /*
        try except statement, when task clicked. Attempts to get from elastic search in order to test
        if the task still exists or not.
         */
        try {
            GetTaskRequest request = new GetTaskRequest(id);
            RequestManager.getInstance().invokeRequest(getContext(), request);
            Task testTask = request.getResult();
            String testTaskId = testTask.getTaskRequester().getId();
            Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
            intent.putExtra("TaskId", id);
            startActivityForResult(intent, 1);
        }
        catch (Exception e){
            updateRList();
            Toast.makeText(getActivity(), "Task no longer exists", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Switches to NewTaskActitivy.
     * This is used to create new Tasks
     * @author Colin
     */
    public void newTask() {
        Intent intent = new Intent(getActivity(), NewTaskActivity.class);
        startActivityForResult(intent, 1);
    }
}
