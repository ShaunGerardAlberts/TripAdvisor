package android.com.shaunalberts.triplogger.database;

/**
 * Shaun      29-September-2016        Initial
 * Shaun      03-October-2016          Added UUID to SettingsTable
 */
public class TripDBSchema {

    public static final class TripTable {
        public static final String NAME = "trips";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String TRIP_TYPE = "tripType";
            public static final String DESTINATION = "destination";
            public static final String DURATION = "duration";
            public static final String COMMENT = "comment";
            public static final String GPS_LOCATION = "gpsLocation";
        }
    }

    public static final class SettingsTable {
        public static final String NAME = "settings";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String ID = "id";
            public static final String EMAIL = "email";
            public static final String GENDER = "gender";
            public static final String COMMENT = "comment";
        }
    }
}
