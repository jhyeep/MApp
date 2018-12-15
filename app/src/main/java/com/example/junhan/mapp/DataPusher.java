package com.example.junhan.mapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;


// class for pushing events to firebase
public class DataPusher {

    FirebaseDatabase database;
    DatabaseReference myRef;

    public DataPusher() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    // sends new event to firebase, input: EventsItem
    /*
    NOTE: dateStart and dateEnd are
     */
    public void sendData(EventsItem event){
        String name = event.getName();
        String dateStart = event.getDateStart();
        String dateEnd = event.getDateEnd();
        String location = event.getLocation();
        int attendance = event.getAttendance();
        String desc = event.getDesc();

        myRef.child(name).child("name").setValue(name);
        myRef.child(name).child("dateStart").setValue(dateStart);
        myRef.child(name).child("dateEnd").setValue(dateEnd);
        myRef.child(name).child("location").setValue(location);
        myRef.child(name).child("attendance").setValue(attendance);
        myRef.child(name).child("desc").setValue(desc);
    }

    // sends new event to firebase, input: bunch of attributes of String type
    public void sendData(String name, String dateStart, String dateEnd, String location, int attendance, String desc){
        myRef.child(name).child("name").setValue(name);
        myRef.child(name).child("dateStart").setValue(dateStart);
        myRef.child(name).child("dateEnd").setValue(dateEnd);
        myRef.child(name).child("location").setValue(location);
        myRef.child(name).child("attendance").setValue(attendance);
        myRef.child(name).child("desc").setValue(desc);
    }

    // updates ATTENDANCE of existing event, input: name of event and new value
    public void updateAttendance(String eventName, int value){
        Map<String, Object> updates = new HashMap<>();
        String key = eventName + "/" + "attendance";
        updates.put(key, value);
        myRef.updateChildren(updates);
    }

}
