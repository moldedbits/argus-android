package com.moldedbits.argus.samples.simplelogin;

import android.widget.Toast;

import com.moldedbits.argus.ArgusState;

/**
 * Created by abhishek
 * on 16/06/17.
 *
 * This is a sample implementation of default login provider available in Argus
 */

public class SimpleEmailLoginProvider extends com.moldedbits.argus.provider.login.EmailLoginProvider {

    /**
     * this function is called after user input was validated
     * this is where actual API call to your server will go
     */
    @Override
    protected void doServerLogin(String username, String password) {
        // need to set state signed-in in Argus here
        if(username.equals("valid@user.com") && password.equals("password")) {
            // do a real API call here and in on success do following
            if (resultListener != null) {
                resultListener.onSuccess(ArgusState.SIGNED_IN);
            }
        } else {
            if (context != null) {
                Toast.makeText(context, context.getString(R.string.invalid_email),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
