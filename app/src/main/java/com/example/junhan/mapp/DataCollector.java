package com.example.junhan.mapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeMap;

public class DataCollector {

    Date currentDate = new Date();
    public DataCollector(){ }

    // TODO: implement firebase
    // returns a list of ALL events (unsorted)
    public static ArrayList<EventsItem> getAllData() throws ParseException {
        ArrayList<EventsItem> list = new ArrayList<>();

        DateFormat strToDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date1 = strToDate.parse("10/12/2018 10:00");
        Date date2 = strToDate.parse("11/12/2018 18:00");

        list.add(new EventsItem("Event 1", date2, date2, "LT1", 10, "Event 1 details"));
        list.add(new EventsItem("Event 2", date1, date1, "CC14", 5, "Event 2 details"));
        list.add(new EventsItem("Event 3", date2, date2, "LT5", 123, "Event 3 details"));
        list.add(new EventsItem("Event 4", date2, date2, "LT2", 3, "Event 4 details"));

        return list;
    }

    // returns a list of UNEXPIRED events in order of date
    public static ArrayList<EventsItem> getData() throws ParseException {
        ArrayList<EventsItem> events = getAllData();
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

    // returns a map of events, categorized by a date key (String) and events list value (ArrayList<EventsItem>)
    public static TreeMap<String, ArrayList<EventsItem>> getSortedData() throws ParseException {

        DateFormat convDate = new SimpleDateFormat("dd/MM/yyyy");
        TreeMap<String, ArrayList<EventsItem>> sectioner = new TreeMap<>();
        ArrayList<EventsItem> eventList = getData();

        for (EventsItem event : eventList) {
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

        return sectioner;
    }
}
