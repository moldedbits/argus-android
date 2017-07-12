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
        return ArgusState.valueOf(argusStorage.getString(KEY_ARGUS_STATE, null)).equals(ArgusState.SIGNED_IN);
    }

    /**
     * Get the currently logged in user.
     *
     * @return Currently logged in user, or null if no user is logged in.
     */

    ArgusState getCurrentState() {
        return ArgusState.valueOf(argusStorage.getString(KEY_ARGUS_STATE,
                ArgusState.SIGNED_OUT.toString()));
    }

    void setCurrentState(ArgusState argusState) {
        argusStorage.putString(KEY_ARGUS_STATE, argusState.toString());
    }
}
