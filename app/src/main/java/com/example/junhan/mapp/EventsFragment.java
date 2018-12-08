package com.example.junhan.mapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

import static android.content.Context.MODE_PRIVATE;


public class EventsFragment extends Fragment {

    TreeMap<String, ArrayList<EventsItem>> sectioner;
    private String sharedPrefFile = "com.example.android.mainsharedprefs";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        try {
            sectioner = DataCollector.getSortedData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        final ArrayList<EventsItem> posTracker = new ArrayList<>();
        Date currentDate = new Date();
        for (final String key : sectioner.keySet()) {
            posTracker.add(new EventsItem(key, currentDate, currentDate, "", 0, ""));
            for (EventsItem value : sectioner.get(key)) posTracker.add(value);

            final HeaderRecyclerViewSection sec = new HeaderRecyclerViewSection(key, sectioner.get(key));
            sectionAdapter.addSection(sec);
            sec.setOnItemClickListener(new HeaderRecyclerViewSection.onItemClickListener() {
                @Override
                public void onItemClick(int position) {
//                    EventsItem selectedEvent = sec.eventList.get(position);
                    Intent intent = new Intent(getActivity(), EventDetails.class);
                    intent.putExtra("CURRENT_ITEM", posTracker.get(position).getDesc());
                    startActivity(intent);
                }

                //TODO: make attendance +1 to event in firebase
                //TODO: make plus icon change to tick icon
                //TODO: make tick icon stay as tick icon even after app is closed (sharedpref things)
                @Override
                public void onCheckClick(int position) {
                    Toast.makeText(getActivity(), "Marked event as attending.", Toast.LENGTH_SHORT).show();
//                    HeaderRecyclerViewSection.attendCheck(position);

                }
            });
        }

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(sectionAdapter);


        return view;
    }

}
