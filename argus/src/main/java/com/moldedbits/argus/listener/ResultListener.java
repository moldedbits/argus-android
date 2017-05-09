package com.moldedbits.argus.listener;

import com.moldedbits.argus.model.ArgusUser;

public interface ResultListener {
    enum ResultState {
        SIGNED_UP,
        PHONE_VALIDATED,
        SIGNED_IN
    }

    void onSuccess(ArgusUser user, ResultState resultState);
    void onFailure(String message, ResultState resultState);
}
