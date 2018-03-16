package com.cmput301w18t05.taskzilla.request.command;

import android.util.Log;

import com.cmput301w18t05.taskzilla.ElasticSearchController;
import com.cmput301w18t05.taskzilla.SearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.SearchRequest;

import java.util.ArrayList;

/**
 * Created by Andy on 3/14/2018.
 */

public class SearchTaskRequest extends SearchRequest {
    private String searchKeywords;
    private ArrayList<Task> results;

    public SearchTaskRequest(String keywords) {
        this.searchKeywords = keywords;
    }

    @Override
    public void execute() {
        ElasticSearchController.SearchForTasks search = new ElasticSearchController.SearchForTasks();
        search.execute(searchKeywords);

        try {
            results = search.get();
        }
        catch(Exception e) {
            Log.i("Error", "Failed to get tasks from the async object");
        }
    }

    @Override
    public void executeOffline() {
    }

    public ArrayList<Task> getTasks() {
        return results;
    }
}
