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

package com.cmput301w18t05.taskzilla;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by wyatt on 04/04/18.
 */

public class AppCache {

    private static AppCache instance = new AppCache();
    private ArrayList<Task> cachedTasks = new ArrayList<>();
    private ArrayList<Bid> cachedBids = new ArrayList<>();
    private ArrayList<User> cachedUsers = new ArrayList<>();
    private int taskIDCounter = 0;

    private AppCache() {
    }

    public static AppCache getInstance() {
        return instance;
    }

    public ArrayList<Task> getCachedTasks() {
        return cachedTasks;
    }

    public ArrayList<Bid> getCachedBids() {
        return cachedBids;
    }

    public ArrayList<User> getCachedUsers() {
        return cachedUsers;
    }

    // can override these to accept different classes
    public void addInCache(Task task) {
        System.out.println("Adding task to cache with requester = "+task.getTaskRequester().getUsername());
        System.out.println("Setting this taskid to "+taskIDCounter);
        task.setId(String.valueOf(taskIDCounter));
        cachedTasks.add(task);
        taskIDCounter++;
    }

    public void addInCache(Bid bid) {
        cachedBids.add(bid);
    }

    public void addInCache(User user) {
        cachedUsers.add(user);
    }

    public User findCachedUserByUserid(String userid) {
        System.out.println("Searching for users in cache with userid: "+userid);
        if (currentUser.getInstance().getId() == userid) {
            System.out.println("Found user in usercache: "+currentUser.getInstance()+" with username: "+currentUser.getInstance().getUsername());
            return currentUser.getInstance();
        }
        for (User u : cachedUsers) {
            if (u.getId().equals(userid)) {
                System.out.println("Found user in usercache with name: "+u+" and username: "+u.getUsername());
                return u;
            }
        }
        return null;
    }

    public Task findCachedTaskById(String taskid) {
        System.out.println("Searching cache for tasks with taskid: "+taskid);

        for (Task t : cachedTasks) {
            System.out.println("Looking at task: "+t+" with id: "+t.getId());
            System.out.println(t.getId() == taskid);
            System.out.println("\""+t.getId()+"\" \""+taskid+"\"");
            if (t.getId().equals(taskid)) {
                System.out.println("Found task "+t);
                return t;
            }
        }
        System.out.println("Did not find this task");
        return null;
    }

}
