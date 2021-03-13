package com.faanggang.wisetrack.comment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class CommentManager {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void UploadComment(Comment comment){
        CollectionReference collectionRef = db.collection("Comments");

        HashMap<String, Object> hm = new HashMap<String,Object>();
        hm.put("uID", comment.getAuthorID());
        hm.put("eID", comment.getExperimentID());
        hm.put("content", comment.getContent());
        hm.put("datetime", comment.getDatetime());
        hm.put("children", comment.getChildren());

        DocumentReference newCommentRef = collectionRef.document();
        newCommentRef.set(hm);
    }

}
