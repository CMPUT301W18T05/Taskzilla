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

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

/**
 * AppCache class
 *
 * Stores information used in case device drops connection.
 */
public class AppCache {

    private static AppCache instance = new AppCache();
    private TreeSet<Task> cachedTasks = new TreeSet<>();
    private TreeSet<Bid> cachedBids = new TreeSet<>();
    private TreeSet<User> cachedUsers = new TreeSet<>();
    private TreeSet<Notification> cachedNotifications = new TreeSet<>();
    private int taskIDCounter = 0;
    private int bidIDCounter = 0;

    private AppCache() {
    }

    public static AppCache getInstance() {
        return instance;
    }

    public int getTaskIDCounter() {
        return taskIDCounter;
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

    /**
     * add task to app cache
     *
     * @param task
     */
    public void addInCache(Task task) {
        System.out.println("Adding task to cache with requester = "+task.getTaskRequester().getUsername());

        if (task.getId() == null) {
            System.out.println("Setting this taskid to "+taskIDCounter);
            task.setId(String.valueOf(taskIDCounter));
            taskIDCounter++;
        }

        cachedTasks.add(task);

    }

    /**
     * add a collection of tasks to the app cache
     * @param tasks
     */
    public void addInCache(Collection<Task> tasks) {
        if (tasks != null)
            cachedTasks.addAll(tasks);
    }

    /**
     * add a bid into cache
     * @param bid
     */
    public void addInCache(Bid bid) {
        if (bid == null)
            return;
        System.out.println("Adding bid: " + bid.getId() + " to cache.");

        bid.setId(String.valueOf(bidIDCounter));
        cachedBids.add(bid);
        bidIDCounter++;
    }

    /**
     * add a collection of bids into the cache and remove duplicates.
     * @param bids
     */
    public void addBidsToCache(Collection<Bid> bids) {
        if (bids != null)
            cachedBids.addAll(bids);
    }

    /**
     * add user to cache
     * @param user
     */
    public void addInCache(User user) {
        if (user != null)
            cachedUsers.add(user);
    }

    /**
     * remove all tasks from the cache that match the task id
     * @param taskid
     */
    public void removeTaskByTaskid(String taskid) {
        for (Task t : cachedTasks)
            if (t.getId().equals(taskid))
                cachedTasks.remove(t);
    }

    /**
     * find a  user object in the cache that matches the userid
     * @param userid
     * @return user object that matches the userid, will only ever return at most one.
     */
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

    /**
     * find all tasks in the cache that match the task id
     * @param taskid
     * @return all tasks with this task id.
     */
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

    /**
     * find all caches bids that match the userid
     * @param userid
     * @return all bids that have this user id.
     */
    public ArrayList<Bid> findCachedBidsByUserid(String userid) {
        System.out.println("Searching for bids in cache with userid: "+userid);

        ArrayList<Bid> result = new ArrayList<>();
        for (Bid b : cachedBids) {
            System.out.println("Looking at bid with userid: "+b.getUserId()+" and taskid: "+b.getTaskId());
            if (b.getUserId().equals(userid)) {
                System.out.println("Found bid in bidcache: "+b+" matching userid: "+userid);
                result.add(b);
            }
        }
        return result;
    }

    /**
     * find all bids that match a task id
     * @param Taskid
     * @return all bids matching the task id.
     */
    public ArrayList<Bid> findCachedBidsByTaskid(String Taskid) {
        System.out.println("Searching for bids in cache with taskid: "+Taskid);

        ArrayList<Bid> result = new ArrayList<>();
        for (Bid b : cachedBids) {
            if (b.getTaskId().equals(Taskid)) {
                System.out.println("Found bid in bidcache: "+b.getId()+" matching taskid: "+Taskid);
                result.add(b);
            }
        }
        return result;
    }

    /**
     * remove bids in cache by bid id.
     * @param id
     */
    public void removeBidByBidid(String id) {
        System.out.println("Trying to remove bid: "+id);
        for (Bid b : cachedBids) {
            if (b.getId().equals(id)) {
                System.out.println("Found bid with this id");
                cachedBids.remove(b);
                return;
            }
        }
        System.out.println("Could not find bid with id: "+id);
    }

    /**
     * clear the cache of all objects. This happens mostly when the device comes online.
     */
    public void emptyCache() {
        System.out.println("Clearing local cache.");
        cachedTasks.clear();
        cachedUsers.clear();
        cachedBids.clear();
    }

}
