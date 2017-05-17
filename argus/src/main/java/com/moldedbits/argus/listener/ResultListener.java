package com.moldedbits.argus.listener;

import com.moldedbits.argus.State;

public interface ResultListener {
    void onSuccess(State resultState);
    void onFailure(String message);
}
