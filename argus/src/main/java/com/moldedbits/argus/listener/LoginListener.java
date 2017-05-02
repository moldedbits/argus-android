package com.moldedbits.argus.listener;

import com.moldedbits.argus.model.IArgusUser;

public interface LoginListener {
    void onLoginSuccess(IArgusUser user);

    void onLoginFailure(String message);
}
