package com.moldedbits.argus.listener;

import com.moldedbits.argus.model.ArgusUser;

public interface SignUpListener {
    void onSignUpSuccess(ArgusUser user);
    void onSignUpFailure(String message);
}
