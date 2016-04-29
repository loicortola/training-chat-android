package com.loicortola.chat.android.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loicortola.chat.android.R;


public class DashboardActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "extra_username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        String username = getIntent().getExtras().getString(EXTRA_USERNAME);
        TextView message = (TextView) findViewById(R.id.hello_world);
        message.setText(getString(R.string.hello_world, username));
    }

}
