package com.moldedbits.argus;

import android.support.annotation.Nullable;

import com.moldedbits.argus.model.IArgusUser;

/**
 * Helper to access Session information
 */
public class ArgusSessionManager {

    /**
     * Static user object only for DEMO. This will be stored in a secure storage in the actual
     * implementation.
     */
    static IArgusUser currentUser;

    /**
     * Is a user currently logged in.
     *
     * @return True if a user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return false;
    }

    /**
     * Get the currently logged in user.
     *
     * @return Currently logged in user, or null if no user is logged in.
     */
    @Nullable
    public static IArgusUser getCurrentUser() {
        return currentUser;
    }

    static void setCurrentUser(IArgusUser user) {
        currentUser = user;
    }
}
