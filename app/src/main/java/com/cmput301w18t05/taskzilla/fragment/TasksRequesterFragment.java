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
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.activity.NewTaskActivity;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByRequesterUsernameRequest;

import java.util.ArrayList;


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

        //Set up listview and adapter
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

        taskListView = view.findViewById(R.id.RequesterTasksListView);
        taskListView.setAdapter(adapter);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newTask();
            }
        });

        FloatingActionButton floatingActionButton2 = view.findViewById(R.id.fab2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestManager.getInstance().invokeRequest(getContext(), requestTasks);
                taskList.clear();
                taskList.addAll(requestTasks.getResult());
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Task list refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                viewTask(taskList.get(position).getId());
            }
        });
    }

    /**
     * upon pressing a task on the listview, switches to ViewTaskActivity
     * @param id id of the task to be view is passed in as a String
     * @author Colin
     */
    public void viewTask(String id) {
        Intent intent = new Intent(getActivity(), ViewTaskActivity.class);
        intent.putExtra("TaskId",id);
        startActivity(intent);
    }

    /**
     * Switches to NewTaskActitivy.
     * This is used to create new Tasks
     * @author Colin
     */
    public void newTask() {
        Intent intent = new Intent(getActivity(), NewTaskActivity.class);
        startActivity(intent);
    }
}
