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

package com.cmput301w18t05.taskzilla.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cmput301w18t05.taskzilla.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {

    TabLayout tabs;
    ViewPager tabsContent;
    FragmentPagerAdapter tabsAdapter;

    public TasksFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_tasks,
                container, false);

        tabsAdapter = new TabsManager(this.getChildFragmentManager());
        tabsContent = mLinearLayout.findViewById(R.id.ProviderRequesterTabContent);
        tabsContent.setAdapter(tabsAdapter);

        tabs = mLinearLayout.findViewById(R.id.ProviderRequesterTab);
        tabs.setupWithViewPager(tabsContent);

        return mLinearLayout;
    }

    public class TabsManager extends FragmentPagerAdapter {
        TabsManager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TasksRequesterFragment();
                case 1:
                    return new TasksProviderFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Requester";
                case 1:
                    return "Provider";
                default:
                    return null;
            }
        }
    }
}
