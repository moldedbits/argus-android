package com.moldedbits.argus.sample;

import android.app.Application;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.SimpleNextScreenProvider;
import com.moldedbits.argus.provider.login.EmailLoginProvider;
import com.moldedbits.argus.provider.signup.EmailSignupProvider;
import com.moldedbits.argus.provider.login.FaceBookLoginProvider;
import com.moldedbits.argus.provider.login.GoogleLoginProvider;
import com.moldedbits.argus.provider.login.LoginProvider;

import java.util.ArrayList;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ArrayList<LoginProvider> loginProviders = new ArrayList<>();
        loginProviders.add(new EmailLoginProvider());
        loginProviders.add(new GoogleLoginProvider());
        loginProviders.add(new FaceBookLoginProvider());

        Argus argus = new Argus.Builder()
                .nextScreenProvider(new SimpleNextScreenProvider(MainActivity.class))
                .signupProvider(new EmailSignupProvider())
                .setLoginLayout(R.layout.custom_login_fragment)
                .loginProvider(loginProviders)
                .build();

        Argus.initialize(argus);
    }
}
