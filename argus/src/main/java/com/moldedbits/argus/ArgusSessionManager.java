package com.moldedbits.argus;

import android.support.annotation.Nullable;

import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.storage.ArgusStorage;

/**
 * Helper to access Session information
 */
class ArgusSessionManager {

    /**
     * Responsible for storing all data in shared preferences
     */
    private ArgusStorage argusStorage;

    ArgusSessionManager(ArgusStorage argusStorage) {
        this.argusStorage = argusStorage;
    }

    /**
     * Is a user currently logged in.
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
    @Nullable
    ArgusUser getCurrentUser() {
        return argusStorage.getCurrentUser();
    }

    void setCurrentUser(ArgusUser user) {
        argusStorage.setCurrentUser(user);
    }
}
