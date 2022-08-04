package com.example.b07sportsballs;

import com.google.firebase.database.DatabaseReference;

import java.util.HashSet;

public abstract class User {
    protected String username;
    protected String password;
//    private UserBinder binder;
//    private UserWriter writer;
    protected DatabaseReference ref;

    // Default constructor for reading from database.
    public User() {}

    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void logIn(final Updater listener);

    public HashSet<Venue> collectAllVenues() {
        return null;
    }

    public HashSet<Event> collectUpcomingEvents() {
        return null;
    }
}
