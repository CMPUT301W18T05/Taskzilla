package com.cmput301w18t05.taskzilla;

import android.content.Context;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddTaskRequest;

/**
 * Created by Colin on 2018-03-13.
 */

public class NewTaskController {

    private NewTaskActivity view;
    private Context ctx;

    public NewTaskController(NewTaskActivity view, Context context) {
        this.view = view;
        this.ctx = context;
    }

    public void addTask(String name, User user, String description){
        //Check field lengths and give error

        Task task = new Task(name,user,description);
        //Add task to elastic search

        AddTaskRequest request = new AddTaskRequest(task);
        RequestManager.getInstance().invokeRequest(ctx, request);

        view.finish();
    }

    public void cancelTask(){
        view.finish();
    }
    //Add picture and location in part 5
}
