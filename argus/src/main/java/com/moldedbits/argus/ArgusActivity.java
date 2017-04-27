package com.moldedbits.argus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ArgusActivity extends AppCompatActivity implements
        LoginFragment.OnFragmentInteractionListener {

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

    @Override
    public void onLoginSuccess() {
        startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
        finish();
    }
}
