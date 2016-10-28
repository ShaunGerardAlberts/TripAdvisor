package android.com.shaunalberts.triplogger;

import android.com.shaunalberts.triplogger.database.TripBaseHelper;
import android.com.shaunalberts.triplogger.database.TripCursorWrapper;
import android.com.shaunalberts.triplogger.database.TripDBSchema;
import android.com.shaunalberts.triplogger.database.TripDBSchema.SettingsTable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * Singleton class.  Can to either create or return this class.  Then used to read, insert or
 * update the Setting.
 *
 * Shaun      03-October-2016          Initial - tried to include this in Setting but became too complicated
 */
public class SettingLab {

    private static SettingLab sSettingLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static ContentValues getContentValues(Setting setting) {
        ContentValues value = new ContentValues();
        value.put(SettingsTable.Cols.UUID, "1");
        value.put(SettingsTable.Cols.NAME, setting.getStudentName());
        value.put(SettingsTable.Cols.ID, setting.getIdNum());
        value.put(SettingsTable.Cols.EMAIL, setting.getEmail());
        value.put(SettingsTable.Cols.GENDER, setting.getGender());
        value.put(SettingsTable.Cols.COMMENT, setting.getComment());
        return value;
    }

    public static SettingLab get(Context context) {
        if (sSettingLab == null) {
            sSettingLab = new SettingLab(context);
        }
        return sSettingLab;
    }

    private SettingLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TripBaseHelper(mContext).getWritableDatabase();
    }

    //insert settings
    public void addSetting(Setting s) {
        ContentValues contentValues = SettingLab.getContentValues(s);
        mDatabase.insert(SettingsTable.NAME, null, contentValues);
    }

    //get user profile
    public Setting getSetting(String id) {
        TripCursorWrapper cursor = querySettings(
                SettingsTable.Cols.UUID + " = ?", new String[] {"1"}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getSetting();
        } finally {
            cursor.close();
        }
    }

    //update settings
    public void updateSetting(Setting setting) {
        String uuidString = setting.getId();
        ContentValues values = getContentValues(setting);

        mDatabase.update(SettingsTable.NAME, values,
                SettingsTable.Cols.UUID + " =?", new String[]{uuidString});
    }

    public Setting getSetting() {
        TripCursorWrapper cursor = querySettings(null, null);
        Setting setting = null;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                setting = cursor.getSetting();
            }
        } finally {
            cursor.close();
        }

        return setting;
    }

    //Performs a query, gets a cursor and wraps it with CursorWrapper.  This contains a getTrip()
    //method that opens up the cursor and returns a neat Trip object.
    private TripCursorWrapper querySettings(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TripDBSchema.SettingsTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new TripCursorWrapper(cursor);
    }
}
