package com.faanggang.wisetrack.comment;

public class Response extends Comment {
    private Comment respondee;
    Response(String eID, String aID, String cont, Comment respondee) {
        super(eID, aID, cont);
        this.respondee = respondee;
    }

    public Comment getRespondee() {
        return respondee;
    }

    public void setRespondee(Comment respondee) {
        this.respondee = respondee;
    }
}
