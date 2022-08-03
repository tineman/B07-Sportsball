package com.example.b07sportsballs;

import com.google.firebase.database.DatabaseReference;

import java.util.HashSet;

public abstract class User {
    private String username;
    private String password;
//    private UserBinder binder;
//    private UserWriter writer;
    DatabaseReference ref;

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

    public DatabaseReference getRef() {
        return ref;
    }

    public void setRef(DatabaseReference ref) {
        this.ref = ref;
    }

    public abstract void logIn();

    public HashSet<Venue> collectAllVenues() {
        return null;
    }

    public HashSet<Event> collectUpcomingEvents() {
        return null;
    }
}
