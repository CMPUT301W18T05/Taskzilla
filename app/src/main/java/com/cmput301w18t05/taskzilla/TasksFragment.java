package com.cmput301w18t05.taskzilla;


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
