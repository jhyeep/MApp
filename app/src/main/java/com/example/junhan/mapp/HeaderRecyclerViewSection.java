package com.example.junhan.mapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class HeaderRecyclerViewSection extends StatelessSection{
    private static final String TAG = HeaderRecyclerViewSection.class.getSimpleName();
    private String title;
    Context mContext;
    ArrayList<EventsItem> mExampleList;
    public HeaderRecyclerViewSection(String title, ArrayList<EventsItem> list) {
        super(R.layout.events_header, R.layout.event_item);
        this.title = title;
        this.mExampleList = list;
    }
    @Override
    public int getContentItemsTotal() {
        return mExampleList.size();
    }
    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder iHolder = (ItemViewHolder)holder;
        EventsItem currentItem = mExampleList.get(position);

        DateFormat convDate = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat convTime = new SimpleDateFormat("HH:mm");

        iHolder.mTextViewAttend.setText(Integer.toString(currentItem.getAttendance()));
        iHolder.mTextView1.setText(currentItem.getName());
        iHolder.mTextView2.setText(convTime.format(currentItem.getDateStart()) + " - " + convTime.format(currentItem.getDateEnd())
        + "   |   " + currentItem.getLocation());
    }
    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder hHolder = (HeaderViewHolder)holder;
        hHolder.headerTitle.setText(title);
    }
}