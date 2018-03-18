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

import android.test.ActivityInstrumentationTestCase2;

import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.controller.ProfileController;
import com.cmput301w18t05.taskzilla.fragment.ProfileFragment;

/**
 * Created by Jeremy on 2018-02-23.
 */

public class UserProfileActivityTest extends ActivityInstrumentationTestCase2 {
    public UserProfileActivityTest(){
        super(MainActivity.class);
    }

    public void testProviderRatingUpdate() {
        //ProfileFragment profileFragment = new ProfileFragment();
        //User user = new User();
        //ProfileController profileController = new ProfileController(user, profileFragment);
        //profileController.updateProviderRating(10.0);
        //assertEquals("10.0", profileFragment.getProviderRatingField().getText());
    }

}
