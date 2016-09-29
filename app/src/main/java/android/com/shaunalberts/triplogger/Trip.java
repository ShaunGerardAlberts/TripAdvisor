package android.com.shaunalberts.triplogger;

import java.util.Date;
import java.util.UUID;

/**
 * Shaun      29-September-2016        Initial
 */
public class Trip {

    private UUID mId;
    private String title;
    private Date mDate;
    private String mTripType;
    private String mDestination;
    private int mDuration;//assume this is in whole days
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

    public String getTripType() {
        return mTripType;
    }

    public void setTripType(String tripType) {
        this.mTripType = tripType;
    }

    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String destination) {
        this.mDestination = destination;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
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
