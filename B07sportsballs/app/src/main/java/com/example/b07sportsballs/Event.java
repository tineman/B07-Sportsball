package com.example.b07sportsballs;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class Event {

    private EventWriter writer;
    private EventBinder binder;
    private String name, host, location;
    private Date startTime, endTime;
    private int currPlayers, maxPlayers;
    //ref is the reference to the node containing the event
    private DatabaseReference ref;

    public Event()
    {

    }

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
     *
     * Update function is called on Event update even if the activity is switched
     *
     * @param ref the reference to the event's node in Firebase
     * @param onUpdate an implementation of a void() function. Called whenever event updates
     */
    public void bindToDatabase(DatabaseReference ref, EventBinder.Updater onUpdate)
    {
        this.ref = ref;
        EventBinder binder = new EventBinder(this);
        this.binder = binder;
        this.binder.bind(this.ref, onUpdate);
    }

    /**
     * Updates the onUpdate function called when event updates. Assumes Event has already been bound
     * to a reference.
     *
     * @param onUpdate an implementation of a void() function. Called whenever event updates
     */
    public void changeOnUpdate(EventBinder.Updater onUpdate)
    {
        this.binder.update(this.ref, onUpdate);
    }

    /**
     * Writes Event to database under /Venues/[specificvenue]/Event/this.name, Assumes the bindToDatabase
     * has been called on the event already
     */
    public void writeToDatabase()
    {
        writer.write(this.ref, this);
    }

    /**
     * Checks if the event is at capacity. If so, returns false. If not, increment the currPlayer by one
     * and return true. Assumes that bindToDatabase has been called on event already
     * @return The success value of the operation
     */
    public boolean increment()
    {
        if(currPlayers >= maxPlayers) return false;

        currPlayers += 1;
        writer.increment(this.ref);
        return true;

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
