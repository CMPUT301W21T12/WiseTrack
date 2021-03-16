package com.faanggang.wisetrack.comment;

import java.util.Date;

public class Response extends Comment {
    private Comment parent;
    Response(String eID, String uID, String username, String cont, Comment parent, Date dt) {
        super(eID, username, uID, cont,dt);
        this.parent = parent;
    }

    public Response(String eID, String uID, String username, String cont, Date dt) {
        super(eID, username, uID, cont ,dt);
    }

    public Comment getParent() {
        return this.parent;
    }

    public void setParent(Comment p) {
        this.parent = p;
    }
}
