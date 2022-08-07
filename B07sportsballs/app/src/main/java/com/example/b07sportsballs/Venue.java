package com.example.b07sportsballs;


import android.util.Log;

import com.google.firebase.database.DatabaseReference;


import java.util.HashSet;

public class Venue
{
        private String name;
        private HashSet<Event> events;
        private DatabaseReference ref;
        private VenueWriter venuewriter;
        private VenueReader venuereader;
        public static HashSet<String> allVenues;

        //Empty constructor
        public Venue() { }

        public Venue(String name)
        {
                this.name = name;
        }

        //returns the name of the venue
        public String getName()
        {
                return name;
        }

        // Sets name of venue
        public void setName(String name) { this.name = name; }

        //returns a HashSet of the events that will take place in the given venue
        public HashSet<Event> getEvents()
        {
                return events;
        }

        public void setReader(VenueReader venuereader)
        {
                this.venuereader = venuereader;
        }

        public void setWriter(VenueWriter venuewriter)
        {
                this.venuewriter = venuewriter;
        }

        public void setEvents(HashSet<Event> events)
        {
                this.events = events;
        }

        //read the venues stored in the database
        public void readFromDataBase(DatabaseReference ref)
        {
                setReader(new VenueReader());
                VenueReader.ref = ref.child("Root").child("Venues");
                venuereader.read(ref);
        }

        //create a new venue in the database
        public void writeToDataBase(DatabaseReference ref) {
                setWriter(new VenueWriter());
                venuewriter.write(ref,this);
        }

        //return a HashSet of all venues that exist
        public static HashSet<String> getAllVenues()
        {
                if (!VenueReader.isRunning)
                {
                        Log.i("demo", "allVenues: " + allVenues);
                }
                return allVenues;
        }
}


