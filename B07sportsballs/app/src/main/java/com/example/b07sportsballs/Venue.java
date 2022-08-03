package com.example.b07sportsballs;
import com.google.firebase.database.DatabaseReference;
import java.util.HashSet;

public class Venue
{
        public String name;
        public HashSet<Event> events;
        private DatabaseReference ref;
        private VenueWriter venuewriter;
        private VenueReader venuereader;

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
                VenueReader reader = new VenueReader();
                reader.read(ref);
        }

        //creates a new venue in the database
        public void writeToDataBase(DatabaseReference ref) {
                VenueWriter writer = new VenueWriter();
                writer.write(ref,this);
        }
}
