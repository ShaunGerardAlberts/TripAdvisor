package android.com.shaunalberts.triplogger.database;

import android.com.shaunalberts.triplogger.Setting;
import android.com.shaunalberts.triplogger.Trip;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.com.shaunalberts.triplogger.database.TripDBSchema.TripTable;
import android.com.shaunalberts.triplogger.database.TripDBSchema.SettingsTable;
import android.location.Location;

import java.util.Date;
import java.util.UUID;

/**
 * Given a cursor, wrap it with CursorWrapper, and create the getTrip() to return a neat trip
 * object.
 *
 *
 * Shaun      29-September-2016        Initial
 * Shaun      02-October-2016          Added getSetting() to return a Setting object from database
 * Shaun      03-October-2016          Added UUID to SettingsTable
 * Shaun      04-October-2016          Make duration a String, make inserting easier
 *
 */

public class TripCursorWrapper extends CursorWrapper{

    public TripCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Trip getTrip() {
        String uuidString = getString(getColumnIndex(TripTable.Cols.UUID));
        String title = getString(getColumnIndex(TripTable.Cols.TITLE));
        long date = getLong(getColumnIndex(TripTable.Cols.DATE));
        int tripType = getInt(getColumnIndex(TripTable.Cols.TRIP_TYPE));
        String destination = getString(getColumnIndex(TripTable.Cols.DESTINATION));
        String duration = getString(getColumnIndex(TripTable.Cols.DURATION));
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

    //For the User Profile(Settings), SettingsActivity
    public Setting getSetting() {
        String uuidString = getString(getColumnIndex(SettingsTable.Cols.UUID));
        String name = getString(getColumnIndex(SettingsTable.Cols.NAME));
        String id = getString(getColumnIndex(SettingsTable.Cols.ID));
        String email = getString(getColumnIndex(SettingsTable.Cols.EMAIL));
        String gender = getString(getColumnIndex(SettingsTable.Cols.GENDER));
        String comment = getString(getColumnIndex(SettingsTable.Cols.COMMENT));

        Setting setting = new Setting();
        setting.setStudentName(name);
        setting.setIdNum(id);
        setting.setEmail(email);
        setting.setGender(gender);
        setting.setComment(comment);

        return setting;

    }

}
