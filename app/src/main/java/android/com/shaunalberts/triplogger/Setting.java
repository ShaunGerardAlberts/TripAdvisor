package android.com.shaunalberts.triplogger;

/**
 * Will hold the users profile information that will be displayed in the SettingActivity.  This
 * class will get and set those values as wel as allow a user to insert and update the corresponding
 * table.
 *
 * Shaun      02-October-2016          Initial
 * Shaun      03-October-2016          Separated the database related work out this class
 *
 */
public class Setting {

    private String mId;
    private String studentName;
    private String idNum;
    private String email;
    private String gender;
    private String comment;

    private static final String SETTING_INDEX = "1";

    //constructor
    public Setting() {
        this(SETTING_INDEX);
    }

    public Setting(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
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


}

















