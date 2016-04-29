package com.loicortola.chat.android.task;

import android.os.AsyncTask;

import com.loicortola.chat.android.listener.AsyncTaskController;
import com.loicortola.chat.android.service.ChatAPIService;
import com.loicortola.chat.android.ChatApplication;

/**
 * Created by loic on 29/04/2016.
 */
public class LoginTask extends AsyncTask<Void, Void, Boolean> {

    private String login;
    private String password;

    private AsyncTaskController<Boolean> l;
    private ChatAPIService chatAPIService;

    public LoginTask(ChatApplication app, String login, String password) {
        this.login = login;
        this.password = password;
        chatAPIService = app.getService();
    }

    public void setLoginTaskListener(AsyncTaskController l) {
        this.l = l;
    }


    @Override
    protected void onPreExecute() {
        if (l != null) {
            l.onPreExecute();
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return chatAPIService.connect(login, password);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (l != null) {
            l.onPostExecute(result);
        }
    }
}
