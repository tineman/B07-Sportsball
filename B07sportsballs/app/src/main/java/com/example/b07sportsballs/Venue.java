package com.example.b07sportsballs;


import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashSet;
import java.util.Objects;

public class Venue
{
        private String name = "";
        private HashSet<Event> events;
        private DatabaseReference ref;
        private VenueWriter venuewriter;
        private VenueReader venuereader;
        public static HashSet<String> allVenues = new HashSet<String>();
        //hashsets of all events for the given venue
        public HashSet<String> allEvents = new HashSet<String>();

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
                getAllEvents();
                setReader(new VenueReader());
                VenueReader.ref = ref.child(Constants.DATABASE.ROOT).child(Constants.DATABASE.VENUE_PATH);
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
                return allVenues;
        }

        //read database to store all of the event names for the given venue in a hashset
        public void getAllEvents()
        {
                if (!name.equals(null) && !name.equals(""))
                {
                        VenueGetEventsReader eventsReader = new VenueGetEventsReader();
                        DatabaseReference ref2 = FirebaseDatabase.getInstance(Constants.DATABASE.DB_URL).getReference();
                        ref2 = ref2.child(Constants.DATABASE.ROOT).child(Constants.DATABASE.VENUE_PATH).
                                child(name).child(Constants.DATABASE.VENUE_EVENTS_KEY);
                        eventsReader.read(ref2, this);
                }
        }

        //store all of the event names for the venue in allEvents
        public void storeAllEvents(HashSet<String> keys)
        {
                this.allEvents = keys;
        }



        @Override
        public boolean equals(Object obj) {
                if (this == obj)
                {
                        return true;
                }
                if (obj == null)
                {
                        return false;
                }
                if (this.getClass() != obj.getClass())
                {
                        return false;
                }
                Venue venue = (Venue) obj;
                return venue.getName().equals(name) && venue.getEvents().equals(events);
        }

        @Override
        public int hashCode() {
                return Objects.hash(name, events);
        }


}


