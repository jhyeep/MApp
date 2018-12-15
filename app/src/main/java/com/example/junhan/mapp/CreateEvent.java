package com.example.junhan.mapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//TODO: clean up
// Create event activity where user submits event's details to firebase
public class CreateEvent extends AppCompatActivity {
    EditText title;
    AutoCompleteTextView loc;
    AutoCompleteTextView start;
    AutoCompleteTextView end;
    AutoCompleteTextView date;
    AutoCompleteTextView month;
    AutoCompleteTextView year;
    EditText details;
    Button createEventButton;

    List<String> locationArray = getLocationsList();
    List<String> startArray = getTimeList();
    List<String> dateArray = getDate();
    List<String> monArray = Arrays.asList("Jan","Feb","Mar",
            "Apr","May","Jun",
            "Jul","Aug","Sep",
            "Oct","Nov","Dec");
    List<String> yearArray = getYear();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        setTitle("Create Event");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title = findViewById(R.id.titlename);
        loc = findViewById(R.id.location);
        start = findViewById(R.id.timeStart);
        end = findViewById(R.id.timeEnd);
        date = findViewById(R.id.date);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        details = findViewById(R.id.details);
        createEventButton = findViewById(R.id.create_event_button);

        details.setSelection(0);

        makeDropDown(locationArray, loc);
        makeDropDown(startArray, start);
        makeDropDown(startArray, end);
        makeDropDown(dateArray, date);
        makeDropDown(monArray, month);
        makeDropDown(yearArray, year);

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    CreateEventInfo();
                    finish();
                }

            }
        });
    }

    public boolean validate(){
        boolean ans = false;

        String userDate = date.getText().toString() +" " + month.getText().toString() + " "+year.getText().toString();

        if (title.getText().toString().length() == 0){
            Toast.makeText(this, "Please enter an event name", Toast.LENGTH_SHORT).show();
        } else if (!locationArray.contains(loc.getText().toString())){
            Toast.makeText(this, "Please enter valid LOCATION", Toast.LENGTH_SHORT).show();
        } else if (!dateArray.contains(date.getText().toString()) | !monArray.contains(month.getText().toString())| !yearArray.contains(year.getText().toString())){
            Toast.makeText(this, "Please enter valid DATE", Toast.LENGTH_SHORT).show();
        }else if (datePassed()){
            Toast.makeText(this, "Please ensure Date is valid", Toast.LENGTH_SHORT).show();
        } else if (!startArray.contains(start.getText().toString()) | !startArray.contains(end.getText().toString())){
            Toast.makeText(this, "Please enter valid TIMING", Toast.LENGTH_SHORT).show();
        } else if (Integer.valueOf(start.getText().toString()) > Integer.valueOf(end.getText().toString())){
            Toast.makeText(this, "Please ensure start time before end time", Toast.LENGTH_SHORT).show();
        } else if (details.getText().toString().length() == 0){
            Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show();
        } else {
            ans = true;
        }

        // todo check if date passed
        return ans;
    }

    public void CreateEventInfo(){

        // getting date time
        String mon = Integer.toString(monArray.indexOf(month.getText().toString()) + 1);
        if (mon.length() == 1){
            mon = "0"+mon;
        }
        String dat = date.getText().toString();
        if (dat.length() == 1){
            dat = "0"+dat;
        }
        String yea = year.getText().toString();
        String fullday = dat +"/" + mon +"/"+ yea;
        String startT = start.getText().toString().substring(0, 2) +":"+ start.getText().toString().substring(2);
        String endT = end.getText().toString().substring(0, 2) +":"+ end.getText().toString().substring(2);


        String name = title.getText().toString();
        String dateStart = fullday + " " + startT;
        String dateEnd = fullday + " " + endT;
        String location = loc.getText().toString().split(", ")[1];
        String det = details.getText().toString();

        DataPusher dp = new DataPusher();
        dp.sendData(name, dateStart, dateEnd, location, 0, det);
    }

    public boolean datePassed(){
        boolean ans = true;

        if (Integer.valueOf(year.getText().toString()) > Calendar.getInstance().get(Calendar.YEAR)){
            ans = false;
        } else if(monArray.indexOf(month.getText().toString()) > Calendar.getInstance().get(Calendar.MONTH) ){
            ans = false;
        } else if(Integer.valueOf(date.getText().toString()) >= Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
            ans = false;
        }
        return ans;

    }
    public List<String> getLocationsList(){
        Map<String, String> aLocations =  EmailRegex.getLocations();
        List<String> locationArray = new ArrayList<String>();


        Iterator it = aLocations.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String key = pair.getKey().toString();
            key = key.replace("tt", "TT");
            key = key.replace("cc", "CC");
            key = key.replace("lt", "LT");
            /*
            if (key.length() < 5 ){
                key.substring(0,2).toUpperCase();
            }
            System.out.println(key);
            */
            String val = pair.getValue().toString();
            locationArray.add(key + ", " + val );
            //it.remove(); // avoids a ConcurrentModificationException
        }
        return locationArray;
    }

    public List<String> getTimeList(){
        List<String> timeArray = new ArrayList<String>();

        String first;
        for(int i=0;i<=23;i++) {
            if (i < 10){
                first = "0" + Integer.toString(i);
            }
            else {
                first = Integer.toString(i);
            }

            timeArray.add(first+"00");
            timeArray.add(first+"30");
        }
        return timeArray;
    }

    public List<String> getDate(){
        List<String> dateArray = new ArrayList<String>();

        String first;
        for(int i=0;i<=31;i++) {
            if (i < 10){
                first = Integer.toString(i);
            }
            else {
                first = Integer.toString(i);
            }

            dateArray.add(first);

        }
        return dateArray;
    }

    public List<String> getYear(){
        List<String> dateArray = new ArrayList<String>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        for(int i=0;i<=2;i++) {
            dateArray.add(Integer.toString(year + i));
        }
        return dateArray;
    }

    public void makeDropDown(List<String> array, AutoCompleteTextView ref){
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, array);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv = ref;
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }

}