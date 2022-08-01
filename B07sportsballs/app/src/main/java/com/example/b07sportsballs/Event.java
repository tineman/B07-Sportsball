package com.example.b07sportsballs;

import java.util.Date;

public class Event {
    String name, owner, venue;
    Date startTime, endTime;
    int currPlayers, maxPlayers;

    public Event(String name, String owner, String venue, Date startTime, Date endTime,
                 int currPlayers, int maxPlayers) {
        this.name = name;
        this.owner = owner;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currPlayers = currPlayers;
        this.maxPlayers = maxPlayers;
    }

}
