package com.moldedbits.argus.samples.email_login_signup;

import android.widget.Toast;

import com.moldedbits.argus.provider.ForgotPasswordProvider;

/**
 * Created by abhishek
 * on 23/06/17.
 */

public class SimpleForgotPasswordProvider extends ForgotPasswordProvider {
    @Override
    public void sendPasswordResetEmail(final String email) {
        Toast.makeText(context, "Sending email to:" + email, Toast.LENGTH_LONG).show();
    }
}
