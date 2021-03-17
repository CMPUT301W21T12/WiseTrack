package com.faanggang.wisetrack.experiment;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.user.UserManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.faanggang.wisetrack.comment.ViewAllCommentActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewExperimentActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView expNameView;
    private TextView expDescriptionView;
    private TextView expRegionView;
    private TextView expMinTrialsView;
    private TextView expOwnerView;
    private TextView expStatusView;
    private String expID;
    private ExperimentManager experimentManager;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        experimentManager = new ExperimentManager();
        userManager = new UserManager(FirebaseFirestore.getInstance());
        expID = getIntent().getStringExtra("EXP_ID");
        Log.w("EXP", expID);
        setContentView(R.layout.view_experiment_detail);
        expNameView = findViewById(R.id.view_experimentName);
        expDescriptionView = findViewById(R.id.view_experimentDescription);
        expRegionView = findViewById(R.id.view_experimentRegion);
        expMinTrialsView = findViewById(R.id.view_min_num_trials);
        expOwnerView = findViewById(R.id.view_owner);
        expStatusView = findViewById(R.id.view_status);
        experimentManager.getExperimentInfo(expID, task->{
            DocumentSnapshot docSnap = task.getResult();
            expNameView.setText(docSnap.getString("name"));
            expDescriptionView.setText(docSnap.getString("description"));
            expRegionView.setText(docSnap.getString("region"));
            expMinTrialsView.setText(docSnap.getLong("minTrials").toString());
            userManager.getUserInfo(docSnap.getString("uID"), task2->{
                expOwnerView.setText(task2.getResult().getString("userName"));
            if (docSnap.getBoolean("open")) {
                expStatusView.setText("Open");
            } else {
                expStatusView.setText("Closed");
            }
        });
        });


        FloatingActionButton ExperimentActionMenu = findViewById(R.id.experiment_action_menu);

        // link floating action button to experiment action menu xml
        registerForContextMenu(ExperimentActionMenu);

        ExperimentActionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();  // Note: default is long clicking to open action menu
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.experiment_action_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // check which item was clicked
        switch (item.getItemId()) {
            case R.id.subscribe_option:
                Toast.makeText(this, "Subscribe option selected", Toast.LENGTH_SHORT).show();
                return true;  // item clicked return true
            case R.id.unpublish_option:
                Toast.makeText(this, "Unpublish option selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.end_experiment_option:
                Toast.makeText(this, "End experiment option selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.results_option:
                Toast.makeText(this, "View experiment results selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.geolocations_option:
                Toast.makeText(this, "View geolocation option selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.execute_trials_option:
                Toast.makeText(this, "Execute trials option selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.comment_option:
                Intent intent = new Intent(ViewExperimentActivity.this, ViewAllCommentActivity.class);
                intent.putExtra("EXP_ID", expID);
                startActivity(intent);
                return true;
            case R.id.view_trials_option:
                Toast.makeText(this, "View trials option selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
