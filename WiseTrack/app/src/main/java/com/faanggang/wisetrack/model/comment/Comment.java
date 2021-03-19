package com.faanggang.wisetrack.model.comment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {
    private String experimentID;
    private String authorID;
    private String content;
    private Date datetime;
    private String firebaseID; // ID of the document in firebase

    public Comment(String eID, String aID, String cont, Date dt) {
        this.experimentID = eID;
        this.authorID = aID;
        this.content = cont;
        this.datetime = dt;
    }

    public String getExperimentID() {
        return experimentID;
    }

    public void setExperimentID(String experimentID) {
        this.experimentID = experimentID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    /**
     * This function converts the date type private attribute to a string for display.
     * @return Date and time that the comment was made.
     */
    public String getDateTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(this.datetime);
    }

    public String getFirebaseID() {
        return firebaseID;
    }
    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }
}
