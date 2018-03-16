package com.cmput301w18t05.taskzilla;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

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
        EditText taskName = (EditText) view.findViewById(R.id.TaskName);
        EditText taskDescription = (EditText) view.findViewById(R.id.Description);

        if(TextUtils.getTrimmedLength(taskName.getText()) <= 25
                && TextUtils.getTrimmedLength(taskName.getText()) >0
                && TextUtils.getTrimmedLength(taskDescription.getText()) <= 25
                && TextUtils.getTrimmedLength(taskDescription.getText()) >0){
            Task task = new Task(name, user, description);

            AddTaskRequest request = new AddTaskRequest(task);
            RequestManager.getInstance().invokeRequest(ctx, request);

            view.finish();
        }else{

            if (TextUtils.getTrimmedLength(taskName.getText()) > 25) {
                taskName.setError("Name can not exceed 25 characters");
            }

            if (TextUtils.getTrimmedLength(taskName.getText()) == 0) {
                taskName.setError("Name can not empty");
            }

            if (TextUtils.getTrimmedLength(taskDescription.getText()) > 280) {
                taskDescription.setError("Description can not exceed 280 characters");
            }
            
            if (TextUtils.getTrimmedLength(taskDescription.getText()) == 0) {
                taskDescription.setError("Description can not be empty");
            }

        }
    }

    public void cancelTask(){
        view.finish();
    }
    //Add picture and location in part 5
}
