package com.moldedbits.argus;

import android.content.Intent;
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
        Class nextScreen = Argus.getInstance().getNextScreen();
        if (nextScreen != null) {
            startActivity(new Intent(this, nextScreen));
            finish();
        } else {
            startActivity(Argus.getInstance().getNextScreenProvider().getNextScreen(this));
            finish();
        }
    }
}
