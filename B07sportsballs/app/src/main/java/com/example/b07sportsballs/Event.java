package com.example.b07sportsballs;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class Event {

    //Note - all

    private EventWriter writer;
    private EventReader reader;
    private String name;
    private String host;
    private String location;
    private Date startTime;
    private Date endTime;
    private int currPlayers;
    private int maxPlayers;
    private DatabaseReference ref;

    //Note - reader, writer, and ref have no getters to avoid being written

    //For persistent listener
    public Event()
    {

    }

    public Event(EventWriter writer, EventReader reader, String name, String host, String location, Date startTime, Date endTime, int currPlayers, int maxPlayers, DatabaseReference ref) {
        this.writer = writer;
        this.reader = reader;
        this.name = name;
        this.host = host;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currPlayers = currPlayers;
        this.maxPlayers = maxPlayers;
        this.ref = ref;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public String getLocation() {
        return location;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getCurrPlayers() {
        return currPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    //Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setCurrPlayers(int currPlayers) {
        this.currPlayers = currPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

}
