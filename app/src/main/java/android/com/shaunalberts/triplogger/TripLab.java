package android.com.shaunalberts.triplogger;

import android.com.shaunalberts.triplogger.database.TripBaseHelper;
import android.com.shaunalberts.triplogger.database.TripCursorWrapper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.com.shaunalberts.triplogger.database.TripDBSchema.TripTable;
import android.os.Environment;

/**
 * Going to store a list of trips.  Using a Singleton class here, allows only one instance of
 * the class to exist at a time.  Used to get a list off all the trips, add, delete or update
 * trips.
 *
 * Shaun      29-September-2016        Initial
 * Shaun      04-September-2016        Added delete function
 */
public class TripLab {

    private static TripLab sTripLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //ContentValues maps each column with a value, used when inserting, updating
    private static ContentValues getContentValues(Trip trip) {
        ContentValues values = new ContentValues();
        values.put(TripTable.Cols.UUID, trip.getId().toString());
        values.put(TripTable.Cols.TITLE, trip.getTitle());
        values.put(TripTable.Cols.DATE, trip.getDate().getTime());
        values.put(TripTable.Cols.TRIP_TYPE, trip.getTripType());
        values.put(TripTable.Cols.DESTINATION, trip.getDestination());
        values.put(TripTable.Cols.DURATION, trip.getDuration());
        values.put(TripTable.Cols.COMMENT, trip.getComment());
        values.put(TripTable.Cols.GPS_LOCATION, trip.getGpsLoction());
        return values;
    }

    public static TripLab get(Context context) {
        if (sTripLab == null) {
            sTripLab = new TripLab(context);
        }
        return sTripLab;
    }

    //get the databases that we can connect to,  must still implement the update, etc functions
    private TripLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TripBaseHelper(mContext).getWritableDatabase();
    }

    //get a list of all the trips
    public List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();
        TripCursorWrapper cursor = queryTrip(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                trips.add(cursor.getTrip());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return trips;
    }

    //using a given id, get the trip record
    public Trip getTrip(UUID id) {
        TripCursorWrapper cursor = queryTrip(
          TripTable.Cols.UUID + " = ?", new String[] {id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getTrip();
        } finally {
            cursor.close();
        }
    }

    public void updateTrip(Trip trip) {
        String uuidString = trip.getId().toString();
        ContentValues values = getContentValues(trip);

        mDatabase.update(TripTable.NAME, values,
            TripTable.Cols.UUID + " = ?", new String[] {uuidString}
        );
    }

    public void addTrip(Trip trip) {
        ContentValues values = getContentValues(trip);
        mDatabase.insert(TripTable.NAME, null, values);
    }

    //Get file object pointing to correct location
    public File getPhotoFile(Trip trip) {
        File externalFilesDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //Verify that there is external storage
        if (externalFilesDir == null) {
            return null;
        }
        return new File(externalFilesDir, trip.getPhotoFilename());
    }

    //Performs a query, gets a cursor and wraps it with CursorWrapper.  This contains a getTrip()
    //method that opens up the cursor and returns a neat Trip object.
    private TripCursorWrapper queryTrip(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TripTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new TripCursorWrapper(cursor);
    }

    //Delete a record
    public void deleteTrip(Trip trip) {
        String uuisString = trip.getId().toString();
        mDatabase.delete(
                TripTable.NAME,
                TripTable.Cols.UUID + " = ?",
                new String[] {uuisString}
        );
    }

}
