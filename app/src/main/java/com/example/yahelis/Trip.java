package com.example.yahelis;

public class Trip {
    private String information;
    private String dargatiul;
    private int day;
    private int month;
    private int year;
    private String photo;
    private int km;
    private int time;
    private String name;
    public Trip(String information, String dargatiul, int day, int month, int year, String photo, int km, int time, String name) {
        this.information = information;
        this.dargatiul = dargatiul;
        this.day = day;
        this.month = month;
        this.year = year;
        this.photo = photo;
        this.km = km;
        this.time = time;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trip() {
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getDargatiul() {
        return dargatiul;
    }

    public void setDargatiul(String dargatiul) {
        this.dargatiul = dargatiul;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }



}