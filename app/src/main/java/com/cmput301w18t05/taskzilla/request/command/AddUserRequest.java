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

import com.cmput301w18t05.taskzilla.controller.ElasticsearchController;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.InsertionRequest;

/**
 * Created by wyatt on 07/03/18.
 */

public class AddUserRequest extends InsertionRequest {
    User user;
    ElasticsearchController.AddUser task;

    public AddUserRequest(User user) {
        this.user = user;
    }

    @Override
    public void execute() {
        task = new ElasticsearchController.AddUser();
        task.execute(user); // for now, subject to change.
    }

    @Override
    public void executeOffline() {
    }

    public boolean getResult() {
        try {
            return task.get();
        }
        catch (Exception e) {
            return false;
        }
    }

}
