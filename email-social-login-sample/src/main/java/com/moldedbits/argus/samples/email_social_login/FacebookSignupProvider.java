package com.moldedbits.argus.samples.email_social_login;

import android.util.Log;

import com.facebook.AccessToken;
import com.moldedbits.argus.provider.social.FacebookOnBoardingProvider;

public class FacebookSignupProvider extends FacebookOnBoardingProvider {

    @Override
    public void onSuccess(AccessToken token) {
        super.onSuccess(token);
        Log.d("FACEBOOK_SIGNUP",token.getToken());
    }

    @Override
    public void onFailure(String message) {
        super.onFailure(message);
    }
}
