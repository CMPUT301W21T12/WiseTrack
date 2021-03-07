package com.faanggang.wisetrack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ViewSelfActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_self_profile);

        //assume Main activity fetches userID and passes it through intent

    }

}
