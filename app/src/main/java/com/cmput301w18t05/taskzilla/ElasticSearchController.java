package com.cmput301w18t05.taskzilla;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.Delete;
import io.searchbox.core.Index;

import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jeremy
 */

public class ElasticSearchController {

    private static JestDroidClient client;

    public void addTask(Task task) throws Exception {
        verifySettings();
        String source = taskSourceString(task);
        if (Strings.isNullOrEmpty(source)) {
            throw new Exception("Builder error: returned empty string");
        } else {
            try {
                Index index = new Index.Builder(source).index("cmput301w18t05").type("task").build();
                client.execute(index);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateTask(Task task) throws Exception {
        verifySettings();
        String source = taskSourceString(task);
        if (Strings.isNullOrEmpty(source)) {
            throw new Exception("Builder error: returned empty string");
        } else {
            try {

                Index index = new Index.Builder(source).index("cmput301w18t05").type("task").id(task.getId()).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeTask(String taskId) {
        verifySettings();
        try {
            client.execute(new Delete.Builder(taskId).index("cmput301w18t05").type("task").build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Task searchTask(String taskName, String userName) {
        Task task = new Task();
        return task;
    }

    public List<Task> searchTaskDescriptions(List<String> keywords) {
        List<Task> tasks = new ArrayList<Task>();
        return tasks;
    }

    public String taskSourceString(Task task) {
        String source = "";
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            builder.field("name", task.getName());
            builder.field("id", "");
            builder.field("requester", task.getTaskRequester());
            builder.field("provider", task.getTaskProvider());
            builder.field("status", task.getStatus());
            builder.field("description", task.getDescription());
            builder.field("location", task.getLocation());
            builder.startArray("bids");
            for (Bid bid : task.getBids()) {
                builder.value(bid.getBidAmount());
            }
            builder.endArray();
            builder.startArray("photos");
            for (Photo photo : task.getPhotos()) {
                builder.value(photo.getImage());
            }
            builder.endArray();
            builder.endObject();
            source = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return source;
        }
    }

    public void addUser(User user) throws Exception {
        verifySettings();
        String source = userSourceString(user);
        if (Strings.isNullOrEmpty(source)) {
            throw new Exception("Builder error: returned empty string");
        } else {
            try {
                Index index = new Index.Builder(source).index("cmput301w18t05").type("user").build();
                client.execute(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUser(User user) throws Exception {
        verifySettings();
        String source = userSourceString(user);
        if (Strings.isNullOrEmpty(source)) {
            throw new Exception("Builder error: returned empty string");
        } else {
            try {
                Index index = new Index.Builder(source).index("cmput301w18t05").type("user").id(user.getId()).build();
                client.execute(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUser(String userId) {
        verifySettings();
        try {
            client.execute(new Delete.Builder(userId).index("cmput301w18t05").type("user").build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User searchUser(String userName) {
        User user = new User();
        return user;
    }

    public String userSourceString(User user) {
        String source = "";
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            builder.field("name", user.getName());
            builder.field("username", user.getUsername());
            builder.field("id", "");
            builder.field("phone", user.getPhone());
            builder.field("email", user.getEmail());
            builder.field("providerRating", user.getProviderRating());
            builder.field("requesterRating", user.getRequesterRating());
            builder.field("numRequests", user.getNumRequests());
            builder.field("numCompleteTasks", user.getNumCompleteTasks());
            builder.startArray("photos");
            for (Photo photo : user.getPhotos()) {
                builder.value(photo.getImage());
            }
            builder.endArray();
            builder.endObject();
            source = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return source;
        }
    }

    public void addBid(Bid bid) throws Exception {
        verifySettings();
        String source = bidSourceString(bid);
        if (Strings.isNullOrEmpty(source)) {
            throw new Exception("Builder error: returned empty string");
        } else {
            try {
                Index index = new Index.Builder(source).index("cmput301w18t05").type("bid").build();
                client.execute(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateBid(Bid bid) throws Exception {
        verifySettings();
        String source = bidSourceString(bid);
        if (Strings.isNullOrEmpty(source)) {
            throw new Exception("Builder error: returned empty string");
        } else {
            try {
                Index index = new Index.Builder(source).index("cmput301w18t05").type("bid").id(bid.getId()).build();
                client.execute(index);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeBid(String bidId) {
        verifySettings();
        try {
            client.execute(new Delete.Builder(bidId).index("cmput301w18t05").type("bid").build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String bidSourceString(Bid bid) {
        String source = "";
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
            builder.field("bidAmount", bid.getBidAmount());
            builder.field("id", "");
            builder.field("provider", bid.getProvider());
            builder.field("requester", bid.getRequester());
            builder.field("taskName", bid.getTaskName());
            builder.endObject();
            source = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return source;
        }
    }

    public List<Task> getTasksFromBid(String userName) {
        List<Task> tasks = new ArrayList<Task>();
        return tasks;
    }


    private static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/cmput301w18t05/");
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
