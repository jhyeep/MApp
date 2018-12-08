package com.example.junhan.mapp;

import java.util.Date;

public class EventsItem {

    private int attendance;
    private String name;
    private String location;
    private Date dateStart;
    private Date dateEnd;
    private String desc;

    public EventsItem(String name, Date dateStart, Date dateEnd, String location, int attendance, String desc){

        this.name = name;
        this.dateStart = dateStart; //stores both date and time, refer to DataCollector for usage
        this.dateEnd = dateEnd;
        this.attendance = attendance;
        this.location = location;
        this.desc = desc;

    }

    public String getName(){
        return name;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public String getLocation() {
        return location;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getAttendance() {
        return attendance;
    }

    public String getDesc() {
        return desc;
    }
}
