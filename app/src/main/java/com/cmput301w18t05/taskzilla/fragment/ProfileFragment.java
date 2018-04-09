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

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cmput301w18t05.taskzilla.AppColors;
import com.cmput301w18t05.taskzilla.EmailAddress;
import com.cmput301w18t05.taskzilla.PhoneNumber;
import com.cmput301w18t05.taskzilla.Photo;
import com.cmput301w18t05.taskzilla.Review;
import com.cmput301w18t05.taskzilla.ReviewCustomAdapter;
import com.cmput301w18t05.taskzilla.Task;
import com.cmput301w18t05.taskzilla.activity.MainActivity;
import com.cmput301w18t05.taskzilla.activity.ZoomImageActivity;
import com.cmput301w18t05.taskzilla.R;
import com.cmput301w18t05.taskzilla.User;
import com.cmput301w18t05.taskzilla.activity.EditProfileActivity;
import com.cmput301w18t05.taskzilla.currentUser;
import com.cmput301w18t05.taskzilla.request.RequestManager;
import com.cmput301w18t05.taskzilla.request.command.AddUserRequest;
import com.cmput301w18t05.taskzilla.request.command.GetReviewsByUserIdRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByProviderUsernameRequest;
import com.cmput301w18t05.taskzilla.request.command.GetTasksByRequesterUsernameRequest;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * the fragment that represents the current user's profile
 * @author Colin, myapplestory
 */
public class ProfileFragment extends Fragment {
    private static final String FILENAME = "currentUser.sav";

    private ArrayList<Task> taskList;
    private GetTasksByRequesterUsernameRequest requestTasksRequester;
    private GetTasksByProviderUsernameRequest requestTasksProvider;
    private Integer tasksDone;

    private TextView nameField;
    private TextView emailField;
    private TextView phoneField;
    private TextView numRequestsField;
    private TextView numTasksDoneField;
    private TextView providerRatingField;
    private TextView requesterRatingField;
    private Button providerReviewButton;
    private Button requesterReviewButton;

    private String numRequests;
    private String numTasksDone;
    private Button logOut;
    private User user  = currentUser.getInstance();
    private ImageButton editProfile;
    private ImageView profilePicture;
    private AppColors appColors = AppColors.getInstance();

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile,
                container, false);
    }

    /**
     * Set fields in the fragment_profile.xml
     * using the currentUser object which stores
     * all necessary information of the current user that
     * is logged in
     *
     * @author Micheal-Nguyen
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        appColors = AppColors.getInstance();

        findViews(view);
        setValues();

        providerReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                providerRatingOnClick();
            }
        });
        requesterReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requesterRatingOnClick();
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfileClicked();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutClicked();
            }
        });
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfilePictureClicked();
            }
        });
    }

    // Taken from https://stackoverflow.com/questions/41655797/refresh-fragment-when-change-between-tabs?noredirect=1&lq=1
    // 2018-04-01
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
           // getActivity().getActionBar().setTitle("Profile");
            getView().setBackgroundColor(Color.parseColor(appColors.getActionBarColor()));
            taskList = new ArrayList<>();
            //gets all of current user's tasks
            requestTasksRequester = new GetTasksByRequesterUsernameRequest(user.getUsername());
            RequestManager.getInstance().invokeRequest(getContext(), requestTasksRequester);
            Integer tempNumRequests = requestTasksRequester.getResult().size();
            Integer numRequestsInteger = tempNumRequests;
            while(tempNumRequests>0){
                RequestManager.getInstance().invokeRequest(getContext(), requestTasksRequester);
                tempNumRequests = requestTasksRequester.getResult().size();
                numRequestsInteger+=tempNumRequests;
            }
            numRequests = Integer.toString(numRequestsInteger);

            requestTasksProvider = new GetTasksByProviderUsernameRequest(user.getUsername());
            RequestManager.getInstance().invokeRequest(getContext(), requestTasksProvider);
            this.taskList.addAll(requestTasksProvider.getResult());
            tasksDone = 0;
            for(Task task: taskList) {
                if(task.getStatus().equals("Completed")){
                    tasksDone++;
                }
            }
            while(taskList.size()>0 && taskList != null){
                taskList.clear();
                RequestManager.getInstance().invokeRequest(getContext(), requestTasksProvider);
                this.taskList.addAll(requestTasksProvider.getResult());
                for(Task task: taskList) {
                    if(task.getStatus().equals("Completed")){
                        tasksDone++;
                    }
                }
            }
            numTasksDone = Integer.toString(tasksDone);
            numRequestsField.setText(numRequests);
            numTasksDoneField.setText(numTasksDone);
        }
    }

    /**
     * findViews
     * sets each view variable as the respective ones on this fragment
     * @param view the current view
     * @author Micheal-Nguyen
     */
    public void findViews(View view){
        profilePicture = view.findViewById(R.id.profilePictureView);
        nameField = view.findViewById(R.id.nameField2);
        emailField = view.findViewById(R.id.emailField2);
        phoneField = view.findViewById(R.id.phoneField2);
        providerRatingField = view.findViewById(R.id.providerRatingField);
        requesterRatingField = view.findViewById(R.id.requesterRatingField);
        numRequestsField = view.findViewById(R.id.numRequestsField);
        numTasksDoneField = view.findViewById(R.id.numTasksDoneField);
        logOut = view.findViewById(R.id.logOutButton);
        editProfile = view.findViewById(R.id.editButton);
        requesterReviewButton = view.findViewById(R.id.ViewRequesterReviewsButton);
        providerReviewButton = view.findViewById(R.id.ViewProviderReviewsButton);
    }

    /**
     * setValues
     * sets the text and values of each field and variable in this class
     *
     * @author Micheal_Nguyen
     */
    public void setValues(){
        nameField.setText(user.getName());
        emailField.setText(user.getEmail().toString());
        phoneField.setText(user.getPhone().toString());
        if (user.getProviderRating() == 0.0f) {
            providerRatingField.setText("No rating");
            providerRatingField.setTextSize(18);
        } else {
            providerRatingField.setText(String.format(Locale.CANADA,
                    "%.1f", user.getProviderRating()));
        }
        if (user.getProviderRating() == 0.0f) {
            requesterRatingField.setText("No rating");
            requesterRatingField.setTextSize(18);
        } else {
            requesterRatingField.setText(String.format(Locale.CANADA,
                    "%.1f", user.getRequesterRating()));
        }
        try {
            profilePicture.setImageBitmap(user.getPhoto().StringToBitmap());
        }
        catch (Exception e){
            Photo defaultPhoto = new Photo("");
            profilePicture.setImageBitmap(defaultPhoto.StringToBitmap());
        }
    }

    /**
     * Switch to EditProfile Activity
     * Send users information to the activity
     */
    public void editProfileClicked() {
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        intent.putExtra("Name", user.getName());
        intent.putExtra("Email", user.getEmail().toString());
        intent.putExtra("Phone", user.getPhone().toString());
        if(user.getPhoto() == null){
            user.setPhoto(new Photo(""));
        }
        intent.putExtra("Photo", user.getPhoto().toString());
        startActivityForResult(intent, 1);
    }

    /**
     * When log out is clicked, app goes back to log in screen
     * The gson file is cleared to prevent auto login after logging out
     */
    public void logOutClicked() {
        try {
            FileOutputStream fos = getActivity().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(null, out);
            out.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * providerRatingOnClick
     * upon clicking the provider rating field
     * shows dialog with a list of the user's reviews as the provider
     * @author myapplestory
     */
    public void providerRatingOnClick() {
        final AlertDialog mBuilder = new AlertDialog.Builder(this.getContext()).create();
        final View mView = getLayoutInflater().inflate(R.layout.dialog_review_list,null);
        final ListView ReviewsListView = mView.findViewById(R.id.ReviewsListView);
        final TextView ReviewBannerTextView = mView.findViewById(R.id.ReviewsBannerTextView);

        GetReviewsByUserIdRequest request = new GetReviewsByUserIdRequest(user.getId());
        RequestManager.getInstance().invokeRequest(request);
        ArrayList<Review> ReviewsList = request.getResult();

        for (Review review : ReviewsList) {
            if (review.getReviewType().equals("r")) {
                ReviewsList.remove(review);
            }
        }

        if (ReviewsList.isEmpty()) {
            ArrayList<String> tempList = new ArrayList<>();
            tempList.add("No reviews yet :/");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                    android.R.layout.simple_list_item_1, tempList);
            ReviewsListView.setAdapter(adapter);
        } else {
            ArrayAdapter<Review> adapter = new ReviewCustomAdapter(this.getContext(),
                    R.layout.list_view_review, ReviewsList);
            ReviewsListView.setAdapter(adapter);
        }

        String text = "Reviews for " + currentUser.getInstance().getName() + " as a provider";
        ReviewBannerTextView.setText(text);

        mBuilder.setView(mView);
        mBuilder.show();
    }

    /**
     * requesterRatingOnClick
     * upon clicking the provider rating field
     * shows dialog with a list of the user's reviews as the  requester
     * @author myapplestory
     */
    public void requesterRatingOnClick() {
        final AlertDialog mBuilder = new AlertDialog.Builder(this.getContext()).create();
        final View mView = getLayoutInflater().inflate(R.layout.dialog_review_list,null);
        final ListView ReviewsListView = mView.findViewById(R.id.ReviewsListView);
        final TextView ReviewBannerTextView = mView.findViewById(R.id.ReviewsBannerTextView);

        GetReviewsByUserIdRequest request = new GetReviewsByUserIdRequest(user.getId());
        RequestManager.getInstance().invokeRequest(request);
        ArrayList<Review> ReviewsList = request.getResult();

        for (Review review : ReviewsList) {
            if (review.getReviewType().equals("p")) {
                ReviewsList.remove(review);
            }
        }

        if (ReviewsList.isEmpty()) {
            ArrayList<String> tempList = new ArrayList<>();
            tempList.add("No reviews yet :/");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(),
                    android.R.layout.simple_list_item_1, tempList);
            ReviewsListView.setAdapter(adapter);
        } else {
            ArrayAdapter<Review> adapter = new ReviewCustomAdapter(this.getContext(),
                    R.layout.list_view_review, ReviewsList);
            ReviewsListView.setAdapter(adapter);
        }

        String text = "Reviews for " + currentUser.getInstance().getName() + " as a requester";
        ReviewBannerTextView.setText(text);

        mBuilder.setView(mView);
        mBuilder.show();
    }

    /**
     * set the user to be the profile fragment, should be the user that is currently logged in
     *
     * @param user that is currently logged in
     */
    public void setUser(User user) {
        this.user = user;
    }

    public void ProfilePictureClicked(){
        Intent intent = new Intent(getActivity(),ZoomImageActivity.class);
        intent.putExtra("Photo", user.getPhoto().toString());
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("does this work", String.valueOf(requestCode));
        if(requestCode == 1) {
            //code to add to ESC
            if (resultCode == RESULT_OK) {
                String newName = data.getStringExtra("Name");
                String newEmail = data.getStringExtra("Email");
                String newPhone = data.getStringExtra("Phone");
                String newPhoto = data.getStringExtra("Photo");
                user.setName(newName);
                user.setEmail(new EmailAddress(newEmail));
                user.setPhone(new PhoneNumber(newPhone));
                user.setPhoto(new Photo(newPhoto));
                AddUserRequest request = new AddUserRequest(user);
                RequestManager.getInstance().invokeRequest(getContext(), request);
                nameField.setText(newName);
                emailField.setText(newEmail);
                phoneField.setText(newPhone);
                profilePicture.setImageBitmap(user.getPhoto().StringToBitmap());
            }
        }
    }
}
