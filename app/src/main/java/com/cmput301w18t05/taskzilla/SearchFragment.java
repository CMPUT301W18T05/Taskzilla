package com.cmput301w18t05.taskzilla;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private SearchView searchField;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ConstraintLayout mConstraintLayout = (ConstraintLayout) inflater.inflate(R.layout.fragment_search,
                container, false);

        ImageButton mButton = (ImageButton) mConstraintLayout.findViewById(R.id.MapButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMap();
            }
        });

        return mConstraintLayout;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // expand search bar by default
        searchField = view.findViewById(R.id.searchView);
        searchField.setIconified(false);
    }

    public void viewMap(){
        Intent intent = new Intent(getActivity(), MapActivity.class);
        startActivity(intent);
    }
}
