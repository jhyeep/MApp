package com.example.junhan.mapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: cleanup
public class EmailRegex {

    public static Map<String, String> all = new HashMap<String, String>(){{
        put("tt16","2.201");
        put("tt17", "2.202");
        put("tt18", "2.203");
        put("tt19", "2.306");
        put("tt20","2.305");
        put("tt21", "2.310");
        put("tt22", "2.311");
        put("tt23", "2.413");
        put("cc9", "2.307");
        put("cc10", "2.308");
        put("cc13", "2.506");
        put("cc14", "2.507");
        put("lt3", "2.403");
        put("auditorium", "2.101");
        put("audi", "2.302");
        put("it care", "2.204");
        put("office of information tech", "2.205");
        put("ISTD office", "2.206");
    }};

    public static Map<String, String> getLocations(){
        return all;
    }

    //todo get rid of this when app done
    public static void main(String args[]) throws ParseException {
        String test = "Dear Students,\n" +
                " \n" +
                "ISTD will hold a briefing session to share with you information about the new requirements for each track. Apart from the pillar curriculum, Prof Tony Quek (Acting HOP) will also share with you some new initiatives (e.g. labs, working space) in ISTD. No registration is required for this event, for those who are available and are keen to find out more about the above-mentioned items, please join us at the event with the details  below:\n" +
                " \n" +
                "\n" +
                "Date: 30 Dec 2018, Friday\n" +
                "Time: 2pm - 4pm\n" +
                "Venue: LT3\n" +
                "\n" +
                "\n" +
                "[Note: Fyi, an info session for the Freshmore students will be held between 4pm to 5pm in the same LT.]\n" +
                "\n" +
                "\n" +
                "\n" +
                "This email may contain confidential and/or proprietary information that is exempt from disclosure under applicable law and is intended for receipt and use solely by the addressee(s) named above. If you are not the intended recipient, you are notified that any use, dissemination, distribution, or copying of this email, or any attachment, is strictly prohibited. Please delete the email immediately and inform the sender. Thank You\n" +
                "The above message may contain confidential and/or proprietary information that is exempt from disclosure under applicable law and is intended for receipt and use solely by the addressee(s) named above. If you are not the intended recipient, you are hereby notified that any use, dissemination, distribution, or copying of this message, or any attachment, is strictly prohibited. If you have received this email in error, please inform the sender immediately by reply e-mail or telephone, reversing the charge if necessary. Please delete the message thereafter. Thank you. ";
        String tests = "Dear all,\n" +
                "\n" +
                "Science and Math Seminar\n" +
                "\n" +
                "Date: 30 Nov 2018, Friday\n" +
                "\n" +
                "Time: 11am – 1pm\n" +
                "\n" +
                "Venue: Think Tank 17  (Building 2 Level 2, 2.202)\n" +
                "\n";
        /*
        String[] date = dateChecker(tests);
        String[] venue = venueChecker(tests);
        String[] time = timeChecker(tests);
        System.out.print(date[0] + date[1] + "\n");
        System.out.print(venue[0] + venue[1]+ "\n");
        System.out.print(time[0] + time[1] + time[2]);
        */
        //System.out.print(regexChecker("(\\s)?[1]?[0-9]([pP]|[aA])[Mm](\\s)?(\\W)?(\\s)?[1]?[0-9]([pP]|[aA])[Mm](\\s)?", tests));
        /*
        for (String i: getInfo(tests)){
            System.out.print(i + "\n");
        }
        */
        getInfo(tests);
        SimpleDateFormat strtodate = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat convDate = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println(convDate.format(strtodate.parse("13 jan 2018")));
    }

    public static String[] getInfo(String str){
        String[] date = dateChecker(str);
        String[] venue = venueChecker(str);
        String[] time = timeChecker(str);

        System.out.println("out" + date[0] + time[0] + venue[0]);
        System.out.println("info " + date[1]+" "+venue[1]+" "+ time[1]+" "+ time[2] +" "+"\n");
        if (date[0] == "true" && time[0] == "true" && venue[0] == "true" ){
            String[] info = {date[1],venue[1], time[1], time[2]};
            return info;
        }
        else {
            return null;
        }
    }

    public static ArrayList<String> regexChecker(String theRegex, String str){
        Pattern checkRegex = Pattern.compile(theRegex);
        Matcher regexMatcher = checkRegex.matcher(str);

        ArrayList possible = new ArrayList<String>();
        while(regexMatcher.find()){
            if(regexMatcher.group().length() != 0 && !possible.contains(regexMatcher.group().trim())){
                possible.add(regexMatcher.group().trim());
                //System.out.print(regexMatcher.group().trim() + "\n");
            }
        }
        return possible;
    }

    public static String[] dateChecker(String str){
        // check 01 Jan 2018 format
        ArrayList<String> list1 = regexChecker("(\\s(0?[1-9]|[12][0-9]|[3][01])\\s[A-Za-z]{3,4}(\\s)([0-9]{2,4}))", str);
        // check 31/01/2012 format dd/mm/yyyy
        //ArrayList<String> list2 = regexChecker("(0?[1-9]|[12][0-9]|[3][01])/(0?[1-9]|1[012])(/(20)?\\d\\d)?", str);

        String valid = String.valueOf(list1.size() == 1);
        Date date = new Date();
        String Date = new String();

        if (list1.size() == 1){
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

            try {
                date = formatter.parse(formatter.format(formatter.parse(list1.get(0))));
                Date = formatter.format(date).toLowerCase();
            } catch (ParseException e) {
                valid = "false";
            }
        }

        String[] ans = {valid, Date};
        return ans;
    }

    public static String[] venueChecker(String str){

        ArrayList<String> tt = regexChecker("(\\s)?[Tt](hink)?(\\s)?[Tt](ank)?(\\s)?[0-9]{2,4}(\\s)?", str);
        ArrayList<String> cc = regexChecker("(\\W)?(([cC](ohort)?(\\s)?[Cc](lass)?)|([cC]ohort))(\\s)?[0-9]{1,2}(\\s)?", str);
        ArrayList<String> lt = regexChecker("(\\W)?([Ll](ecture)?(\\s)?[tT](heatre)?)(\\s)?[0-9]{1}(\\s)?", str);


        ArrayList<String> roomNum = regexChecker("[0-9]{1}.[0-9]{3}", str);
        //list1.addAll(tt);

        String valid = String.valueOf(tt.size() + cc.size() + lt.size() < 2 && roomNum.size() < 2);
        String num = "";
        if (tt.size() + cc.size() + lt.size() == 1 | roomNum.size() == 1 ){
            //System.out.println("ROOOM SIZE" + roomNum.size());
            if (roomNum.size() == 1 ){
                //System.out.println("ROOOM" + roomNum.get(0));
                if (all.containsValue(roomNum.get(0))){
                    num  = roomNum.get(0);
                } else {
                    valid = "false";
                }

            }
            if (tt.size() == 1){
                //System.out.println("tt");
                String place = tt.get(0).replaceAll("\\D+","");        // check if room num match room if yes carry on
                if (num != ""){
                    if (!all.get("tt" + place).equals(num)) {
                        valid = "false";
                    }
                } else {
                    num = all.get("tt" + place);
                    if (num == null) {
                        valid = "false";
                    }
                    // number = dictionary number
                }
            } else if (cc.size() == 1){
                //System.out.println("cc");
                String place = cc.get(0).replaceAll("\\D+","");
                if (num != ""){
                    if (!all.get("cc" + place).equals(num)) {
                        valid = "false";
                    }
                } else {
                    num = all.get("cc" + place);
                    if (num == null) {
                        valid = "false";
                    }
                    // number = dictionary number
                }
            } else if (lt.size() == 1){
                //System.out.println("lt");
                String place = lt.get(0).replaceAll("\\D+","");
                if (num != ""){
                    if (!all.get("lt" + place).equals(num)) {
                        valid = "false";
                    }
                } else {
                    num = all.get("lt" + place);
                    if (num == null) {
                        valid = "false";
                    }
                    // number = dictionary number
                }
            }

        }

        String[] ans = {valid, num};
        return ans;
    }

    public static String[] timeChecker(String str){
        ArrayList<String> list1 = regexChecker("(\\s)?[1]?[0-9]([pP]|[aA])[Mm](\\s)?(\\W)?(\\s)?[1]?[0-9]([pP]|[aA])[Mm](\\s)?", str);

        String valid = String.valueOf(list1.size() == 1);
        String begin = "";
        String finish = "";

        //System.out.println(list1.size());

        if (list1.size() == 1){
            //System.out.println("here");
            String[] timings = list1.get(0).split("\\W+");
            //System.out.println(timings[0] + timings[1]);
            double start = Double.valueOf(timings[0].replaceAll("\\D+","")) * 100;
            if(timings[0].contains("pm")){
                start += 1200;
            }
            begin = String.valueOf((int)start);
            double end = Double.valueOf(timings[1].replaceAll("\\D+","")) * 100;
            if(timings[1].contains("pm")){
                end += 1200;
            }
            finish = String.valueOf((int)end);
            valid = String.valueOf(end > start);
        }

        String[] ans = {valid, begin, finish};
        return ans;
    }
}
