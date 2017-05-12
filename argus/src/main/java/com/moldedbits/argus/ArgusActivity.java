package com.moldedbits.argus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.moldedbits.argus.listener.ResultListener;
import com.moldedbits.argus.model.ArgusUser;

public class ArgusActivity extends AppCompatActivity
        implements ResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Argus.getInstance() == null) {
            throw new RuntimeException("Argus not initialized");
        }
        // If user is logged in, proceed to next screen
        if (Argus.getInstance().getState() == ArgusState.SIGNED_IN) {
            showNextScreen();
            return;
        }

        // Otherwise, show login flow
        setContentView(R.layout.activity_argus);

        showLoginFragment();
    }

    // TODO:: Add support for login and signup fragments
    private void showLoginFragment() {
        BaseFragment baseFragment = LoginFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, baseFragment)
                .commit();
    }

    @Override
    public void onSuccess(ArgusUser user, ArgusState resultState) {
        Argus.getInstance().setState(resultState);
        if (resultState == ArgusState.SIGNED_IN) {
            Argus.getInstance().loginUser(user);
            showNextScreen();
        } else {
            // TODO:: This is a hack to update view when sign in is cancelled. We will fix this
            // in a future update.
            showLoginFragment();
        }
    }

    @Override
    public void onFailure(String message, ArgusState resultState) {
        //TODO handle failure correctly
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showNextScreen() {
        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
        finish();
    }

    public void onSignupClick(View view) {
        showSignUpScreen();
    }

    private void showSignUpScreen() {
        BaseFragment baseFragment = SignupFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, baseFragment)
                .addToBackStack("login")
                .commit();
    }
}
