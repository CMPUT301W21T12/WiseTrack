package com.faanggang.wisetrack.comment;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;

import java.util.ArrayList;

public class ViewCommentActivity extends AppCompatActivity implements CommentManager.Searcher{
    private CommentManager cmtManager;
    private ArrayList<Comment> comments;
    private CommentAdapter cmtAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_experiment_comments);
        cmtManager = new CommentManager(this);
        comments = new ArrayList<Comment>();
        cmtAdapter = new CommentAdapter(this, comments);
        recyclerView = findViewById(R.id.view_experiment_comments_recycler);
        recyclerView.setAdapter(cmtAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        cmtManager.getExperimentComments(getIntent().getStringExtra("EXP_ID"));
    }

    @Override
    public void onExpCommentsFound(ArrayList<Comment> results) {
        comments.clear();
        comments.addAll(results);
        cmtAdapter.notifyDataSetChanged();
        Log.w("COMMENT","WE GOT HERE");
    }
}
