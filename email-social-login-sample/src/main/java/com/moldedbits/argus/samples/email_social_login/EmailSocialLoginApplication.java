package com.moldedbits.argus.samples.email_social_login;

import android.app.Application;
import android.graphics.drawable.ColorDrawable;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.ArgusTheme;
import com.moldedbits.argus.nextscreenproviders.SimpleNextScreenProvider;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.provider.login.EmailLoginProvider;
import com.moldedbits.argus.provider.social.FacebookOnBoardingProvider;
import com.moldedbits.argus.provider.social.GoogleOnBoardingProvider;
import com.moldedbits.argus.storage.DefaultArgusStorage;
import com.moldedbits.argus.validations.LengthValidation;

import java.util.ArrayList;

public class EmailSocialLoginApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize Argus
        ArrayList<BaseProvider> signupProviders = new ArrayList<>();
        SimpleEmailSignupProvider emailSignupProvider = new SimpleEmailSignupProvider(false);
        emailSignupProvider.getValidationEngine()
                .addPasswordValidation(new LengthValidation(4, 8, getString(R.string.password_length)));
        signupProviders.add(emailSignupProvider);

        // add google signup provider
        signupProviders.add(new GoogleOnBoardingProvider());

        // add facebook signup provider
        signupProviders.add(new FacebookOnBoardingProvider());

        ArrayList<BaseProvider> loginProviders = new ArrayList<>();
        EmailLoginProvider loginProvider = new SimpleEmailLoginProvider();
        loginProvider.setShowPasswordEnabled(false);
        loginProviders.add(loginProvider);

        // add google login provider
        loginProviders.add(new GoogleOnBoardingProvider());

        // add facebook login provider
        loginProviders.add(new FacebookOnBoardingProvider());

        ArgusTheme.Builder themeBuilder = new ArgusTheme.Builder();
        themeBuilder.logo(R.drawable.argus_logo)
                    .backgroundDrawable(R.drawable.bg)
                    .buttonDrawable(R.drawable.button_bg)
                    .welcomeText(getString(R.string.welcome))
                    .welcomeTextSize(18.0f)
                    .showEditTextDrawables(false);

        new Argus.Builder()
                .argusStorage(new DefaultArgusStorage(getApplicationContext()))
                .nextScreenProvider(new SimpleNextScreenProvider(HomeActivity.class))
                .signupProviders(signupProviders)
                .loginProviders(loginProviders)
                .theme(themeBuilder.build())
                .forgotPasswordProvider(new SimpleForgotPasswordProvider())
                .build();
    }
}