package com.example.b07sportsballs;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class Event {

    private EventWriter writer;
    private EventBinder binder;
    private String name, host, location;
    private Date startTime, endTime;
    private int currPlayers, maxPlayers;
    private DatabaseReference ref; //the reference to the event's node

    //For persistent listener
    public Event()
    {

    }

    //For testing
    public Event(EventWriter writer, EventBinder binder, String name, String host, String location, Date startTime, Date endTime, int currPlayers, int maxPlayers, DatabaseReference ref) {
        this.writer = writer;
        this.binder = binder;
        this.name = name;
        this.host = host;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currPlayers = currPlayers;
        this.maxPlayers = maxPlayers;
        this.ref = ref;
    }

    /**
     * Attaches a persistent listener to ref and keeps event updated. Does not check if the event is
     * in the database beforehand, that is the calling function's responsibility
     * @param ref the reference to the event's node in Firebase
     * @param updater an implementation of a void() function. Called whenever event updates
     */
    public void bindToDatabase(DatabaseReference ref, EventBinder.Updater updater)
    {
        this.ref = ref;
        EventBinder binder = new EventBinder(this);
        this.binder = binder;
        this.binder.bind(this.ref, updater);
    }

    /**
     * Writes Event to database under /Venues/[specificvenue]/Event/this.name, Assumes the bindToDatabase
     * has been called on the event already
     */
    public void writeToDatabase()
    {
        writer.write(this.ref, this);
    }








    //Helper functions

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

    public void setCurrPlayers(int currPlayers) {
        this.currPlayers = currPlayers;
    }

    public void setBinder(EventBinder binder) {this.binder = binder;}

    //Sets name, host, location, starttime, endrime, currplayers, maxplayers to those in events
    public void setData(Event event)
    {
        this.name = event.getName();
        this.host = event.getHost();
        this.location = event.getLocation();
        this.startTime = event.getStartTime();
        this.endTime = event.getEndTime();
        this.currPlayers = event.getCurrPlayers();
        this.maxPlayers = event.getMaxPlayers();
    }
}
