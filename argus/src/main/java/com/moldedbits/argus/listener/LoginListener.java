package com.moldedbits.argus.listener;

import com.moldedbits.argus.model.ArgusUser;

public interface LoginListener {
    void onSuccess(ArgusUser user);

    void onFailure(String message);
}
