package com.example.junhan.mapp;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeMap;

public class DataCollector {

    FirebaseDatabase database;
    DatabaseReference myRef;

    private static ArrayList<EventsItem> eventsList;

    public DataCollector(){
    }


    // returns a list of ALL events (unsorted)
//    public static ArrayList<EventsItem> getAllData() throws ParseException {
//        ArrayList<EventsItem> list = new ArrayList<>();
//
//        DateFormat strToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        Date date1 = strToDate.parse("10/12/2018 10:00");
//        Date date2 = strToDate.parse("11/12/2018 18:00");
//
//        list.add(new EventsItem("Event 1", "11/12/2018 18:00", "11/12/2018 18:00", "LT1", 10, "Event 1 details"));
//        list.add(new EventsItem("Event 2", "11/12/2018 18:00", "11/12/2018 18:00", "CC14", 5, "Event 2 details"));
//        list.add(new EventsItem("Event 3", "11/12/2018 18:00", "11/12/2018 18:00", "LT5", 123, "Event 3 details"));
//        list.add(new EventsItem("Event 4", "10/12/2018 10:00", "10/12/2018 10:00", "LT2", 3, "Event 4 details"));
//
//        return list;
//    }


    // filters out expired events
    public static ArrayList<EventsItem> getData(ArrayList<EventsItem> list) throws ParseException {
        final SimpleDateFormat strToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ArrayList<EventsItem> events = list;
        Collections.sort(events, new Comparator<EventsItem>() {
            @Override
            public int compare(EventsItem o1, EventsItem o2) {
                try {
                    return strToDate.parse(o1.getDateStart()).compareTo(strToDate.parse(o2.getDateStart()));
                } catch (ParseException e) {
                    return 0;
                }
            }
        });

        ArrayList<EventsItem> keptEvents = new ArrayList<>();
        Date currentDate = new Date();

        for (EventsItem event : events) {
            if (currentDate.before(strToDate.parse(event.getDateEnd()))){
                keptEvents.add(event);
            }
        }
        return keptEvents;
    }


    // returns a map of unexpired events, categorized by a date key (String) and events list value (ArrayList<EventsItem>)
    public static TreeMap<String, ArrayList<EventsItem>> getSortedData(ArrayList<EventsItem> list) throws ParseException {

        SimpleDateFormat convDate = new SimpleDateFormat("dd/MM/yyyy"); //convTime.format()
        SimpleDateFormat strToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm"); //strToDate.parse()

        TreeMap<String, ArrayList<EventsItem>> section = new TreeMap<>();
        ArrayList<EventsItem> eventList = getData(list);

        for (EventsItem event : eventList) {
            String dateStr = convDate.format(strToDate.parse(event.getDateStart()));
            if (!section.containsKey(dateStr)){
                ArrayList<EventsItem> newList = new ArrayList<>();
                newList.add(event);
                section.put(dateStr,newList);
            } else {
                ArrayList<EventsItem> newList = section.get(dateStr);
                newList.add(event);
                section.put(dateStr,newList);
            }
        }

        return section;
    }
}
