package com.example.junhan.mapp;

// self-explanatory class for an event object
public class EventsItem {

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
