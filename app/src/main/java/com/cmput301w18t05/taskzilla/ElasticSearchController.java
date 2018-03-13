package com.cmput301w18t05.taskzilla;

import android.os.AsyncTask;
import android.util.Log;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy
 *
 * TODO: my bids returns a list of tasks
 */

public class ElasticSearchController {

    private static JestDroidClient client;

    public static class AddTask extends AsyncTask<Task, Void, Void> {
        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();
            for (Task task : tasks) {
                Index index = new Index.Builder(task).index("cmput301w18t05").type("task").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        task.setId(result.getId());
                    }
                } catch (Exception e) {
                    Log.i("Error", "Task not added");
                }
            }
            return null;
        }
    }

    public static class UpdateTask extends AsyncTask<Task, Void, Void> {
        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();
            for (Task task : tasks) {
                Index index = new Index.Builder(task).index("cmput301w18t05").type("task").id(task.getId()).build();
                try {
                    client.execute(index);
                } catch (Exception e) {
                    Log.i("Error", "Task not updated");
                }
            }
            return null;
        }
    }

    public static class RemoveTask extends AsyncTask<Task, Void, Void> {
        @Override
        protected Void doInBackground(Task... tasks) {
            verifySettings();
            for (Task task : tasks) {
                try {
                    client.execute(new Delete.Builder(task.getId()).index("cmput301w18t05").type("task").build());
                } catch (Exception e) {
                    Log.i("Error", "Task not deleted");
                }
            }
            return null;
        }
    }

    public static class GetTask extends AsyncTask<String, Void, Task> {
        @Override
        protected Task doInBackground(String... taskId) {
            verifySettings();
            Task task = new Task();
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

    public static class SearchForTasks extends AsyncTask<String, Void, List<SearchResult.Hit<Task, Void>>> {
        @Override
        protected List<SearchResult.Hit<Task, Void>> doInBackground(String... keywords) {
            verifySettings();
            List<SearchResult.Hit<Task, Void>> tasks = new ArrayList<>();

            /*
            {
                "query": {
                    "match": {
                        "description": {
                            "query": keywords,
                            "operator": "and"
                        }
                    }
                }
            }
            */
            String query =
                    "{\n" +
                            "   \"query\": {\n" +
                            "       \"match\" : {\n" +
                            "           \"description\" : {\n" +
                            "               \"query\" : " + keywords[0] + ",\n" +
                            "               \"operator\" : \"and\"\n" +
                            "           }\n" +
                            "       }\n" +
                            "   }\n" +
                            "}";

            Search search = new Search.Builder(query).addIndex("cmput301w18t05").addType("task").build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    tasks = result.getHits(Task.class);
                }
            } catch (Exception e) {
                Log.i("Error", "Search failed");
            }
            return tasks;
        }
    }

    public void addUser(User user) {
        AsyncTask<User, Void, Void> ESAddUserAsync = new AddUser();
        try {
            ESAddUserAsync.execute(user).get();
        }
        catch (Exception e) {
            // todo
        }
    }

    public static class AddUser extends AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
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
                    }
                    else {
                        Log.i("Event", "Failed to add user: "+user.toString());
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "User not added");
                }
            }
            return null;
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
        protected User doInBackground(String... userId) {
            verifySettings();
            User user = new User();
            for (String id : userId) {
                try {
                    Get get = new Get.Builder("cmput301w18t05", id).type("user").build();
                    JestResult result = client.execute(get);
                    user = result.getSourceAsObject(User.class);
                } catch (Exception e) {
                    Log.i("Error", "Get task failed");
                }
            }
            return user;
        }
    }

    public static class AddBid extends AsyncTask<Bid, Void, Void> {
        @Override
        protected Void doInBackground(Bid... bids) {
            verifySettings();
            for (Bid bid : bids) {
                Index index = new Index.Builder(bid).index("cmput301w18t05").type("bid").build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        bid.setId(result.getId());
                    }
                } catch (Exception e) {
                    Log.i("Error", "Bid not added");
                }
            }
            return null;
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


