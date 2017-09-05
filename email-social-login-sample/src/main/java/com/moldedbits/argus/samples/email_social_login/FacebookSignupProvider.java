package com.moldedbits.argus.samples.email_social_login;

import com.facebook.AccessToken;
import com.moldedbits.argus.provider.social.FacebookOnBoardingProvider;

public class FacebookSignupProvider extends FacebookOnBoardingProvider {

    @Override
    public void onSuccess(AccessToken token) {
        super.onSuccess(token);
    }

    @Override
    public void onFailure(String message) {
        super.onFailure(message);
    }
}
