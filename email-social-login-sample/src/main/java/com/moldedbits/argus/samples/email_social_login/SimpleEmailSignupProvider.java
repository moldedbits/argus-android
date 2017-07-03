package com.moldedbits.argus.samples.email_social_login;

import android.widget.Toast;

import com.moldedbits.argus.provider.signup.EmailSignupProvider;

/**
 * Created by abhishek
 * on 23/06/17.
 */

class SimpleEmailSignupProvider extends EmailSignupProvider {
    SimpleEmailSignupProvider(boolean isValidationRequired) {
        super(isValidationRequired);
    }

    @Override
    public void doServerSignup(String username, String email, String password) {
        // need to set state signed-in in Argus here
        if(username.equals("valid@user.com") && password.equals("password")) {
            // do a real API call here and in on success do following
            if (context != null) {
                Toast.makeText(context, context.getString(R.string.signing_up),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            if (context != null) {
                Toast.makeText(context, context.getString(R.string.invalid_email),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}
