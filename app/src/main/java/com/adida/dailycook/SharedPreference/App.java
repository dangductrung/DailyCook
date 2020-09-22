package com.adida.dailycook.SharedPreference;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;

public class App extends Application {
    private static App self;

    public static App self() {
        return self;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
    }
}