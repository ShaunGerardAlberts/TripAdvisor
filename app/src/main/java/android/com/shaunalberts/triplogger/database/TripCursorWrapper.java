package android.com.shaunalberts.triplogger.database;

import android.com.shaunalberts.triplogger.Trip;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.com.shaunalberts.triplogger.database.TripDBSchema.TripTable;

import java.util.Date;
import java.util.UUID;

/**
 * Given a cursor, wrap it with CursorWrapper, and create the getTrip() to return a neat trip
 * object.
 *
 *
 * Shaun      29-September-2016        Initial
 */
public class TripCursorWrapper extends CursorWrapper{

    public TripCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Trip getTrip() {
        String uuidString = getString(getColumnIndex(TripTable.Cols.UUID));
        String title = getString(getColumnIndex(TripTable.Cols.TITLE));
        long date = getLong(getColumnIndex(TripTable.Cols.DATE));
        String tripType = getString(getColumnIndex(TripTable.Cols.TRIP_TYPE));
        String destination = getString(getColumnIndex(TripTable.Cols.DESTINATION));
        int duration = getInt(getColumnIndex(TripTable.Cols.DURATION));
        String comment = getString(getColumnIndex(TripTable.Cols.COMMENT));
        String gpsLocation = getString(getColumnIndex(TripTable.Cols.GPS_LOCATION));

        Trip trip = new Trip(UUID.fromString(uuidString));
        trip.setTitle(title);
        trip.setDate(new Date(date));
        trip.setTripType(tripType);
        trip.setDestination(destination);
        trip.setDuration(duration);
        trip.setComment(comment);
        trip.setGpsLocation(gpsLocation);

        return trip;
    }
}
