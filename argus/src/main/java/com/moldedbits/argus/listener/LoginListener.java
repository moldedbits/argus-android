package com.moldedbits.argus.listener;

import com.moldedbits.argus.model.ArgusUser;

public interface LoginListener {
    void onLoginSuccess(ArgusUser user);

    void onLoginFailure(String message);
}
