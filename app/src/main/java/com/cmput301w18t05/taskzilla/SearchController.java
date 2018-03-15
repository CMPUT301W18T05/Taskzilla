package com.cmput301w18t05.taskzilla;

import android.content.Context;
import android.util.Log;

import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.SearchTaskRequest;

import java.util.ArrayList;

/**
 * Created by Andy on 3/12/2018.
 */

public class SearchController {
    private ArrayList<String> searchKeywords;
    private ArrayList<Task> searchResults;
    private SearchTaskRequest newRequest;

    public SearchController() {
        this.searchKeywords = new ArrayList<String>();
        this.searchResults = new ArrayList<Task>();
    }

    public void addKeywords(String word) {
        this.searchKeywords.add(word);
    }

    public ArrayList<String> getKeywords() {
        return this.searchKeywords;
    }

    public ArrayList<Task> getResults() {
        return this.searchResults;
    }

    public void clearKeywords() {
        this.searchKeywords.clear();
    }

    public void searchRequest(Context ctx, String sentence) {
        newRequest = new SearchTaskRequest(sentence);
        RequestManager.getInstance().invokeRequest(ctx, newRequest);

        this.searchResults = newRequest.getTasks();
    }

}
