package com.moldedbits.argus.sample;

import android.app.Application;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.SimpleNextScreenProvider;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.provider.login.EmailLoginProvider;
import com.moldedbits.argus.provider.signup.EmailSignupProvider;
import com.moldedbits.argus.provider.sociallogin.FaceBookSignupProvider;
import com.moldedbits.argus.provider.sociallogin.GoogleSignupProvider;

import java.util.ArrayList;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ArrayList<BaseProvider> loginProviders = new ArrayList<>();
        ArrayList<BaseProvider> signupProviders = new ArrayList<>();
        loginProviders.add(new EmailLoginProvider());
        loginProviders.add(new FaceBookSignupProvider());
        loginProviders.add(new GoogleSignupProvider());

        signupProviders.add(new EmailSignupProvider());
        signupProviders.add(new FaceBookSignupProvider());
        signupProviders.add(new GoogleSignupProvider());

        Argus argus = new Argus.Builder()
                .nextScreenProvider(new SimpleNextScreenProvider(MainActivity.class))
                .signupProviders(signupProviders)
                .loginProviders(loginProviders)
                .build();

        Argus.initialize(argus);
    }
}
