package com.moldedbits.argus.listener;

import com.moldedbits.argus.ArgusState;

public interface ResultListener {
    void onSuccess(ArgusState argusState);

    void onFailure(String message);
}
