package com.example.b07sportsballs;

public class Constants {
    //TODO: Update the values when database is finalized.

    public class DATABASE {
        /* Relative path to direct branches of root. */
        public final static String DB_URL = "https://sportsballtesting-default-rtdb.firebaseio.com";
        public final static String CUSTOMER_PATH = "Customers";
        public final static String ADMIN_PATH = "Admins";
        public final static String VENUE_PATH = "Venues";

        /* Admin and Customer keys. */
        public final static String USERNAME_KEY = "name";
        public final static String PASSWORD_KEY = "password";
        public final static String CUSTOMER_JOINED_EVENTS_KEY = "joinedEvents";
        public final static String CUSTOMER_SCHEDULED_EVENTS_KEY = "scheduledEvents";
        public final static String ADMIN_CREATED_VENUES_KEY = "venues";

        /* Venue keys. */
        public final static String VENUE_NAME_KEY = "name";
        public final static String VENUE_EVENTS_KEY = "events";

        /* Event keys. */
        public final static String EVENT_NAME_KEY = "name";
        public final static String EVENT_HOST_KEY = "host";
        public final static String EVENT_START_TIME_KEY = "startTime";
        public final static String EVENT_END_TIME_KEY = "endTime";
        public final static String EVENT_CURR_PLAYERS_KEY = "currPlayers";
        public final static String EVENT_MAX_PLAYERS_KEY = "maxPlayers";
    }

    public class LOGIN_CODE {
        public final static int SUCCESS = 1;
        public final static int WRONG_PASSWORD = 0;
        public final static int NO_USER = 2;
    }

    public class SIGNUP_CODE {
        public final static int SUCCESS = 1;
    }
}