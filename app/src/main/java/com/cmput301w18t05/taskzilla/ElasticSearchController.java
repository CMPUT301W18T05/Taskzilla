package com.cmput301w18t05.taskzilla;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy
 */

public class ElasticSearchController {

    private static JestDroidClient client;

    public void addTask(Task task) {

    }

    public void updateTask(Task task) {

    }

    public void removeTask(Task task) {

    }

    public Task searchTask(String taskName, String userName) {
        Task task = new Task();
        return task;
    }

    public List<Task> searchTaskDescriptions(List<String> keywords) {
        List<Task> tasks = new ArrayList<Task>();
        return tasks;
    }

    public void addUser(User user) {

    }

    public void updateUser(User user) {

    }

    public void removeUser(User user) {

    }

    public User searchUser(String userName) {
        User user = new User();
        return user;
    }

    public void addBid(Bid bid) {

    }

    public void removeBid(Bid bid) {

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
