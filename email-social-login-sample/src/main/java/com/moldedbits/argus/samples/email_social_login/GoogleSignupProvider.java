package com.moldedbits.argus.samples.email_social_login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.moldedbits.argus.provider.social.GoogleOnBoardingProvider;

public class GoogleSignupProvider extends GoogleOnBoardingProvider {

    @Override
    public void onSuccess(GoogleSignInAccount account) {
        super.onSuccess(account);
    }

    @Override
    public void onFailure(String message) {
        super.onFailure(message);
    }
}
