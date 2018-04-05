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
import java.util.Collection;
import java.util.TreeSet;

/**
 * Created by wyatt on 04/04/18.
 */

public class AppCache {

    private static AppCache instance = new AppCache();
    private TreeSet<Task> cachedTasks = new TreeSet<>();
    private TreeSet<Bid> cachedBids = new TreeSet<>();
    private TreeSet<User> cachedUsers = new TreeSet<>();
    private int taskIDCounter = 0;

    private AppCache() {
    }

    public static AppCache getInstance() {
        return instance;
    }

    public ArrayList<Task> getCachedTasks() {
        return new ArrayList<>(cachedTasks);
    }

    public ArrayList<Bid> getCachedBids() {
        return new ArrayList<>(cachedBids);
    }

    public ArrayList<User> getCachedUsers() {
        return new ArrayList<>(cachedUsers);
    }

    // can override these to accept different classes
    public void addInCache(Task task) {
        System.out.println("Adding task to cache with requester = "+task.getTaskRequester().getUsername());
        System.out.println("Setting this taskid to "+taskIDCounter);
        task.setId(String.valueOf(taskIDCounter));
        cachedTasks.add(task);
        taskIDCounter++;
    }

    public void addInCache(Collection<Task> tasks) {
        cachedTasks.addAll(tasks);
    }

    public void addInCache(Bid bid) {
        cachedBids.add(bid);
    }

    public void addBidsToCache(Collection<Bid> bids) {
        cachedBids.addAll(bids);
    }

    public void addInCache(User user) {
        cachedUsers.add(user);
    }

    public void removeTaskByTaskid(String taskid) {
        for (Task t : cachedTasks)
            if (t.getId().equals(taskid))
                cachedTasks.remove(t);
    }

    public User findCachedUserByUserid(String userid) {
        System.out.println("Searching for users in cache with userid: "+userid);
        if (currentUser.getInstance().getId().equals(userid)) {
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
            System.out.println(t.getId().equals(taskid));
            System.out.println("\""+t.getId()+"\" \""+taskid+"\"");
            if (t.getId().equals(taskid)) {
                System.out.println("Found task "+t);
                return t;
            }
        }
        System.out.println("Did not find this task");
        return null;
    }

    public ArrayList<Bid> findCachedBidsByUserid(String userid) {
        System.out.println("Searching for bids in cache with userid: "+userid);

        ArrayList<Bid> result = new ArrayList<>();
        for (Bid b : cachedBids) {
            if (b.getId().equals(userid)) {
                System.out.println("Found bid in bidcache: "+b+" matching userid: "+userid);
                result.add(b);
            }
        }
        return result;
    }

    public ArrayList<Bid> findCachedBidsByTaskid(String Taskid) {
        System.out.println("Searching for bids in cache with taskid: "+Taskid);

        ArrayList<Bid> result = new ArrayList<>();
        for (Bid b : cachedBids) {
            if (b.getTaskId().equals(Taskid)) {
                System.out.println("Found bid in bidcache: "+b+" matching taskid: "+Taskid);
                result.add(b);
            }
        }
        return result;
    }

    public void emptyCache() {
        cachedTasks.clear();
        cachedUsers.clear();
        cachedBids.clear();
    }

}
