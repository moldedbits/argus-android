package com.moldedbits.argus.listener;

import com.moldedbits.argus.ArgusState;

public interface ResultListener {
    void onSuccess(ArgusState resultState);
    void onFailure(String message, ArgusState resultState);
}
