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

package com.cmput301w18t05.taskzilla.request.command;

import com.cmput301w18t05.taskzilla.AppCache;
import com.cmput301w18t05.taskzilla.controller.ElasticSearchController;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.Request;

/**
 * Request for getting a user object from Elastic Search using a username
 * @author Wyatt
 * @see ElasticSearchController
 * @version 1.0
 */
public class GetUserByUsernameRequest extends Request {
    ElasticSearchController.GetUserByUsername task;
    String username;
    User result;

    public GetUserByUsernameRequest(String username) {
        this.username = username;
    }

    public void execute() {
        task = new ElasticSearchController.GetUserByUsername();
        task.execute(this.username);
    }

    @Override
    public void executeOffline() {
    }

    public User getResult() {
        if (result != null) {
            return result;
        }
        try {
            result = this.task.get();
            AppCache.getInstance().addInCache(result);
            return result;
        }
        catch (Exception e) {
            return null;
        }
    }
}
