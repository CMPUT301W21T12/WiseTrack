package com.faanggang.wisetrack.comment;

import java.util.Date;

public class Response extends Comment {
    private Comment parent;
    Response(String eID, String aID, String cont, Comment parent, Date dt) {
        super(eID, aID, cont,dt);
        this.parent = parent;
    }

    public Comment getParent() {
        return this.parent;
    }

    public void setParent(Comment p) {
        this.parent = p;
    }
}
