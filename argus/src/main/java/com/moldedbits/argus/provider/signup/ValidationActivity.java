package com.moldedbits.argus.provider.signup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.moldedbits.argus.R;

public class ValidationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_argus);
        showProgressFragment();
    }

    private void showProgressFragment() {
        EmailVerificationFragment emailVerificationFragment = EmailVerificationFragment
                .newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.argus_content, emailVerificationFragment)
                .commit();
    }
}
