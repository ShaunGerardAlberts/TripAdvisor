package android.com.shaunalberts.triplogger;

/**
 * Will hold the users profile information that will be displayed in the SettingActivity.  This
 * class will get and set those values as wel as allow a user to insert and update the corresponding
 * table.
 *
 * Shaun      02-October-2016          Initial
 * Shaun      03-October-2016          Separated the database related work out this class
 * Shaun      28-October-2016          Renamed variables to conform to the mVar standard
 *
 */
public class Setting {

    private String mId;
    private String mStudentName;
    private String mIdNum;
    private String mEmail;
    private String mGender;
    private String mComment;

    private static final String SETTING_INDEX = "1";

    //constructor
    public Setting() {
        this(SETTING_INDEX);
    }

    public Setting(String mId) {
        this.mId = mId;
    }

    public String getId() {
        return mId;
    }

    public String getStudentName() {
        return mStudentName;
    }

    public void setStudentName(String mStudentName) {
        this.mStudentName = mStudentName;
    }

    public String getIdNum() {
        return mIdNum;
    }

    public void setIdNum(String mIdNum) {
        this.mIdNum = mIdNum;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String mComment) {
        this.mComment = mComment;
    }


}

















