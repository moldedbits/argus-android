package com.moldedbits.argus.listener;

import com.moldedbits.argus.ArgusState;
import com.moldedbits.argus.model.ArgusUser;

public interface ResultListener {
    void onSuccess(ArgusUser user, ArgusState resultState);
    void onFailure(String message, ArgusState resultState);
}
