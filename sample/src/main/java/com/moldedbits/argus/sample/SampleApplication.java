package com.moldedbits.argus.sample;

import android.app.Application;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.SimpleNextScreenProvider;
import com.moldedbits.argus.provider.EmailLoginProvider;
import com.moldedbits.argus.provider.GoogleLoginProvider;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Argus argus = new Argus.Builder()
                .nextScreenProvider(new SimpleNextScreenProvider(MainActivity.class))
                .loginProvider(new EmailLoginProvider())
                .loginProvider(new GoogleLoginProvider())
                .build();
        Argus.initialize(argus);
    }
}
