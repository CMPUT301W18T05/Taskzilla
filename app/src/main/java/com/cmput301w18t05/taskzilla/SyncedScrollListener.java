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

import android.view.View;
import android.widget.AbsListView;

/**
 *
 * class for synced scroll on search fragment listview code gotten from
 * https://stackoverflow.com/questions/8371743/scrolling-listviews-together?lq=1
 *
 */

public class SyncedScrollListener implements AbsListView.OnScrollListener {
    int offset;
    int oldVisibleItem = -1;
    int currentHeight;
    int prevHeight;
    private View mSyncedView;


    public SyncedScrollListener(View syncedView){

        if(syncedView == null){
            throw new IllegalArgumentException("syncedView is null");
        }

        mSyncedView = syncedView;
    }

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        int[] location = new int[2];

        if(visibleItemCount == 0){
            return;
        }

        if(oldVisibleItem != firstVisibleItem){

            if(oldVisibleItem < firstVisibleItem){
                prevHeight = currentHeight;
                currentHeight = view.getChildAt(0).getHeight();

                offset += prevHeight;

            }else{
                currentHeight = view.getChildAt(0).getHeight();

                View prevView;
                if((prevView = view.getChildAt(firstVisibleItem - 1)) != null){
                    prevHeight = prevView.getHeight();
                }else{
                    prevHeight = 0;
                }

                offset -= currentHeight;
            }

            oldVisibleItem = firstVisibleItem;
        }

        view.getLocationOnScreen(location);
        int listContainerPosition = location[1];

        view.getChildAt(0).getLocationOnScreen(location);
        int currentLocation = location[1];

        int blah = listContainerPosition - currentLocation + offset;

        mSyncedView.scrollTo(0, blah);

    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }
}

