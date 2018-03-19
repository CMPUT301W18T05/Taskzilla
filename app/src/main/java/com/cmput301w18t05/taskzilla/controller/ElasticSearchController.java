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

package com.cmput301w18t05.taskzilla.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.cmput301w18t05.taskzilla.Bid;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.User;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy
 *
 * ElasticSearchController,
 *
 * Controller for packaging all the functions dealing with
 * elastic search.
 *
 * Most common use will be with RequestManager
 *
 * @author Jeremy Wyatt
 * TODO: my bids returns a list of tasks
 */
public class ElasticSearchController {

    private static final ElasticSearchController inst = new ElasticSearchController();

    private static JestDroidClient client;

    private ElasticSearchController() {
    }

    /**
     * Singleton class
     * @return instance of the elastic search controller
     */
    public static ElasticSearchController getInstance() {
        return inst;
    }

    public static class AddTask extends AsyncTask<Task, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Task... tasks) {
            verifySettings();
            for (Task task : tasks) {
                Index index = new Index.Builder(task).index("cmput301w18t05").type("task").build();
                try {
                    Log.i("Event", "Trying to add user "+task.toString());
                    DocumentResult result = client.execute(index);
                    Log.i("Event", "Jest returned with: "+result);
                    if (result.isSucceeded()) {
                        Log.i("Event","Successfully added the task object");
                        task.setId(result.getId());
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("Error", "Task not added");
                }
            }
            return false;
        }
    }

    public static class UpdateTask extends AsyncTask<Task, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Task... tasks) {
            verifySettings();
            DocumentResult result = null;
            for (Task task : tasks) {
                Update update = new Update.Builder(task).index("cmput301w18t05").type("task").build();
                try {
                    result = client.execute(update);
                } catch (Exception e) {
                    Log.i("Error", "Task not updated");
                }
            }
            return result != null && result.isSucceeded();
        }
    }

    public static class RemoveTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... taskIds) {
            verifySettings();
            DocumentResult result = null;
            for (String id: taskIds) {

                // get the bids
                try {
                    String query = "{ \"query\" : { \"match\" : { \"taskId\" : \"" + id + "\" } } }";
                    Log.i("Query",query);
                    Search search = new Search.Builder(query)
                                        .addIndex("cmput301w18t05")
                                        .addType("bid")
                                        .build();
                    SearchResult bidsResult = client.execute(search);

                    if (bidsResult.isSucceeded()) {
                        List<Bid> bids = bidsResult.getSourceAsObjectList(Bid.class);

                        for (Bid b : bids) {  // delete them
                            client.execute(
                                    new Delete.Builder(b.getId()).index("cmput301w18t05").type("bid").build()
                            );
                        }
                    }
                }
                catch (Exception e) {
                    Log.i("Error","could not delete bids when removing task");
                }

                try {
                    result = client.execute(new Delete.Builder(id).index("cmput301w18t05").type("task").build());
                    Log.i("Success", "Task deleted");
                } catch (Exception e) {
                    Log.i("Error", "Task not deleted");
                }
            }

            return result != null && result.isSucceeded();
        }
    }

    public static class GetTask extends AsyncTask<String, Void, Task> {
        @Override
        protected Task doInBackground(String... taskId) {
            verifySettings();
            Task task = null;
            for (String id : taskId) {
                try {
                    Get get = new Get.Builder("cmput301w18t05", id).type("task").build();
                    JestResult result = client.execute(get);
                    task = result.getSourceAsObject(Task.class);
                } catch (Exception e) {
                    Log.i("Error", "Get task failed");
                }
            }
            return task;
        }
    }

    public static class GetAllTasks extends AsyncTask<Void, Void, ArrayList<Task>> {
        int from;
        int size;

        public GetAllTasks(int from, int size) {
            this.from = from;
            this.size = size;
        }

        @Override
        protected ArrayList<Task> doInBackground(Void... voids) {
            verifySettings();
            ArrayList<Task> foundTasks = new ArrayList<>();
            String query =
                    "{\n" +
                            "   \"from\" : "+ from +", \"size\" : "+ size +",\n"+
                            "   \"query\": {\n" +
                            "       \"match_all\" : {}\n" +
                            "   }\n" +
                            "}";
            Log.i("Query:",query);

            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t05")
                    .addType("task")
                    .build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> matchingTasks = result.getSourceAsObjectList(Task.class);
                    foundTasks.addAll(matchingTasks);
                }
            }
            catch (Exception e) {
                Log.i("Error","GetAllTasks search encountered an error.");
                return null;
            }

            return foundTasks;
        }
    }

    /**
     * GeTaskByProviderUsername
     *
     * Get a task that matches the username provided,
     * return null otherwise
     */
    public static class GetTasksByProviderUsername extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... usernames) {
            verifySettings();
            ArrayList<Task> foundTasks = new ArrayList<>();

            for (String username : usernames) {
                String query = "{ \"query\" : { \"common\" : \"TaskProvider.username\" : \"" + username + "\" }}";
                Log.i("Query:", query);

                Search search = new Search.Builder(query)
                        .addIndex("cmput301w18t05")
                        .addType("task")
                        .build();

                try {
                    SearchResult result = client.execute(search);
                    if (result.isSucceeded()) {
                        List<Task> matchingTasks = result.getSourceAsObjectList(Task.class);
                        foundTasks.addAll(matchingTasks);
                    }
                } catch (Exception e) {
                    Log.i("Error", "GetAllTasks search encountered an error.");
                    return null;
                }
            }
            return foundTasks;
        }
    }

    /**
     * GetTasksByRequesterUsername
     *
     * get a task that matches the requester username
     * return null otherwise.
     */
    public static class GetTasksByRequesterUsername extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... usernames) {
            verifySettings();
            ArrayList<Task> foundTasks = new ArrayList<>();

            for (String username : usernames) {
                String query = "{ \"query\" : { \"common\" : {\"requesterUsername\" : \"" + username + "\" }}}";
                Log.i("Query:", query);

                Search search = new Search.Builder(query)
                        .addIndex("cmput301w18t05")
                        .addType("task")
                        .build();

                try {
                    SearchResult result = client.execute(search);
                    if (result.isSucceeded()) {
                        List<Task> matchingTasks = result.getSourceAsObjectList(Task.class);
                        foundTasks.addAll(matchingTasks);
                    }
                } catch (Exception e) {
                    Log.i("Error", "GetAllTasks search encountered an error.");
                    return null;
                }
            }
            return foundTasks;
        }
    }

    /**
     * SearchForTasks
     *
     * Search for tasks that match a given keyword in the description
     * return null if failed and return an empty array if no results.
     * return a array with matching tasks if found.
     */
    public static class SearchForTasks extends AsyncTask<String, Void, ArrayList<Task>> {
        int from,size;

        public SearchForTasks(int from, int size) {
            this.from = from;
            this.size = size;
        }

        @Override
        protected ArrayList<Task> doInBackground(String... keywords) {
            verifySettings();
            ArrayList<Task> taskList = new ArrayList<>();

            for (String keyword : keywords) {
                String query =
                "{\n" +
                "   \"from\" : " + from + ",\n" +
                "   \"size\" : " + size + ",\n" +
                "   \"query\": {\n" +
                "       \"common\" : {\n" +
                "           \"description\" : {\n" +
                "               \"query\" : \"" + keyword + "\"\n" +
                "           }\n" +
                "       }\n" +
                "   }\n" +
                "}";
                Log.i("Query:",query);

                Search search = new Search.Builder(query)
                        .addIndex("cmput301w18t05")
                        .addType("task")
                        .build();
                try {
                    SearchResult result = client.execute(search);
                    if (result.isSucceeded()) {
                        List<Task> matchingTasks = result.getSourceAsObjectList(Task.class);
                        taskList.addAll(matchingTasks);
                    }
                    else {
                        Log.i("Event", "No results found in query: " + query);
                        return null;
                    }
                } catch (Exception e) {
                    Log.i("Error", "Search failed");
                    return null;
                }
            }
            return taskList;
        }
    }

    /**
     * AddUser
     *
     * add a user into the elastic search database by passing
     * in a user object.
     */
    public static class AddUser extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            verifySettings();
            for (User user : users) {
                Index index = new Index.Builder(user).index("cmput301w18t05").type("user").build();
                try {
                    Log.i("Event", "Trying to add user "+user.toString());
                    DocumentResult result = client.execute(index);

                    Log.i("Event", "Jest returned with: "+result);
                    if (result.isSucceeded()) {
                        user.setId(result.getId());
                        Log.i("Event", "Successfully added: "+user.toString()+" with id: "+user.getId()+" ... at least we think so.");
                        return true;
                    }
                    else {
                        Log.i("Event", "Failed to add user: "+user.toString());
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "User not added");
                }
            }
            return false;
        }
    }

    /**
     * UpdateUser
     *
     * todo: current implementation does not work.
     */
    public static class UpdateUser extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifySettings();
            for (User user : users) {
                Index index = new Index.Builder(user).index("cmput301w18t05").type("user").id(user.getId()).build();
                try {
                    client.execute(index);
                } catch (Exception e) {
                    Log.i("Error", "User not updated");
                }
            }
            return null;
        }
    }

    /**
     * RemoveUser
     *
     * remove a user that matches a user id
     * todo: fix this to take in a user id
     */
    public static class RemoveUser extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifySettings();
            for (User user : users) {
                try {
                    client.execute(new Delete.Builder(user.getId()).index("cmput301w18t05").type("user").build());
                } catch (Exception e) {
                    Log.i("Error", "User not deleted");
                }
            }
            return null;
        }
    }

    /**
     * GetUser
     *
     * remove a user by user id
     */
    public static class GetUser extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... userIds) {
            verifySettings();
            User user = null;

            for (String id : userIds) {
                try {
                    Get get = new Get.Builder("cmput301w18t05", id).type("user").build();
                    JestResult result = client.execute(get);
                    user = result.getSourceAsObject(User.class);
                }
                catch (Exception e) {
                    Log.i("Error", "Get task failed");
                }
            }

            return user; // will return null if no user found
        }
    }

    /**
     * GetUserByUsername
     *
     * Get a user class by username
     */
    public static class GetUserByUsername extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... userIds) {
            verifySettings();
            ArrayList<User> foundUsers = new ArrayList<>();

            for (String id : userIds) {
                String query = "{ \"query\" : { \"match\" : { \"username\" : \""+ id +"\" } } }";
                Log.i("QUERY: ",query);

                try {
                    Search search = new Search.Builder(query)
                            .addIndex("cmput301w18t05")
                            .addType("user")
                            .build();

                    SearchResult result = client.execute(search);
                    if (result.isSucceeded()) {
                        List<User> test = result.getSourceAsObjectList(User.class);
                        foundUsers.addAll(test);
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "Get user by username failed");
                    return null;
                }
            }
            if (foundUsers.size() > 0) {
                Log.i("FOUND USER: ", foundUsers.get(0).getId()+ " " +foundUsers.get(0).getName());
                return foundUsers.get(0); // will return null if no user found
            }
            return null;
        }
    }

    /**
     * AddBid
     *
     * add the provided bid object to the elastic search
     * bid index
     */
    public static class AddBid extends AsyncTask<Bid, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Bid... bids) {
            verifySettings();

            for (Bid bid : bids) {
                Index index = new Index.Builder(bid).index("cmput301w18t05").type("bid").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        bid.setId(result.getId());
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("Error", "Bid not added");
                }
            }
            return false;
        }
    }

    /**
     * GetBidsByUserID
     *
     * get all the bids that match a given user id
     * todo: take in size and from integer to allow pagination
     */
    public static class GetBidsByUserID extends AsyncTask<String, Void, ArrayList<Bid>> {
        @Override
        protected ArrayList<Bid> doInBackground(String... userIds) {
            verifySettings();
            ArrayList<Bid> foundBids = new ArrayList<>();

            for (String  userId : userIds) {
                String query = "{ \"query\" : { \"match\" : { \"userId\" : \""+ userId + "\" } } }";
                Log.i("Query: ", query);

                SearchResult result;
                Search search = new Search.Builder(query)
                        .addIndex("cmput301w18t05")
                        .addType("bid")
                        .build();

                try {
                    result = client.execute(search);
                    if (result.isSucceeded()) {
                        List<Bid> newBids = result.getSourceAsObjectList(Bid.class);
                        foundBids.addAll(newBids);
                    }
                    else {
                        return null;
                    }
                }
                catch (Exception e) {
                    return null;
                }
            }
            return foundBids;
        }
    }

    /**
     * GetBidsByTaskID
     *
     * Get all bids that match a given task id
     */
    public static class GetBidsByTaskID extends AsyncTask<String, Void, ArrayList<Bid>> {
        @Override
        protected ArrayList<Bid> doInBackground(String... taskIds) {
            verifySettings();
            ArrayList<Bid> foundBids = new ArrayList<>();

            for (String  taskId : taskIds) {
                String query = "{ \"query\" : { \"common\" : { \"taskId\" : \""+ taskId + "\" } } }";
                Log.i("Query: ", query);

                SearchResult result;
                Search search = new Search.Builder(query)
                        .addIndex("cmput301w18t05")
                        .addType("bid")
                        .build();

                try {
                    result = client.execute(search);
                    if (result.isSucceeded()) {
                        List<Bid> newBids = result.getSourceAsObjectList(Bid.class);
                        foundBids.addAll(newBids);
                    }
                    else {
                        return null;
                    }
                }
                catch (Exception e) {
                    return null;
                }
            }
            return foundBids;
        }
    }

    /**
     * RemoveBid
     *
     * remove a bid given a bid id
     * todo: make this take in a bid id
     */
    public static class RemoveBid extends AsyncTask<Bid, Void, Void> {
        @Override
        protected Void doInBackground(Bid... bids) {
            verifySettings();
            for (Bid bid : bids) {
                try {
                    client.execute(new Delete.Builder(bid.getId()).index("cmput301w18t05").type("bid").build());
                } catch (Exception e) {
                    Log.i("Error", "Bid not deleted");
                }
            }
            return null;
        }
    }

    /**
     * verifySettings
     *
     * connect to the elastic search server
     */
    private static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/"); // do not put the index here yet
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}


