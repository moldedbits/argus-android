package com.moldedbits.argus.samples.email_login_signup;

import android.app.Application;

import com.moldedbits.argus.Argus;
import com.moldedbits.argus.ArgusTheme;
import com.moldedbits.argus.nextscreenproviders.SimpleNextScreenProvider;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.provider.login.EmailLoginProvider;
import com.moldedbits.argus.provider.social.FacebookOnBoardingProvider;
import com.moldedbits.argus.provider.social.helper.FacebookConfig;
import com.moldedbits.argus.storage.DefaultArgusStorage;
import com.moldedbits.argus.validations.LengthValidation;

import java.util.ArrayList;
import java.util.List;

public class EmailLoginSignupApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize Argus
        ArrayList<BaseProvider> loginProviders = new ArrayList<>();
        ArrayList<BaseProvider> signupProviders = new ArrayList<>();
        SimpleEmailSignupProvider emailSignupProvider = new SimpleEmailSignupProvider(false);
        emailSignupProvider.getValidationEngine()
                .addPasswordValidation(new LengthValidation(4, 8, getString(R.string.password_length)));

        EmailLoginProvider loginProvider = new SimpleEmailLoginProvider();
        loginProvider.setShowPasswordEnabled(false);
        loginProviders.add(loginProvider);
        signupProviders.add(emailSignupProvider);

        FacebookOnBoardingProvider facebookProvider = new FacebookOnBoardingProvider();
        List<String> permissionList = new ArrayList<>();
        permissionList.add(FacebookConfig.EMAIL);
        permissionList.add(FacebookConfig.PUBLIC_PROFILE);
        facebookProvider.setFacebookPermission(permissionList);
        signupProviders.add(facebookProvider);

        ArgusTheme.Builder themeBuilder = new ArgusTheme.Builder();
        themeBuilder.logo(R.mipmap.ic_launcher);

        new Argus.Builder()
                .argusStorage(new DefaultArgusStorage(getApplicationContext()))
                .nextScreenProvider(new SimpleNextScreenProvider(HomeActivity.class))
                .skipLogin(true)
                .signupProviders(signupProviders)
                .loginProviders(loginProviders)
                .theme(themeBuilder.build())
                .forgotPasswordProvider(new SimpleForgotPasswordProvider())
                .build();
    }
}