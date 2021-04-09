package com.faanggang.wisetrack.view.comment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.CommentManager;
import com.faanggang.wisetrack.model.comment.Response;
import com.faanggang.wisetrack.view.adapters.ResponseAdapter;

import java.util.ArrayList;


public class ViewAllResponseActivity extends AppCompatActivity implements CommentManager.ResponseSearcher, AddResponseFragment.OnFragmentInteractionListener {
    private CommentManager cmtManager;
    private ArrayList<Response> responses;
    private ResponseAdapter rspAdapter;
    private RecyclerView recyclerView;
    private Intent intent;
    private TextView parent_username_view;
    private TextView parent_content_view;
    private TextView parent_datetime_view;
    private String parentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_response);
        cmtManager = new CommentManager(this);
        responses = new ArrayList<Response>();
        rspAdapter = new ResponseAdapter(this, responses);
        intent = getIntent();
        setParent();
        recyclerView = findViewById(R.id.view_comment_responses_recycler);
        recyclerView.setAdapter(rspAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        parentID = intent.getStringExtra("CMT_ID");
        cmtManager.getCommentResponses(parentID);

        final Button addResponseButton = findViewById(R.id.add_response_button);
        addResponseButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AddResponseFragment frag = new AddResponseFragment();
                frag.show(getSupportFragmentManager(), "");
            }
        });
    }

    private void setParent(){
        parent_content_view = findViewById(R.id.parent_comment_content);
        parent_datetime_view = findViewById(R.id.parent_comment_date);
        parent_username_view = findViewById(R.id.parent_comment_username);
        parent_content_view.setText(intent.getStringExtra("PARENT_CONTENT"));
        parent_datetime_view.setText(intent.getStringExtra("PARENT_DATE"));
        parent_username_view.setText(intent.getStringExtra("PARENT_USERNAME"));
    }

    /**
     * This method takes the results of a Firebase Query and adds them to the list of responses to display.
     * @param results List of responses that match the query criteria.
     */
    @Override
    public void onResponsesFound(ArrayList<Response> results) {
        responses.clear();
        responses.addAll(results);
        rspAdapter.notifyDataSetChanged();
    }

    /**
     * This method calls a manager to uploads a response to the Firestore and updates the current list to display immediate changes.
     * @param response A response to upload to Firestore and display.
     */
    @Override
    public void addResponseOkPressed(Response response){
        cmtManager.uploadResponse(parentID, response);
        responses.add(0,response);
        rspAdapter.notifyDataSetChanged();
    };
}