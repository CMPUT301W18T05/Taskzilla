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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.activity.ViewTaskActivity;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByProviderUsernameRequest;
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

    private GetTasksByProviderUsernameRequest requestTasks;
    private SearchTaskRequest newRequest;
    private User cUser = currentUser.getInstance();


    public TasksProviderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Set up listview and adapter
        View v = inflater.inflate(R.layout.fragment_tasks_provider, container, false);
        taskList = new ArrayList<>();
        taskListView = v.findViewById(R.id.ProviderTasksListView);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(adapter);

        requestTasks = new GetTasksByProviderUsernameRequest(currentUser.getInstance().getUsername());
        RequestManager.getInstance().invokeRequest(getContext(), requestTasks);

            taskList.addAll(requestTasks.getResult());

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
