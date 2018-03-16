package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.DeletionRequest;

/**
 * Created by Andy on 3/16/2018.
 */

public class RemoveTaskRequest extends DeletionRequest {
    private String taskId;

    public RemoveTaskRequest(String id) {
        this.taskId = id;
    }

    public void execute(){
        ElasticSearchController.RemoveTask deleteRequest = new ElasticSearchController.RemoveTask();
        deleteRequest.execute(this.taskId);
    }

    public void executeOffline(){}
}
