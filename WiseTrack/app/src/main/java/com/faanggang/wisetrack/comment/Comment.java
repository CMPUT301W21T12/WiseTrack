package com.faanggang.wisetrack.comment;

public abstract class Comment {
    private String experimentID;
    private String authorID;
    private String content;

    Comment(String eID, String aID, String cont) {
        this.experimentID = eID;
        this.authorID = aID;
        this.content = cont;
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
}
