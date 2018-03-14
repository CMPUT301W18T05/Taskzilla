package com.cmput301w18t05.taskzilla.request;

import android.util.Log;

import com.cmput301w18t05.taskzilla.ElasticSearchController;
import com.cmput301w18t05.taskzilla.SearchController;
import com.cmput301w18t05.taskzilla.Task;

import java.util.ArrayList;

import io.searchbox.core.Search;

/**
 * Created by Andy on 3/12/2018.
 */

public class SearchRequest extends Request {

    private String searchKeywords;
    private SearchController searchController;

    public SearchRequest(SearchController searchController) {
        this.searchController = searchController;
        this.searchKeywords = searchController.getKeywords().get(0);
    }

    public void execute() {
        ElasticSearchController.SearchForTasks search = new ElasticSearchController.SearchForTasks();
        search.execute(searchKeywords);

        try {
            searchController.setResults(search.get());
        }
        catch(Exception e) {
            Log.i("Error", "Failed to get tasks from the async object");
        }

    }

}
