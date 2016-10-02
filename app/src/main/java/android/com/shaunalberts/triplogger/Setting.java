package android.com.shaunalberts.triplogger;

/**
 * Shaun      02-October-2016          Initial
 */
public class Setting {

    private String studentName;
    private int idNum;
    private String email;
    private String gender;
    private String comment;
    
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
}
