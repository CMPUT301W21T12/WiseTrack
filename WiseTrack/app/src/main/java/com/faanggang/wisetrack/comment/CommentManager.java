package com.faanggang.wisetrack.comment;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CommentManager {
    private Searcher searcher;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface Searcher{
        void onExpCommentsFound(ArrayList<Comment> results);
    }

    public CommentManager(Searcher searcher){
        this.searcher = searcher;
    }

    public void UploadComment(Comment comment){
        CollectionReference collectionRef = db.collection("Comments");

        HashMap<String, Object> hm = new HashMap<String,Object>();
        hm.put("uID", comment.getAuthorID());
        hm.put("eID", comment.getExperimentID());
        hm.put("content", comment.getContent());
        hm.put("datetime", comment.getDatetime());
        hm.put("userName", comment.getUsername());
        DocumentReference newCommentRef = collectionRef.document();
        newCommentRef.set(hm);
    }

    public void getExperimentComments(String expID) {
        db.collection("Comments").whereEqualTo("eID", expID).orderBy("datetime")
        .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                ArrayList<Comment> results = new ArrayList<Comment>();
                List<DocumentSnapshot> docSnapList = value.getDocuments();
                for (DocumentSnapshot docSnap : docSnapList) {
                    String eID = docSnap.getString("eID");
                    String uID = docSnap.getString("uID");
                    String username = docSnap.getString("userName");
                    String content = docSnap.getString("content");
                    Date dt = docSnap.getDate("datetime");
                    results.add(new Comment(eID, uID, username, content, dt));
                }
                searcher.onExpCommentsFound(results);
            }
        });
    }
}
