package com.example.b07sportsballs;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;

public class Event {

    /*
    Concerns - does write to database need a ref parameter?
    Should we make the read from database have a string parameter with the unique identifier being
    the name? I bring this up again since my idea of the program goes like this:
        Venue is read, and displays a list of names. The program then uses those names and fetches
        the values from the database matching its key so it can be put into its hashset
    The Database reference is unneccary since we know the node we need to attach to - there is no
    pint forcing an implementtion on it, see interface segregation?
     */

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

    public void readFromDatabase(DatabaseReference ref)
    {

    }

    public void writeToDatabase()
    {
        writer.write(this);
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
