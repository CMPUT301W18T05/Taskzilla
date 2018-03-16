package com.cmput301w18t05.taskzilla;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Tasks;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Doc;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy
 *
 * TODO: my bids returns a list of tasks
 */

public class ElasticSearchController {

    private static final ElasticSearchController inst = new ElasticSearchController();

    private static JestDroidClient client;

    private ElasticSearchController() {
    }

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
                Update update = new Update.Builder(task).index("cmput301w18t05").type("task").id(task.getId()).build();
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
        @Override
        protected ArrayList<Task> doInBackground(Void... voids) {
            verifySettings();
            ArrayList<Task> foundTasks = new ArrayList<>();
            String query =
                    "{\n" +
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

    public static class GetTasksByProviderUsername extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... usernames) {
            verifySettings();
            ArrayList<Task> foundTasks = new ArrayList<>();

            for (String username : usernames) {
                String query = "{ \"query\" : { \"common\" : \"TaskProvider.username\" : " + username + " }}";
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

    public static class GetTasksByRequesterUsername extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... usernames) {
            verifySettings();
            ArrayList<Task> foundTasks = new ArrayList<>();

            for (String username : usernames) {
                String query = "{ \"query\" : { \"common\" : \"TaskRequester.username\" : " + username + " }}";
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

    public static class SearchForTasks extends AsyncTask<String, Void, ArrayList<Task>> {
        @Override
        protected ArrayList<Task> doInBackground(String... keywords) {
            verifySettings();
            ArrayList<Task> taskList = new ArrayList<>();

            for (String keyword : keywords) {
                String query =
                "{\n" +
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
                    }
                } catch (Exception e) {
                    Log.i("Error", "Search failed");
                    return null;
                }
            }
            return taskList;
        }
    }

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

    public static class GetBidsByUserID extends AsyncTask<String, Void, ArrayList<Bid>> {
        @Override
        protected ArrayList<Bid> doInBackground(String... userIds) {
            verifySettings();
            ArrayList<Bid> foundBids = new ArrayList<>();

            for (String  userId : userIds) {
                String query = "{ \"query\" : { \"common\" : { \"requesterId\" : \""+ userId + "\" } }";
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


