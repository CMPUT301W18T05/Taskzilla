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
import com.cmput301w18t05.taskzilla.request.DeletionRequest;

/**
 * Created by Andy on 4/5/2018.
 */

/**
 * Request for removing a Notification object from Elastic Search using a task id
 * @author Andy
 * @see ElasticSearchController
 * @version 1.0
 */
public class RemoveNotificationRequest extends DeletionRequest {
    private String nId;

    public RemoveNotificationRequest(String id) {
        this.nId = id;
        queueReady = true;
    }

    public void execute(){
        ElasticSearchController.RemoveNotification deleteRequest = new ElasticSearchController.RemoveNotification();
        deleteRequest.execute(this.nId);
    }

    @Override
    public void executeOffline(){
        //AppCache.getInstance().removeNotificationById(nId);
    }

    @Override
    public boolean requiresConnection() {
        return false;
    }

    public boolean getResult() {
        return true;
    }
}
