package com.loicortola.chat.android;

import android.app.Application;

import com.loicortola.chat.android.service.ChatAPIService;

/**
 * Created by loic on 29/04/2016.
 */
public class ChatApplication extends Application {

    private ChatAPIService chatAPIService;


    @Override
    public void onCreate() {
        super.onCreate();
        chatAPIService = new ChatAPIService(this.getApplicationContext());
    }

    public ChatAPIService getService() {
        return chatAPIService;
    }
}
