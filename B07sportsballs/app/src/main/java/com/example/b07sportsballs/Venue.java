package com.example.b07sportsballs;

import com.google.firebase.database.DatabaseReference;

import java.util.HashSet;

public class Venue
{
        public String name;
        //uncomment the line below once Event class has been created
        //public HashSet<Event> events;
        public DatabaseReference ref;
        public VenueWriter venuewriter;
        public VenueReader venuereader;

        public Venue()
        {
                this.name = "";
                this.venuewriter = new VenueWriter();
                this.venuereader = new VenueReader();
        }

        public Venue(String name)
        {
                this.name = name;
                this.venuewriter = new VenueWriter();
                this.venuereader = new VenueReader();
        }

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        //public HashSet<Event> getEvents() {
        //        return events;
        //}

        public void setReader(VenueReader venuereader) {
                this.venuereader = venuereader;
        }

        public void setWriter(VenueWriter venuewriter) {
                this.venuewriter = venuewriter;
        }

        //public void setEvents(HashSet<Event> events) {
        //        this.events = events;
        //}

        public void readFromDataBase(DatabaseReference ref)
        {
                VenueReader reader = new VenueReader();
                reader.read(ref);
        }

        public void writeToDataBase(DatabaseReference ref) {
                VenueWriter writer = new VenueWriter();
                writer.write(ref);
        }
}
