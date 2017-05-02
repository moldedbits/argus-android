package com.moldedbits.argus;

import android.support.annotation.Nullable;

import com.moldedbits.argus.model.ArgusUser;

/**
 * Helper to access Session information
 */
public class ArgusSessionManager {

    /**
     * Static user object only for DEMO. This will be stored in a secure storage in the actual
     * implementation.
     */
    static ArgusUser currentUser;
    static boolean isLoggedIn;

    /**
     * Is a user currently logged in.
     *
     * @return True if a user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Get the currently logged in user.
     *
     * @return Currently logged in user, or null if no user is logged in.
     */
    @Nullable
    public static ArgusUser getCurrentUser() {
        return currentUser;
    }

    static void setCurrentUser(ArgusUser user) {
        currentUser = user;
    }

    //TODO will moved to local storage
    public static void setIsLoggedIn(boolean isLoggedIn) {
        ArgusSessionManager.isLoggedIn = isLoggedIn;
    }
}
