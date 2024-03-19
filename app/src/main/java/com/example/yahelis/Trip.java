package com.example.yahelis;

import android.graphics.Bitmap;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Trip {


    private String ownerName;
    private String ownerEmail;

    private ArrayList<String> participantsNames=new ArrayList<>();

    private ArrayList<String> participantsEmails = new ArrayList<>();

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public ArrayList<String> getParticipantsNames() {
        return participantsNames;
    }
    public ArrayList<String> addParticipantsNames(String ownerName) {
         participantsNames.add(ownerName);
         return participantsNames;
    }


    public void setParticipantsNames(ArrayList<String> participantsNames) {
        this.participantsNames = participantsNames;
    }

    public ArrayList<String> getParticipantsEmails() {
        return participantsEmails;
    }
    public ArrayList<String> addParticipantsEmails(String email) {
          participantsEmails.add(email);
          return participantsEmails;
    }

    public void setParticipantsEmails(ArrayList<String> participantsEmails) {
        this.participantsEmails = participantsEmails;
    }

    private String tripID;
    private String information;
    private String dargatiul;
    private String date;
    private String photo;
    private int km;
    private int time;
    private String name;
    private String place;
    private String area;
    private int totalTravelers;

    public Trip(String ownerName, String ownerEmail, ArrayList<String> participantsNames, ArrayList<String> participantsEmails, String tripID, String information, String dargatiul, String date, String photo, int km, int time, String name, String place, String area, int totalTravelers) {
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.participantsNames = participantsNames;
        this.participantsEmails = participantsEmails;
        this.tripID = tripID;
        this.information = information;
        this.dargatiul = dargatiul;
        this.date = date;
        this.photo = photo;
        this.km = km;
        this.time = time;
        this.name = name;
        this.place = place;
        this.area = area;
        this.totalTravelers = totalTravelers;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getTotalTravelers() {
        return totalTravelers;
    }

    public void setTotalTravelers(int totalTravelers) {
        this.totalTravelers = totalTravelers;
    }

    public Trip(){

    }


    public Trip(String information, String dargatiul, String date, String photo, int km, int time, String name,String place, String area, int totalTravelers) {
        this.information = information;
        this.dargatiul = dargatiul;
        this.date = date;
        this.photo = photo;
        this.km = km;
        this.time = time;
        this.name = name;
        this.place = place;
        this.area = area;
        this.totalTravelers = totalTravelers;
    }


    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDargatiul() {
        return dargatiul;
    }

    public void setDargatiul(String dargatiul) {
        this.dargatiul = dargatiul;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}