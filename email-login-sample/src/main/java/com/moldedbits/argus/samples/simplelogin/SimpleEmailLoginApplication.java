package com.moldedbits.argus.samples.simplelogin;

import android.app.Application;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.ArgusTheme;
import com.moldedbits.argus.nextscreenproviders.SimpleNextScreenProvider;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.storage.DefaultArgusStorage;

import java.util.ArrayList;

public class SimpleEmailLoginApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ArrayList<BaseProvider> loginProviders = new ArrayList<>();
        loginProviders.add(new SimpleEmailLoginProvider());

        // argus is customizable, supplying my custom logo to argus
        ArgusTheme argusTheme = new ArgusTheme.Builder()
                .logo(R.mipmap.ic_launcher)
                .build();

        new Argus.Builder()
                // using default storage, dont want to do anything fancy here
                .argusStorage(new DefaultArgusStorage(getApplicationContext()))
                // this is the screen which should open after login is successfully completed
                .nextScreenProvider(new SimpleNextScreenProvider(HomeActivity.class))
                // we only want to implement login screen
                .loginProviders(loginProviders)
                // set custom theme
                .theme(argusTheme)
                .build();
    }
}