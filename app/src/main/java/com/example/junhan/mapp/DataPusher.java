package com.example.junhan.mapp;

import android.support.annotation.NonNull;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

//TODO: *** class for pushing events to firebase ***
// remember to initialize an instance of the class before using its methods
public class DataPusher {

    FirebaseDatabase database;
    DatabaseReference myRef;

    public DataPusher() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

    // sends new event to firebase, input: EventsItem
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

    // sends new event to firebase, input: bunch of attributes
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
