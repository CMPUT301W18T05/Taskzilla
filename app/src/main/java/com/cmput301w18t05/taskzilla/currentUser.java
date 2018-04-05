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






/**
 * currentUser
 *
 * Singleton object to hold the currently logged
 * in user.
 * @author Colin
 * @version 1.0
 */
public class currentUser extends User {
    private static currentUser instance = new currentUser();
    private static User current = null;

    private currentUser(){

    }

    /**
     * Returns the instance of the Current user
     * @return
     */
    public static User getInstance() {
        return current;
    }

    public static currentUser getRealInstance() {
        return instance;
    }

    /**
     * Initializes the current user
     * @param user
     */
    public void setUser(User user) {
        currentUser.current = user;
    }
}
