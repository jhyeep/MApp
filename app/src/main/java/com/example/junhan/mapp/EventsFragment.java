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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class EventsFragment extends Fragment {

    ArrayList<EventsItem> exampleList = new ArrayList<>();

    private ArrayList<EventsItem> getData() throws ParseException {
        ArrayList<EventsItem> exampleList = new ArrayList<>();

        DateFormat strToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date1 = strToDate.parse("10/12/2018 10:00");
        Date date2 = strToDate.parse("11/12/2018 18:00");

        exampleList.add(new EventsItem("Event 1", date2, date2, "LT1", 0, ""));
        exampleList.add(new EventsItem("Event 2", date1, date1, "CC14", 0, ""));
        exampleList.add(new EventsItem("Event 3", date2, date2, "LT5", 0, ""));
        exampleList.add(new EventsItem("Event 1", date2, date2, "LT2", 0, ""));

        return exampleList;
    }

    private ArrayList<EventsItem> compareData() throws ParseException {
        ArrayList<EventsItem> events = this.getData();
        Collections.sort(events, new Comparator<EventsItem>() {
            @Override
            public int compare(EventsItem o1, EventsItem o2) {
                return o1.getDateStart().compareTo(o2.getDateStart());
            }
        });

        ArrayList<EventsItem> keptEvents = new ArrayList<>();
        Date currentDate = new Date();
        for (EventsItem event : events) {
            if (currentDate.before(event.getDateEnd())){
                keptEvents.add(event);
            }
        }
        return keptEvents;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            exampleList = this.compareData();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        DateFormat convDate = new SimpleDateFormat("dd/MM/yyyy");
        TreeMap<String, ArrayList<EventsItem>> sectioner = new TreeMap<>();
        for (EventsItem event : exampleList) {
            String dateStr = convDate.format(event.getDateStart());
            if (!sectioner.containsKey(dateStr)){
                ArrayList<EventsItem> newList = new ArrayList<>();
                newList.add(event);
                sectioner.put(dateStr,newList);
            } else {
                ArrayList<EventsItem> newList = sectioner.get(dateStr);
                newList.add(event);
                sectioner.put(dateStr,newList);
            }
        }

//        HeaderRecyclerViewSection firstSection = new HeaderRecyclerViewSection("First Section", exampleList);
//        HeaderRecyclerViewSection secondSection = new HeaderRecyclerViewSection("Second Section", exampleList);
//        HeaderRecyclerViewSection thirdSection = new HeaderRecyclerViewSection("Third Section", exampleList);

        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();

        for (String key : sectioner.keySet()) {
            sectionAdapter.addSection(new HeaderRecyclerViewSection(key, sectioner.get(key)));
        }
//        sectionAdapter.addSection(firstSection);
//        sectionAdapter.addSection(secondSection);
//        sectionAdapter.addSection(thirdSection);

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(sectionAdapter);

//        EventsAdapter mAdapter = new EventsAdapter(getContext(), exampleList);
//        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

}
