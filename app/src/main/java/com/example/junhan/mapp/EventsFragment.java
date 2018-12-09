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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


public class EventsFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.subsharedprefs";
    TreeMap<String, ArrayList<EventsItem>> sectioner;
    private ArrayList<EventsItem> eventList;
    SectionedRecyclerViewAdapter sectionAdapter;
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferences = getActivity().getSharedPreferences(sharedPrefFile, 0);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        eventList = new ArrayList<>();
        sectioner = new TreeMap<>();
        mRecyclerView = view.findViewById(R.id.recyclerView);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                SimpleDateFormat format = new SimpleDateFormat();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue().toString();
                    String location = ds.child("location").getValue().toString();
                    int attendance = ((Long) ds.child("attendance").getValue()).intValue();
                    String desc = ds.child("desc").getValue().toString();
                    String dateStart = ds.child("dateStart").getValue().toString();
                    String dateEnd = ds.child("dateEnd").getValue().toString();
                    EventsItem event = new EventsItem(name, dateStart, dateEnd, location, attendance, desc);
                    eventList.add(event);

                    try {
                        sectioner = DataCollector.getSortedData(eventList);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error creating section", Toast.LENGTH_SHORT).show();
                    }

                    sectionAdapter = new SectionedRecyclerViewAdapter();

                    final ArrayList<EventsItem> posTracker = new ArrayList<>();
                    String currentDate = new Date().toString();
                    SimpleDateFormat convDate = new SimpleDateFormat("dd/MM/yyyy"); //convDate.format
                    SimpleDateFormat strToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm"); //strToDate.parse
                    for (final String key : sectioner.keySet()) {
                        posTracker.add(new EventsItem(key, currentDate, currentDate, "", 0, ""));
                        for (EventsItem value : sectioner.get(key)) posTracker.add(value);
                        final HeaderRecyclerViewSection sec = new HeaderRecyclerViewSection(getActivity(), key, sectioner.get(key));
                        sectionAdapter.addSection(sec);
                        mRecyclerView.setAdapter(sectionAdapter);
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        sec.setOnItemClickListener(new HeaderRecyclerViewSection.onItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
//                    EventsItem selectedEvent = sec.eventList.get(position);
                                Intent intent = new Intent(getActivity(), EventDetails.class);
                                intent.putExtra("CURRENT_ITEM", posTracker.get(position).getName());
                                startActivity(intent);
                            }

                            @Override
                            public void onCheckClick(View itemView, int position) {
                                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                                ImageView image = itemView.findViewById(R.id.imageView);
                                TextView attendance = itemView.findViewById(R.id.textAttend);
                                DataPusher dp = new DataPusher();

                                // clicking on attending event
                                if (mPreferences.getString(posTracker.get(position).getName(), "") == "1") {
                                    Toast.makeText(getActivity(), "No longer attending event", Toast.LENGTH_SHORT).show();
                                    preferencesEditor.putString(posTracker.get(position).getName(), "0");
                                    image.setImageResource(R.drawable.ic_plus_blue);
                                    dp.updateAttendance(posTracker.get(position).getName(), posTracker.get(position).getAttendance() - 1);
                                }

                                //clicking on new event
                                else {
                                    Toast.makeText(getActivity(), "Marked event as attending", Toast.LENGTH_SHORT).show();
                                    preferencesEditor.putString(posTracker.get(position).getName(), "1");
                                    image.setImageResource(R.drawable.ic_check);
                                    dp.updateAttendance(posTracker.get(position).getName(), posTracker.get(position).getAttendance() + 1);
                                }
                                preferencesEditor.apply();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error syncing from database", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}