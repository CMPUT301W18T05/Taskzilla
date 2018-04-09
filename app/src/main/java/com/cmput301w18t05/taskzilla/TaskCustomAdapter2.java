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

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Custom listView adapter for displaying tasks
 * Shows the task title, requester, status, and lowest bid (if any)
 *
 * @author myapplestory
 */
public class TaskCustomAdapter2 extends ArrayAdapter<Photo> {

    public TaskCustomAdapter2(Context context, int layoutResource, ArrayList<Photo> photoList) {
        super(context, layoutResource, photoList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Photo photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.tasks_list_view3,
                    parent, false);
        }
        ImageView requesterImage = convertView.findViewById(R.id.SearchListRequesterPicture);

        try {
            assert photo != null;
            requesterImage.setImageBitmap(photo.StringToBitmap());
        }
        catch (Exception e){
            Photo defaultPhoto = new Photo("");
            requesterImage.setImageBitmap(defaultPhoto.StringToBitmap());
        }
        return convertView;
    }

    @Override
    public void setNotifyOnChange(boolean notifyOnChange) {
        super.setNotifyOnChange(notifyOnChange);
    }
}
