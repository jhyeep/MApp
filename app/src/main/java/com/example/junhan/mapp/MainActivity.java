package com.example.junhan.mapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.mainsharedprefs";
    private DrawerLayout drawer;
    TextView profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);


        //start pushing events from email
        emailSend();

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        profileName = findViewById(R.id.profileName);
//        profileName.setText(mPreferences.getString("savedName", ""));

        //Sidebar
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_map);
        }
//        profileName = findViewById(R.id.profileName);
//        profileName.setText(mPreferences.getString("savedName", ""));
    }

    //Sidebar menu items
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_map:
                setTitle("mApp");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();
                break;
            case R.id.nav_events:
                setTitle("Events");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EventsFragment()).commit();
                break;
//            case R.id.nav_friends:
//                setTitle("Friends");
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FriendsFragment()).commit();
//                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    //Toolbar items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    //TODO: make refresh button do something (or just remove this)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_event:
                Intent intent = new Intent(this, CreateEvent.class);
                startActivity(intent);
                return true;
            //more cases if needed
        }
        return super.onOptionsItemSelected(item);
    }

    public void emailSend() {
        DataPusher dp = new DataPusher();
        String username = mPreferences.getString("savedName", "");
        String password = mPreferences.getString("savedPassword", "");
        HashMap<String, String[]> fetched = FetchingEmail.getEmails(username, password);
        if (fetched == null) {
            Toast.makeText(this, "Error fetching emails", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Fetching new events...", Toast.LENGTH_SHORT).show();
            SimpleDateFormat strtodate = new SimpleDateFormat("dd MMM yyyy");
            SimpleDateFormat convDate = new SimpleDateFormat("dd/MM/yyyy");
            for (String event : fetched.keySet()) {
                String name = event;
                String location = fetched.get(event)[3];
                String desc = fetched.get(event)[4];
                String dateStart = "";
                String dateEnd = "";
                try {
                    dateStart = convDate.format(strtodate.parse(fetched.get(event)[0])) + " " + fetched.get(event)[1];
                    dateEnd = convDate.format(strtodate.parse(fetched.get(event)[0])) + " " + fetched.get(event)[2];
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dp.sendData(name, dateStart, dateEnd, location, 0, desc);
                Toast.makeText(this, "Sent to FB", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
