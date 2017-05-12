package com.moldedbits.argus.sample;

import android.app.Application;
import android.text.TextUtils;
import android.util.Patterns;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.ArgusTheme;
import com.moldedbits.argus.SimpleNextScreenProvider;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.provider.login.EmailLoginProvider;
import com.moldedbits.argus.provider.signup.EmailSignupProvider;
import com.moldedbits.argus.provider.social.FacebookOnBoardingProvider;
import com.moldedbits.argus.provider.social.GoogleOnBoardingProvider;
import com.moldedbits.argus.storage.DefaultArgusStorage;
import com.moldedbits.argus.validations.LengthValidation;
import com.moldedbits.argus.validations.RegexValidation;

import java.util.ArrayList;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ArrayList<BaseProvider> loginProviders = new ArrayList<>();
        ArrayList<BaseProvider> signupProviders = new ArrayList<>();
        loginProviders.add(new EmailLoginProvider());
        loginProviders.add(new GoogleOnBoardingProvider());
        loginProviders.add(new FacebookOnBoardingProvider());

        EmailSignupProvider emailSignupProvider = new EmailSignupProvider();
        emailSignupProvider.getValidationEngine()
                .addPasswordValidation(new LengthValidation(4, 8, getString(R.string.password_length)))
                .addEmailValidation(new RegexValidation(Patterns.EMAIL_ADDRESS.pattern(),
                        getString(R.string.enter_valid_email)));

        signupProviders.add(emailSignupProvider);
        signupProviders.add(new GoogleOnBoardingProvider());
        signupProviders.add(new FacebookOnBoardingProvider());

        ArgusTheme argusTheme = new ArgusTheme.Builder()
                .buttonColor(R.color.com_facebook_blue)
                .build();

        new Argus.Builder()
                .argusStorage(new DefaultArgusStorage(getApplicationContext()))
                .nextScreenProvider(new SimpleNextScreenProvider(MainActivity.class))
                .signupProviders(signupProviders)
                .loginProviders(loginProviders)
                .theme(argusTheme)
                .signupLayout(R.layout.custom_signup_layout)
                .build();
    }
}