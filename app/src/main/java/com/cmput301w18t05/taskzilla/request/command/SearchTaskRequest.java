package com.cmput301w18t05.taskzilla.request.command;

import android.util.Log;

import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.request.SearchRequest;

import java.util.ArrayList;

/**
 * Created by Andy on 3/14/2018.
 */

public class SearchTaskRequest extends SearchRequest {
    private String searchKeywords;
    private ArrayList<Task> results;
    private ElasticSearchController.SearchForTasks search;
    private int from = 0;
    private int size = 10;

    public SearchTaskRequest(String keywords) {
        this.searchKeywords = keywords;
    }

    @Override
    public void execute() {
        search = new ElasticSearchController.SearchForTasks(from,size);
        search.execute(searchKeywords);

        try {
            results = search.get();
            from += size;
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
