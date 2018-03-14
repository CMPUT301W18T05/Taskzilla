package com.cmput301w18t05.taskzilla;

import java.util.ArrayList;

/**
 * Created by Andy on 3/12/2018.
 */

public class SearchController {
    private ArrayList<String> searchKeywords;
    private ArrayList<Task> searchResults;

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

    public void setResults(ArrayList<Task> updatedResults){
        this.searchResults = updatedResults;
    }

    public void clearKeywords() {
            this.searchKeywords.clear();
    }

}
