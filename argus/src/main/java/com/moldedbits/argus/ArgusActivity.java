package com.moldedbits.argus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.moldedbits.argus.listener.LoginListener;
import com.moldedbits.argus.listener.PhoneNoValidationListener;
import com.moldedbits.argus.listener.SignUpListener;
import com.moldedbits.argus.model.ArgusUser;

public class ArgusActivity extends AppCompatActivity
        implements LoginListener, SignUpListener, PhoneNoValidationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_argus);

        if (Argus.getInstance() == null) {
            throw new RuntimeException("Argus not initialized");
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, LoginFragment.newInstance())
                .commit();
    }

    public void onLoginSuccess(ArgusUser user) {
        Argus.getInstance().loginUser(user);
        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
        finish();
    }

    @Override
    public void onLoginFailure(String message) {
        //TODO handle failure correctly
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpSuccess(ArgusUser user) {
        // TODO: 05/05/17 to be implemented
    }

    @Override
    public void onSignUpFailure(String message) {
        // TODO: 05/05/17 to be implemented
    }

    @Override
    public void onPhoneValidationSuccess(ArgusUser user) {
        // TODO: 05/05/17 to be implemented
    }

    @Override
    public void onPhoneValidationFailure(String message) {
        // TODO: 05/05/17 to be implemented
    }
}
