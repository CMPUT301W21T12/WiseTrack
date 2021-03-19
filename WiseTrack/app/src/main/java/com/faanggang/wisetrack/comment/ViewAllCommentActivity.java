package com.faanggang.wisetrack.comment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.adapters.CommentAdapter;

import java.util.ArrayList;

public class ViewAllCommentActivity extends AppCompatActivity implements CommentManager.CommentSearcher, AddCommentFragment.OnFragmentInteractionListener{
    private CommentManager cmtManager;
    private ArrayList<Comment> comments;
    private CommentAdapter cmtAdapter;
    private RecyclerView recyclerView;
    private String expID;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_comment);
        cmtManager = new CommentManager(this);
        comments = new ArrayList<Comment>();
        cmtAdapter = new CommentAdapter(this, comments);
        recyclerView = findViewById(R.id.view_experiment_comments_recycler);
        recyclerView.setAdapter(cmtAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        expID = getIntent().getStringExtra("EXP_ID");
        cmtManager.getExperimentComments(expID);

        // Code Block for starting up add comment fragment
        final Button addCommentButton = findViewById(R.id.add_comment_button);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("EXP_ID", expID);
                AddCommentFragment frag = new AddCommentFragment();
                frag.setArguments(bundle);
                frag.show(getSupportFragmentManager(), "");
            }
        });


    }
    /**
     * This method takes the results of a Firebase Query and adds them to the list of comments to display.
     * @param results: List of comments that match the query criteria.
     */
    @Override
    public void onExpCommentsFound(ArrayList<Comment> results) {
        comments.clear();
        comments.addAll(results);
        cmtAdapter.notifyDataSetChanged();
    }
    /**
     * This method calls the manager to upload a comment to the Firestore and updates the current list to display immediate changes.
     * @param comment: A comment to upload to Firestore and display.
     */
    @Override
    public void addCommentOkPressed(Comment comment){
        cmtManager.uploadComment(comment);
        cmtManager.getExperimentComments(expID);
    };
}
