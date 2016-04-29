package com.loicortola.chat.android.service;

import android.content.Context;
import android.util.Log;


import com.loicortola.chat.android.R;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by loic on 29/04/2016.
 */
public class ChatAPIService {

    private static final String TAG = ChatAPIService.class.getSimpleName();

    private OkHttpClient client;

    private String backendUrl;

    public ChatAPIService(Context ctx) {
        this.client = new OkHttpClient.Builder().build();
        this.backendUrl = ctx.getString(R.string.chat_api);
    }

    /**
     * Attempt connection with basic auth
     *
     * @param login    the login
     * @param password the password
     * @return true if auth success, false otherwise
     */
    public boolean connect(String login, String password) {
        final String credentials = Credentials.basic(login, password);
        Log.d(TAG, "Will call URL " + backendUrl + "/connect");
        Log.d(TAG, "With credentials " + credentials);
        Request request = new Request.Builder().url(backendUrl + "/connect")
                .header("Authorization", Credentials.basic(login, password))
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                // If response is 200, set authentication strategy in client
                this.client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        return response.request().newBuilder().header("Authorization", credentials).build();
                    }
                }).build();
                return true;
            }
            // Else, authentication failed
            return false;
        } catch (IOException e) {
            Log.w(TAG, e);
            return false;
        }
    }
}
