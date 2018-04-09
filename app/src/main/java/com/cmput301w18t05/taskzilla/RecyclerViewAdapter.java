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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by kio22 on 2018-04-06.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    ArrayList<Photo> photoList;
    Context context;
    View view;
    ViewHolder viewHolder;
    private CustomOnItemClick listener;


    public RecyclerViewAdapter(Context context,ArrayList<Photo> photoList, CustomOnItemClick listener){
        this.listener = listener;
        this.photoList = photoList;
        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;

        public ViewHolder(View v, final CustomOnItemClick listener){

            super(v);

            // taken from https://stackoverflow.com/questions/33264042/recyclerview-how-to-catch-the-onclick-on-an-imageview
            // 2018-04-07
            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(listener != null)
                        listener.onColumnClicked(getAdapterPosition());
                }
            });



            imageView = (ImageView) v.findViewById(R.id.ImageView);
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view = LayoutInflater.from(context).inflate(R.layout.recyclerview_items,parent,false);
        viewHolder = new ViewHolder(view, listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.imageView.setImageBitmap(photoList.get(position).StringToBitmap());

    }

    @Override
    public int getItemCount(){

        return photoList.size();
    }
}