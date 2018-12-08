package com.example.junhan.mapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

// TODO: make activity_event_details.xml a proper display screen

public class EventDetails extends AppCompatActivity {
    TextView eventDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();

        eventDetails = findViewById(R.id.eventDetails);
        String blah = intent.getStringExtra("CURRENT_ITEM");
        eventDetails.setText(blah);
    }


}

