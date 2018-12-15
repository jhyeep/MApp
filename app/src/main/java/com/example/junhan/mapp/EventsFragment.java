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

        // Gets event details from firebase
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { //updates on data change
                eventList.clear();

                // firebase data are event attributes only, we put these attributes in a new event and stores the created event in eventList
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.child("name").getValue().toString();
                    String location = ds.child("location").getValue().toString();
                    int attendance = ((Long) ds.child("attendance").getValue()).intValue();
                    String desc = ds.child("desc").getValue().toString();
                    String dateStart = ds.child("dateStart").getValue().toString();
                    String dateEnd = ds.child("dateEnd").getValue().toString();
                    EventsItem event = new EventsItem(name, dateStart, dateEnd, location, attendance, desc);
                    eventList.add(event);

                    /* getSortedData changes the arraylist of events into a sorted hashmap of dates with keys being the list of
                    events (sorted by date) that occur on that date */
                    try {
                        sectioner = DataCollector.getSortedData(eventList);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error creating section", Toast.LENGTH_SHORT).show();
                    }

                    sectionAdapter = new SectionedRecyclerViewAdapter();

                    final ArrayList<EventsItem> posTracker = new ArrayList<>();
                    String currentDate = new Date().toString();
                    final SimpleDateFormat convDate = new SimpleDateFormat("dd/MM/yyyy"); //convDate.format()
                    final SimpleDateFormat convTime = new SimpleDateFormat("HH:mm"); //convTime.format()
                    final SimpleDateFormat strToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm"); //strToDate.parse()

                    for (final String key : sectioner.keySet()) { //remember sectioner is the hashmap of dates with value being an arraylist of events

                        /*
                        When user clicks on a card, the code uses the position of the clicked item to determine what event it is.
                        However, while this position number is inaccurate as it includes the header as well. Hence the posTracker
                        which is a list of sorted events. Actions can then refer to particular events by using posTracker.get[position].
                         */
                        posTracker.add(new EventsItem(key, currentDate, currentDate, "", 0, ""));
                        for (EventsItem value : sectioner.get(key)) posTracker.add(value); //for event in arraylist of events, add it to posTracker

                        final HeaderRecyclerViewSection sec = new HeaderRecyclerViewSection(getActivity(), key, sectioner.get(key));
                        sectionAdapter.addSection(sec);
                        mRecyclerView.setAdapter(sectionAdapter);
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        // on user click, send intent to and creates the EventDetails activity
                        sec.setOnItemClickListener(new HeaderRecyclerViewSection.onItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(getActivity(), EventDetails.class);
                                intent.putExtra("NAME", posTracker.get(position).getName());
                                intent.putExtra("LOCATION", posTracker.get(position).getLocation());
                                intent.putExtra("DETAILS", posTracker.get(position).getDesc());
                                try {
                                    intent.putExtra("DATE", convDate.format(strToDate.parse(posTracker.get(position).getDateStart())));
                                    intent.putExtra("TIME", convTime.format(strToDate.parse(posTracker.get(position).getDateStart())) + " - " + convTime.format(strToDate.parse(posTracker.get(position).getDateEnd())));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                            }

                            /*
                            When user clicks on the 'mark attendance' button (the plus sign), it changes to a tick and the
                            fact that he has marked the event is saved in shared preferences so he won't need to mark it
                            again when the app is closed and opened again.

                            Attendance of the event is updated by +1 to firebase,.
                             */
                            @Override
                            public void onCheckClick(View itemView, int position) {
                                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                                ImageView image = itemView.findViewById(R.id.imageView);
                                DataPusher dp = new DataPusher();

                                // clicking on attending event
                                if (mPreferences.getString(posTracker.get(position).getName(), "") == "1") {
                                    Toast.makeText(getActivity(), "No longer attending event", Toast.LENGTH_SHORT).show();
                                    preferencesEditor.putString(posTracker.get(position).getName(), "0");
                                    image.setImageResource(R.drawable.ic_plus_blue);
                                    dp.updateAttendance(posTracker.get(position).getName(), posTracker.get(position).getAttendance() - 1);
                                }

                                //clicking on new unattending event
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