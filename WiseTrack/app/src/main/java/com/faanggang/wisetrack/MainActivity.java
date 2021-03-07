package com.faanggang.wisetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        setContentView(R.layout.main_menu);  // EDIT LATER: display main_menu later
                                                // create class to handle menu button clicks?

        Button publishButton = findViewById(R.id.menuPublish_button);

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, PublishExperimentActivity.class);
                startActivity(intent);
            }
        });


    }

/*    @Override
    public void onPublish(Experiment experiment){
        experimentAdapter.add(experiment);
    }
 */
}