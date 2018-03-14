package com.cmput301w18t05.taskzilla;

/**
 * Created by Colin on 2018-03-13.
 */

public class NewTaskController {

    private NewTaskActivity view;

    public NewTaskController(NewTaskActivity view) {
        this.view = view;
    }

    public void addTask(String name, User user, String description){
        //Check field lengths and give error

        Task task = new Task(name,user,description);
        //Add task to elastic search
        view.finish();
    }

    public void cancelTask(){
        view.finish();
    }
    //Add picture and location in part 5
}
