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

/**
 *  CommentManager is a controller class that is primarily focused on retrieving and uploading
 *  comment/response data from the Cloud Firestore.
 */
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

    /**
     * Uploads a Comment object to the Cloud Firestore. The Comment Object must have the following
     * attributes populated with information: uID, eID, content, datetime in order for a successful
     * upload. This method is asynchronous but does not return anything.
     * @param comment
     * This is the Comment Object that you wish to upload to the Firestore.
     */
    public void UploadComment(Comment comment){
        CollectionReference collectionRef = db.collection("Comments");
        HashMap<String, Object> hm = new HashMap<String,Object>();
        hm.put("uID", comment.getAuthorID());
        hm.put("eID", comment.getExperimentID());
        hm.put("content", comment.getContent());
        hm.put("datetime", comment.getDatetime());
        collectionRef.add(hm);
    }
    /**
     * Uploads a Response object to the Cloud Firestore. The Response Object must have the following
     * attributes populated with information: uID, content, datetime in order for a successful
     * upload. This method is asynchronous but does not return anything.
     * @param commentID
     * This is the ID of the parent of the Response Object. The response will be uploaded to the
     * parent's collection of responses.
     * @param response
     * This is the Response Object that will be uploaded to the Firestore.
     */
    public void UploadResponse(String commentID, Response response){
        DocumentReference docRef = db.collection("Comments").document(commentID);
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("datetime", response.getDatetime());
        hm.put("content", response.getContent());
        hm.put("uID", response.getAuthorID());
        docRef.collection("Responses").add(hm);

    }

    /**
     * Queries the Cloud Firestore for Comments belonging to a certain experiment.
     * This method does not return anything. It instead calls on an interface method implemented
     * by the Object that is receiving the data.
     * @param expID
     * The document ID of the experiment that the comments are being queried for.
     */
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
    /**
     * Queries the Cloud Firestore for Responses belonging to a certain comment parent.
     * This method does not return anything. It instead calls on an interface method implemented
     * by the Object that is receiving the data.
     * @param commentID
     * The document ID of the comment that the responses are being queried for.
     */
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

