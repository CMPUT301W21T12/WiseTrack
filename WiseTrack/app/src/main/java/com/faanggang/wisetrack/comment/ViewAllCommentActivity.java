package com.faanggang.wisetrack.comment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.adapters.CommentAdapter;

import java.util.ArrayList;
import java.util.Date;

public class ViewAllCommentActivity extends AppCompatActivity implements CommentManager.commentSearcher, AddCommentFragment.OnFragmentInteractionListener{
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

    @Override
    public void onExpCommentsFound(ArrayList<Comment> results) {
        comments.clear();
        comments.addAll(results);
        cmtAdapter.notifyDataSetChanged();
    }

    @Override
    public void addCommentOkPressed(Comment comment){
        cmtManager.UploadComment(comment);
    };
}
