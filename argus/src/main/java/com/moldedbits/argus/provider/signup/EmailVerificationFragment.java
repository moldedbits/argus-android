package com.moldedbits.argus.provider.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.R;

import lombok.Setter;

/**
 * Created by shishank on 10/05/17.
 */

public class EmailVerificationFragment extends Fragment {

    public interface EmailVerificationListener {
        void onValidated();
        void onCancelled();
    }

    private View btnValidate, btnCancel;

    @Setter
    private EmailVerificationListener emailVerificationListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_email_verification, container, false);
        btnValidate = view.findViewById(R.id.btn_validate);
        btnCancel = view.findViewById(R.id.btn_cancel);
        setListeners();
        return view;
    }

    private void setListeners() {
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailVerificationListener.onValidated();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailVerificationListener.onCancelled();
            }
        });
    }
}
