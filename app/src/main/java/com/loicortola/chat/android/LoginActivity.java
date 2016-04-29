package com.loicortola.chat.android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskController<Boolean> {

    private EditText mInputUsername;
    private EditText mInputPassword;
    private Button mSubmitBtn;
    private ProgressBar mProgressBar;
    private LoginTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mInputUsername = (EditText) findViewById(R.id.form_username);
        mInputPassword = (EditText) findViewById(R.id.form_password);
        mSubmitBtn = (Button) findViewById(R.id.btn_login);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mSubmitBtn.setOnClickListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelTasks();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String login = mInputUsername.getText().toString();
                String password = mInputPassword.getText().toString();
                startLoginTask(login, password);
                break;
        }
    }

    private void startLoginTask(String login, String password) {
        if (loginTask != null && loginTask.getStatus().equals(AsyncTask.Status.RUNNING)) {
            loginTask.cancel(true);
        }
        loginTask = new LoginTask((ChatApplication) this.getApplication(), login, password);
        loginTask.setLoginTaskListener(this);
        loginTask.execute();
    }

    private void cancelTasks() {
        loginTask.cancel(true);
    }

    /*
     * Login Task callbacks
     */

    @Override
    public void onPreExecute() {
        // Show progress bar
        mProgressBar.setVisibility(View.VISIBLE);
        // Disable login button
        mSubmitBtn.setEnabled(false);
    }

    @Override
    public void onPostExecute(Boolean success) {
        if (success) {
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DashboardActivity.class);

            intent.putExtra(DashboardActivity.EXTRA_USERNAME, mInputUsername.getText().toString());
            startActivity(intent);
        } else {
            Toast.makeText(this, "KO", Toast.LENGTH_LONG).show();
        }
        // Hide progress bar
        mProgressBar.setVisibility(View.INVISIBLE);
        // Enable login button
        mSubmitBtn.setEnabled(true);
    }
}
