package com.example.junhan.mapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.TreeMap;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class EventsFragment extends Fragment {

    ArrayList<EventsItem> eventList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            eventList = DataCollector.getData();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        TreeMap<String, ArrayList<EventsItem>> sectioner = null;
        try {
            sectioner = DataCollector.getSortedData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();
        for (String key : sectioner.keySet()) {
            sectionAdapter.addSection(new HeaderRecyclerViewSection(key, sectioner.get(key)));
        }

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(sectionAdapter);

        return view;
    }

}
