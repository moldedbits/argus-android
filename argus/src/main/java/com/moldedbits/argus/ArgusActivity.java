package com.moldedbits.argus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.moldedbits.argus.listener.ResultListener;

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

        int backgroundDrawable = Argus.getInstance().getArgusTheme().getBackgroundDrawable();
        if (backgroundDrawable > 0) {
            findViewById(R.id.argus_content).setBackgroundResource(backgroundDrawable);
        }

        showLoginFragment();
    }

    private void showLoginFragment() {
        BaseFragment baseFragment = LoginFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.argus_content, baseFragment)
                .commit();
    }

    @Override
    public void onSuccess(ArgusState argusState) {
        Argus.getInstance().setState(argusState);
        if (argusState == ArgusState.SIGNED_IN) {
            showNextScreen();
        }
    }

    @Override
    public void onFailure(String message) {
        //TODO handle failure correctly
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showNextScreen() {
        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
        finish();
    }

    public void onSignupClick(View view) {
        showSignupFragment();
    }

    private void showSignupFragment() {
        BaseFragment baseFragment = SignupFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.argus_content, baseFragment)
                .addToBackStack("login")
                .commit();
    }
}
