package com.moldedbits.argus.listener;

import com.moldedbits.argus.model.ArgusUser;

public interface SignUpListener {

    void onSignupSuccess(ArgusUser user);

    void onSignupFailure();
}
