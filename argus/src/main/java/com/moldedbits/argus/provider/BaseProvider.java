package com.moldedbits.argus.provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.moldedbits.argus.ArgusTheme;
import com.moldedbits.argus.R;
import com.moldedbits.argus.handler.ThemeHelper;
import com.moldedbits.argus.listener.ResultListener;
import com.moldedbits.argus.validations.ValidationEngine;

import lombok.Getter;
import lombok.Setter;

/**
 * Provides login functionality for specific end point
 */
public abstract class BaseProvider {

    private static final int DEFAULT_CONTAINER_ID = -1;

    @Nullable
    protected Context context;

    @Nullable
    @Setter
    protected ResultListener resultListener;

    protected Fragment fragment;

    @Getter
    protected ValidationEngine validationEngine;
    protected ArgusTheme theme;
    protected ThemeHelper themeHelper;
    private ProgressDialog progressDialog;

    /**
     * Provide the login view which will be shown on the login screen for this provider
     *
     * @param fragment   Login fragment. This is needed to inject activity result callbacks
     * @param parentView Parent view in which this view will be inflated.
     * @return Inflated view to be shown on screen
     */
    public View providerView(Fragment fragment, ViewGroup parentView) {
        this.context = fragment.getContext();
        this.fragment = fragment;

        themeHelper = new ThemeHelper();

        View view = inflateView(parentView);
        View actionView = view.findViewById(getActionButtonId());
        if (actionView == null) {
            throw new RuntimeException("BaseProvider view needs a button with id R.id.login");
        }

        view.findViewById(getActionButtonId()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAction();
            }
        });

        return view;
    }

    /**
     * Override this if you want a custom id for your login button
     *
     * @return Login button id
     */
    protected int getActionButtonId() {
        return R.id.action_button;
    }

    /**
     * Override this if you want to listen to the onActivityResult of the parent fragment
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    /**
     * Inflate your login view here
     *
     * @param parentView Parent view
     * @return Inflated view
     */
    abstract protected View inflateView(ViewGroup parentView);

    /**
     * Perform login here. Implementations should take care of showing loading overlay to block
     * out UI
     */
    protected abstract void performAction();

    public int getContainerId() {
        return DEFAULT_CONTAINER_ID;
    }

    public boolean isInProgress() {
        return false;
    }

    protected void showProgressDialog(String message) {
        hideProgressDialog();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
