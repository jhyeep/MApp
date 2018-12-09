package com.example.junhan.mapp;

import java.util.Date;

public class EventsItem {

    private int attendance;
    private String name;
    private String location;
    private String dateStart;
    private String dateEnd;
    private String desc;

    public EventsItem(String name, String dateStart, String dateEnd, String location, int attendance, String desc){

        this.name = name;
        this.dateStart = dateStart; //stores both date and time, refer to DataCollector for usage
        this.dateEnd = dateEnd;
        this.attendance = attendance;
        this.location = location;
        this.desc = desc;

    }

    public EventsItem(){}

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

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
