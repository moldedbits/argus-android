package com.moldedbits.argus.samples.email_social_login;

import android.app.Application;
import android.graphics.Color;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.moldedbits.argus.Argus;
import com.moldedbits.argus.ArgusTheme;
import com.moldedbits.argus.nextscreenproviders.SimpleNextScreenProvider;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.provider.login.EmailLoginProvider;
import com.moldedbits.argus.provider.social.FacebookOnBoardingProvider;
import com.moldedbits.argus.provider.social.GoogleOnBoardingProvider;
import com.moldedbits.argus.provider.social.helper.FacebookConfig;
import com.moldedbits.argus.storage.DefaultArgusStorage;
import com.moldedbits.argus.validations.LengthValidation;

import java.util.ArrayList;
import java.util.List;

public class EmailSocialLoginApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        // initialize Argus
        ArrayList<BaseProvider> signupProviders = new ArrayList<>();
        SimpleEmailSignupProvider emailSignupProvider = new SimpleEmailSignupProvider(false);
        emailSignupProvider.getValidationEngine()
                .addPasswordValidation(new LengthValidation(4, 8, getString(R.string.password_length)));
        signupProviders.add(emailSignupProvider);


        GoogleOnBoardingProvider googleProvider = new GoogleSignupProvider();

        FacebookOnBoardingProvider facebookProvider = new FacebookSignupProvider();
        List<String> permissionList = new ArrayList<>();
        permissionList.add(FacebookConfig.PUBLIC_PROFILE);
        facebookProvider.setFacebookPermission(permissionList);


        ArrayList<BaseProvider> loginProviders = new ArrayList<>();
        EmailLoginProvider loginProvider = new SimpleEmailLoginProvider();
        loginProvider.setShowPasswordEnabled(false);
        loginProviders.add(loginProvider);

        // add google login provider
        loginProviders.add(googleProvider);

        // add facebook login provider
        loginProviders.add(facebookProvider);

        // add facebook signup provider
        signupProviders.add(facebookProvider);

        // add google signup provider
        signupProviders.add(googleProvider);

        ArgusTheme.Builder themeBuilder = new ArgusTheme.Builder();
        themeBuilder.logo(R.drawable.argus_logo)
                .backgroundDrawable(R.drawable.bg)
                .buttonDrawable(R.drawable.button_bg)
                .welcomeText(getString(R.string.welcome))
                .welcomeTextColor(Color.WHITE)
                .welcomeTextSize(18.0f)
                .showEditTextDrawables(false);

        new Argus.Builder()
                .argusStorage(new DefaultArgusStorage(getApplicationContext()))
                .nextScreenProvider(new SimpleNextScreenProvider(HomeActivity.class))
                .enableSkipLogin(true)
                .skipLoginText(getString(R.string.skip_login))
                .signupProviders(signupProviders)
                .loginProviders(loginProviders)
                .theme(themeBuilder.build())
                .forgotPasswordProvider(new SimpleForgotPasswordProvider())
                .googleServerClientId(getString(R.string.server_client_id))
                .build();
    }
}