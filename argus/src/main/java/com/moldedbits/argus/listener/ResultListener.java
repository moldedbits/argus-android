package com.moldedbits.argus.listener;

import com.moldedbits.argus.model.ArgusUser;

public interface ResultListener {
    enum ResultState {
        SIGN_UP,
        PHONE_VALIDATION,
        LOGIN,
        FORGOT_PASSWORD
    }

    void onSuccess(ArgusUser user, ResultState resultState);
    void onFailure(String message, ResultState resultState);
}
