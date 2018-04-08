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
 * Created by kio22 on 2018-04-07.
 */

public class AppColors {

    private static AppColors instance = new AppColors();
    private static String actionBarColor;
    private static String actionBarTextColor;
    private static String backgroundColor;

    private AppColors(){

    }


    public static AppColors getInstance() {
        return instance;
    }


    public void setActionBarColor(String actionBarColor) {
        this.actionBarColor = actionBarColor;
    }

    public String getActionBarColor() {
        return actionBarColor;
    }

    public void setActionBarTextColor(String actionBarTextColor) {
        AppColors.actionBarTextColor = actionBarTextColor;
    }

    public  String getActionBarTextColor() {
        return actionBarTextColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        AppColors.backgroundColor = backgroundColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public  void setInstance(AppColors instance) {
        AppColors.instance = instance;
    }

}
