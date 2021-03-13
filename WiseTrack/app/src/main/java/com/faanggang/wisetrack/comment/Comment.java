package com.faanggang.wisetrack.comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Comment {
    private String experimentID;
    private String authorID;
    private String content;
    private Date datetime;



    private String firebaseID; // ID of the document in firebase
    private ArrayList<String> children; // list of IDs to refer to comment children

    public Comment(String eID, String aID, String cont, Date dt) {
        this.experimentID = eID;
        this.authorID = aID;
        this.content = cont;
        this.datetime = dt;
        this.children = new ArrayList<String>();
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

    public String getDatetimeString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this.datetime);
    }

    public ArrayList<String> getChildren() {
        return children;
    }
    public String getFirebaseID() {
        return firebaseID;
    }

    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }
}
