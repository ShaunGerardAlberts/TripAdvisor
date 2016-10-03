package android.com.shaunalberts.triplogger;

import android.com.shaunalberts.triplogger.database.TripCursorWrapper;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.com.shaunalberts.triplogger.database.TripDBSchema.SettingsTable;

/**
 * Will hold the users profile information that will be displayed in the SettingActivity.  This
 * class will get and set those values as wel as allow a user to insert and update the corresponding
 * table.
 *
 * Shaun      02-October-2016          Initial
 *
 */
public class Setting {

    private String studentName;
    private int idNum;
    private String email;
    private String gender;
    private String comment;

    private SQLiteDatabase mDatabase;

    //May not be the best idea to leave this being a static class
    private static ContentValues getContentValues(Setting setting) {
        ContentValues value = new ContentValues();
        value.put(SettingsTable.Cols.NAME, setting.getStudentName());
        value.put(SettingsTable.Cols.ID, setting.getIdNum());
        value.put(SettingsTable.Cols.EMAIL, setting.getEmail());
        value.put(SettingsTable.Cols.GENDER, setting.getGender());
        value.put(SettingsTable.Cols.COMMENT, setting.getComment());
        return value;
    }
    
    public Setting() {

    }

    public Setting(String name, int id, String gender, String comment) {
        this.studentName = name;
        this.idNum = id;
        this.gender = gender;
        this.comment = comment;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getIdNum() {
        return idNum;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //CRUD functionality, database work here...

    //return the user profile
    //update the user profile
    //insert the user profile
    //insert or update user profile
    public void updateOrInsertUserProfile(Setting setting) {
        String curName = setting.getStudentName();
        int curId = setting.getIdNum();
        String curEmail = setting.getEmail();
        String curGender = setting.getGender();
        String curComment = setting.getComment();

        if (curName == null && curEmail == null && curGender == null || curComment == null) {
            //insert
            ContentValues values = getContentValues(setting);
            mDatabase.insert(SettingsTable.NAME, null, values);
        } else {
            //update
            ContentValues values = getContentValues(setting);
            mDatabase.update(SettingsTable.NAME, values, null, null);
        }
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
                SettingsTable.NAME,
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

















