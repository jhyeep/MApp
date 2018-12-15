package com.example.junhan.mapp;

public class EventsItem {

    /*
    Everything is self-explanatory and obvious, except for dateStart and dateEnd. Both the time and date are stored in one attribute,
    eg. "15/12/2018 12:30", which is why in all the code that needs to retrieve the date/time alone, you will see a lot of SimpleDateFormat
    usage where the String is converted to a Date and back to a String.
     */

    private int attendance;
    private String name;
    private String location;
    private String dateStart;
    private String dateEnd;
    private String desc;

    public EventsItem(String name, String dateStart, String dateEnd, String location, int attendance, String desc){

        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.attendance = attendance;
        this.location = location;
        this.desc = desc;

    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getDesc() {
        return desc;
    }
}
