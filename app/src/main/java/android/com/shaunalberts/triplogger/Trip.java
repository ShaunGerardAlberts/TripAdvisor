package android.com.shaunalberts.triplogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Shaun      29-September-2016        Initial
 * Shaun      01-October-2016          Changed mTripType to int
 */
public class Trip {

    private UUID mId;
    private String title;
    private Date mDate;
    private int mTripType;//represents a int value from a list
    private String mDestination;
    private String mDuration;//assume this is in whole days
    private String mComment;
    private String mGpsLocation;

    //Constructor
    public Trip() {
        this(UUID.randomUUID());
    }

    public Trip(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getPhotoFilename() {
        return "IMG" + getId().toString() + ".jpg";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public int getTripType() {
        return mTripType;
    }

    public void setTripType(int tripType) {
        this.mTripType = tripType;
    }

    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String destination) {
        this.mDestination = destination;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        this.mDuration = duration;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        this.mComment = comment;
    }

    public String getGpsLoction() {
        return mGpsLocation;
    }

    public void setGpsLocation(String gpsLocation) {
        this.mGpsLocation = gpsLocation;
    }
}
