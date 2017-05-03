package com.moldedbits.argus.sample;

import android.app.Application;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.SimpleNextScreenProvider;
import com.moldedbits.argus.provider.login.EmailLoginProvider;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.provider.signup.EmailSignupProvider;
import com.moldedbits.argus.provider.sociallogin.FaceBookSignupProvider;
import com.moldedbits.argus.provider.sociallogin.GoogleSignupProvider;

import java.util.ArrayList;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ArrayList<BaseProvider> baseProviders = new ArrayList<>();
        ArrayList<BaseProvider> signupProviders = new ArrayList<>();
        baseProviders.add(new EmailLoginProvider());
        baseProviders.add(new FaceBookSignupProvider());
        baseProviders.add(new GoogleSignupProvider());
        signupProviders.add(new EmailSignupProvider());
        signupProviders.add(new FaceBookSignupProvider());
        signupProviders.add(new GoogleSignupProvider());

        Argus argus = new Argus.Builder()
                .nextScreenProvider(new SimpleNextScreenProvider(MainActivity.class))
                .signupProvider(signupProviders)
                .setLoginLayout(R.layout.custom_login_fragment)
                .setSignupLayout(R.layout.custom_signup_layout)
                .loginProvider(baseProviders)
                .build();

        Argus.initialize(argus);
    }
}
