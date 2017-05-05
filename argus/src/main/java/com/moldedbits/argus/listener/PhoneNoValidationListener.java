package com.moldedbits.argus.listener;

import com.moldedbits.argus.model.ArgusUser;

public interface PhoneNoValidationListener {
    void onPhoneValidationSuccess(ArgusUser user);
    void onPhoneValidationFailure(String message);
}
