package com.moldedbits.argus;

import android.support.annotation.Nullable;

import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.storage.ArgusStorage;

import java.util.List;

/**
 * Helper to access Session information
 */
class ArgusSessionManager {

    private static final String KEY_ARGUS_STATE = "argus_state";

    private static final String KEY_PROVIDER_IN_PROGRESS = "provider_in_progress";

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
    @Nullable
    ArgusUser getCurrentUser() {
        return argusStorage.getCurrentUser();
    }

    void setCurrentUser(ArgusUser user) {
        argusStorage.setCurrentUser(user);
    }

    ArgusState getCurrentState() {
        return ArgusState.valueOf(argusStorage.getString(KEY_ARGUS_STATE,
                                                         ArgusState.SIGNED_OUT.toString()));
    }

    void setCurrentState(ArgusState argusState) {
        argusStorage.putString(KEY_ARGUS_STATE, argusState.toString());
    }

    void setProviderInProgres(BaseProvider providerInProgres) {
        argusStorage.putString(KEY_PROVIDER_IN_PROGRESS, providerInProgres.getClass().getName());
    }

    BaseProvider getProviderInProgress(List<BaseProvider> loginProviders,
                                       List<BaseProvider> signupProviders) {
        String providerClass = argusStorage.getString(KEY_PROVIDER_IN_PROGRESS, null);

        for (BaseProvider provider : loginProviders) {
            if (provider.getClass().getName().equals(providerClass)) {
                return provider;
            }
        }
        for (BaseProvider provider : signupProviders) {
            if (provider.getClass().getName().equals(providerClass)) {
                return provider;
            }
        }

        return null;
    }
}
