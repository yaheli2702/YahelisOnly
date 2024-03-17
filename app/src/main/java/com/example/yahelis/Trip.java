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

    public void setParticipantsNames(ArrayList<String> participantsNames) {
        this.participantsNames = participantsNames;
    }

    public ArrayList<String> getParticipantsEmails() {
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


    public Trip(){

    }

    public Trip(String tripID, String information, String dargatiul, String date, String photo, int km, int time, String name) {
        this.tripID = tripID;
        this.information = information;
        this.dargatiul = dargatiul;
        this.date = date;
        this.photo = photo;
        this.km = km;
        this.time = time;
        this.name = name;
    }

    public Trip(String information, String dargatiul, String date, String photo, int km, int time, String name) {
        this.information = information;
        this.dargatiul = dargatiul;
        this.date = date;
        this.photo = photo;
        this.km = km;
        this.time = time;
        this.name = name;
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