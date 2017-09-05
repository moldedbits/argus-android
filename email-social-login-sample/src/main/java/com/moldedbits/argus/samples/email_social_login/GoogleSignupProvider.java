package com.moldedbits.argus.samples.email_social_login;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.moldedbits.argus.provider.social.GoogleOnBoardingProvider;


public class GoogleSignupProvider extends GoogleOnBoardingProvider {

    @Override
    public void onSuccess(GoogleSignInAccount account) {
        super.onSuccess(account);
         Log.d("GOOGLE_SIGNUP",account.getIdToken());
    }

    @Override
    public void onFailure(String message) {
        super.onFailure(message);
    }
}
