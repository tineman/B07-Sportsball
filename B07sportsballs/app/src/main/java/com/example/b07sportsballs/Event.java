package com.example.b07sportsballs;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.Objects;
import java.io.Serializable;

public class Event implements Serializable{

    private transient EventWriter writer;
    private transient EventBinder binder;
    private String name, host, location;
    private Date startTime, endTime; //Java class deprecated, causing setter warning
    private int currPlayers, maxPlayers;
    //ref is the reference to the node containing the event
    private transient DatabaseReference ref;

    /**
     * When using the empty constructor, call bindToDatabase afterwards to initialise the values and
     * the reference.
     * Call setWriter() before calling writeToDatabase() or increment()
     */

    public Event()
    {

    }

    //For Testing
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
    public void bindToDatabase(DatabaseReference ref, Updater onUpdate)
    {
        if (this.ref == null) {
            this.ref = ref;
            EventBinder binder = new EventBinder(this);
            this.binder = binder;
            this.binder.bind(this.ref, onUpdate);
        }
    }

    /**
     * Updates the onUpdate function called when event updates. Assumes Event has already been bound
     * to a reference.
     *
     * @param onUpdate an implementation of a void() function. Called whenever event updates
     */
    public void changeOnUpdate(Updater onUpdate)
    {
        this.binder.update(this.ref, onUpdate);
    }

    /**
     * Call setWriter on an event before writing to it
     */
    public void setWriter()
    {
        if (this.writer == null) this.writer = new EventWriter();
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

    /**
     * An event is defined as equalling another event when it has the same name and location
     * This is to ensure only one event at a venue can have a certain name
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return this.name.equals(event.getName()) && this.location.equals(event.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name) + Objects.hash(location);
    }





    //Helper functions

    //Getters & setters
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

    public DatabaseReference collectRef() {
        return ref;
    }

    public void setCurrPlayers(int currPlayers) {
        this.currPlayers = currPlayers;
    }

    public void setBinder(EventBinder binder) {this.binder = binder;}

    //Sets name, host, location, starttime, endtime, currplayers, maxplayers to those in events
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
