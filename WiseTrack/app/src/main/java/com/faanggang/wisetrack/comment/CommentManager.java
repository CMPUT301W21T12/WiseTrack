package com.faanggang.wisetrack.comment;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CommentManager {
    private commentSearcher commentSearcher;
    private responseSearcher responseSearcher;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface commentSearcher {
        void onExpCommentsFound(ArrayList<Comment> results);
    }
    public interface responseSearcher {
        void onResponsesFound(ArrayList<Response> results);
    }

    public CommentManager(commentSearcher searcher){
        this.commentSearcher = searcher;
    }
    public CommentManager(responseSearcher searcher){
        this.responseSearcher = searcher;
    }

    public void UploadComment(Comment comment){
        CollectionReference collectionRef = db.collection("Comments");
        HashMap<String, Object> hm = new HashMap<String,Object>();
        hm.put("uID", comment.getAuthorID());
        hm.put("eID", comment.getExperimentID());
        hm.put("content", comment.getContent());
        hm.put("datetime", comment.getDatetime());
        collectionRef.add(hm);
    }

    public void UploadResponse(String commentID, Response response){
        DocumentReference docRef = db.collection("Comments").document(commentID);
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("datetime", response.getDatetime());
        hm.put("content", response.getContent());
        hm.put("uID", response.getAuthorID());
        docRef.collection("Responses").add(hm);

    }

    public void getExperimentComments(String expID) {
        db.collection("Comments").whereEqualTo("eID", expID)
        .orderBy("datetime",Query.Direction.DESCENDING)
        .get().addOnCompleteListener(task->{
            if (task.isSuccessful()){
            ArrayList<Comment> results = new ArrayList<Comment>();
            List<DocumentSnapshot> docSnapList = task.getResult().getDocuments();
            for (DocumentSnapshot docSnap : docSnapList) {
                String eID = docSnap.getString("eID");
                String uID = docSnap.getString("uID");
                String username = docSnap.getString("userName");
                String content = docSnap.getString("content");
                Date dt = docSnap.getDate("datetime");
                Comment comment = new Comment(eID, uID, content, dt);
                comment.setFirebaseID(docSnap.getId());
                results.add(comment);
            }
            commentSearcher.onExpCommentsFound(results);
            }
        });
    }

    public void getCommentResponses(String commentID){
        db.collection("Comments").document(commentID).collection("Responses")
        .orderBy("datetime", Query.Direction.DESCENDING).get().addOnCompleteListener(task->{
            if (task.isSuccessful()) {
                Log.e("", String.valueOf(task.getResult().size()));
                if (task.getResult().size() != 0){
                    ArrayList<Response> results = new ArrayList<Response>();
                    List<DocumentSnapshot> docSnapList = task.getResult().getDocuments();
                    for (DocumentSnapshot docSnap : docSnapList) {
                        String eID = docSnap.getString("eID");
                        String uID = docSnap.getString("uID");
                        String username = docSnap.getString("userName");
                        String content = docSnap.getString("content");
                        Date dt = docSnap.getDate("datetime");
                        results.add(new Response(eID, uID, username, dt));
                    }
                    responseSearcher.onResponsesFound(results);
                }
            }
        });
    }
}

