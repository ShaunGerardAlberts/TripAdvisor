package android.com.shaunalberts.triplogger.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.com.shaunalberts.triplogger.database.TripDBSchema.TripTable;
import android.com.shaunalberts.triplogger.database.TripDBSchema.SettingsTable;

/**
 * Shaun      29-September-2016        Initial
 */
public class TripBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "tripBase.db";

    public TripBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create the trips table
        db.execSQL("create table " + TripTable.NAME + "(" +
                " _id integer primary key autoincrement, (" +
                TripTable.Cols.UUID + ", " +
                TripTable.Cols.TITLE + ", " +
                TripTable.Cols.DATE + ", " +
                TripTable.Cols.TRIP_TYPE + ", " +
                TripTable.Cols.DESTINATION + ", " +
                TripTable.Cols.DURATION + ", " +
                TripTable.Cols.COMMENT + ", " +
                TripTable.Cols.GPS_LOCATION +
                ")"
        );
        //Create the settings table
        db.execSQL("create table " + SettingsTable.NAME + "(" +
                SettingsTable.Cols.ID + ", " +
                SettingsTable.Cols.NAME + ", " +
                SettingsTable.Cols.EMAIL + ", " +
                SettingsTable.Cols.GENDER + ", " +
                SettingsTable.Cols.COMMENT +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
