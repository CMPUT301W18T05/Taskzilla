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

package com.cmput301w18t05.taskzilla.controller;

import android.content.Context;
import android.view.View;

import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.GetUserRequest;

/**
 * Created by Micheal-Nguyen on 3/16/2018.
 */

public class ProfileController {
    private Context ctx;
    private View view;
    private String userId;
    private User user;

    public ProfileController(View v, Context context) {
        this.ctx = context;
        this.view = v;
        this.userId = new String();
    }

    public void setUserID(String id) {
        this.userId = id;
    }

    public User getUser() {
        return this.user;
    }

    /**
     * getUserRequest
     * get user from RequestManager and set the
     * user to be the result for the controller
     *
     * @author Micheal-Nguyen
     */
    public void getUserRequest() {
        GetUserRequest request = new GetUserRequest(userId);
        RequestManager.getInstance().invokeRequest(ctx, request);

        this.user = request.getResult();
    }

}
