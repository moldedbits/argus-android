package com.moldedbits.argus;

import com.moldedbits.argus.storage.ArgusStorage;

/**
 * Helper to access Session information
 */
class ArgusSessionManager {

    private static final String KEY_ARGUS_STATE = "argus_state";

    /**
     * Responsible for storing all data in shared preferences
     */
    private ArgusStorage argusStorage;

    ArgusSessionManager(ArgusStorage argusStorage) {
        this.argusStorage = argusStorage;
    }

    /**
     * Is a user currently logged in.
     *
     * @return True if a user is logged in, false otherwise
     */
    boolean isLoggedIn() {
        return false;
    }

    /**
     * Get the currently logged in user.
     *
     * @return Currently logged in user, or null if no user is logged in.
     */

    State getCurrentState() {
        return State.valueOf(argusStorage.getString(KEY_ARGUS_STATE,
                                                    State.SIGNED_OUT.toString()));
    }

    void setCurrentState(State state) {
        argusStorage.putString(KEY_ARGUS_STATE, state.toString());
    }
}
