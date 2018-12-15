package com.example.junhan.mapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

// activity for event details page (when user clicks on card in events list
public class EventDetails extends AppCompatActivity {
    TextView eventDetails;
    TextView eventTime;
    TextView eventTitle;
    TextView eventDate;
    TextView eventLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        setTitle("Event Details");
        eventDetails = findViewById(R.id.eventDetails);
        eventDate = findViewById(R.id.eventDate);
        eventTime = findViewById(R.id.eventTime);
        eventTitle = findViewById(R.id.eventTitle);
        eventLocation = findViewById(R.id.eventLocation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // gets intent from EventFragment and sets text fields
        Intent intent = getIntent();
        String details = intent.getStringExtra("DETAILS");
        String time = intent.getStringExtra("TIME");
        String location = intent.getStringExtra("LOCATION");
        String date = intent.getStringExtra("DATE");
        String title = intent.getStringExtra("NAME");

        eventTime.setText(time);
        eventDetails.setText(details);
        eventLocation.setText(location);
        eventDate.setText(date);
        eventTitle.setText(title);
    }
}

