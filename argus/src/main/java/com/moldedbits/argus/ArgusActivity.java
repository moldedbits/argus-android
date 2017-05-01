package com.moldedbits.argus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class ArgusActivity extends AppCompatActivity implements
        LoginFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_argus);

        if (Argus.getInstance() == null) {
            throw new RuntimeException("Argus not initialized");
        }
        if (ArgusSessionManager.isLoggedIn()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, LoginFragment.newInstance())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, SignupFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onLoginSuccess() {
        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
        finish();
    }

    @Override
    public void onSignUpSuccess() {
        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
        finish();
    }

    @Override
    public void onSignupError() {
        Toast.makeText(this,"fail",Toast.LENGTH_SHORT).show();
    }
}
