package com.moldedbits.argus.sample;

import android.app.Application;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.SimpleNextScreenProvider;
import com.moldedbits.argus.provider.EmailLoginProvider;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Argus argus = new Argus.Builder()
                .nextScreenProvider(new SimpleNextScreenProvider(MainActivity.class))
                .loginProvider(new EmailLoginProvider())
                .build();
        Argus.initialize(argus);
    }
}
