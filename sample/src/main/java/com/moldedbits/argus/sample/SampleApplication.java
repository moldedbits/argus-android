package com.moldedbits.argus.sample;

import android.app.Application;

import com.moldedbits.argus.Argus;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Argus argus =
                new Argus.Builder()
                        .nextScreen(MainActivity.class)
                        .build();
        Argus.initialize(argus);
    }
}
