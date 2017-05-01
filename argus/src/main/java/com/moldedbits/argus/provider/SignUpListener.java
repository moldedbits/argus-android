package com.moldedbits.argus.provider;

/**
 * Created by shishank on 01/05/17.
 */

public interface SignUpListener {

    void onSignupSuccess();

    void onSignupError();

    void startSignup();
}
