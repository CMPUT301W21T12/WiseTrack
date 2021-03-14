package com.faanggang.wisetrack.comment;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



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
        hm.put("children", comment.getChildren());

        DocumentReference newCommentRef = collectionRef.document();
        newCommentRef.set(hm);
    }

    public void getExperimentComments(String expID) {

        db.collection("Comments").whereEqualTo("eID", expID).orderBy("datetime")
        .get()
        .addOnCompleteListener( task ->{
            if (task.isSuccessful()) {
                ArrayList<Comment> results = new ArrayList<Comment>();
                List<DocumentSnapshot> docSnapList = task.getResult().getDocuments();
                for (DocumentSnapshot docSnap: docSnapList ){
                    String eID = docSnap.getString("eID");
                    String uID = docSnap.getString("uID");
                    String content = docSnap.getString("content");
                    Date dt = docSnap.getDate("datetime");
                    results.add(new Comment(eID, uID, content, dt));
                }
                searcher.onExpCommentsFound(results);
            }else {
                Log.w("COMPLETED:FAILURE", task.getException().toString());
            }
        })
        .addOnFailureListener(e -> {
            Log.w("FAILURE", e.toString());
        });
        ;


    }



}
