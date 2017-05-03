package com.moldedbits.argus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.LoginProvider;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Single contact point for client
 */
public class Argus {

    private static Argus _instance;
    private ArgusSessionManager argusSessionManager;

    @Getter
    private NextScreenProvider nextScreenProvider;

    @Getter
    private List<LoginProvider> loginProviders;

    @Getter
    private int loginLayout;

    /**
     * @param context is needed here in order to initialize ArgusSessionManager which in turn needs
     *                it to initialize ArgusStorage
     */
    private Argus(@NonNull final Context context) {
        argusSessionManager = new ArgusSessionManager(context);
    }

    public static void initialize(Argus argus) {
        if (_instance != null) {
            throw new RuntimeException("Argus is already initialized");
        }
        _instance = argus;
    }

    public static Argus getInstance() {
        return _instance;
    }

    public void loginUser(ArgusUser user) {
        argusSessionManager.setCurrentUser(user);
    }

    public static class Builder {

        private Argus argus;

        public Builder(final Context context) {
            if(context == null) {
                throw new IllegalArgumentException("Context cannot be null");
            }
            argus = new Argus(context);
        }

        public Builder nextScreenProvider(NextScreenProvider provider) {
            argus.nextScreenProvider = provider;
            return this;
        }

        public Builder setLoginLayout(int layout) {
            argus.loginLayout = layout;
            return this;
        }

        public Builder loginProvider(LoginProvider provider) {
            if (argus.loginProviders == null) {
                argus.loginProviders = new ArrayList<>();
            }
            argus.loginProviders.add(provider);
            return this;
        }

        public Builder loginProvider(List<LoginProvider> providers) {
            if (argus.loginProviders == null) {
                argus.loginProviders = new ArrayList<>();
            }
            argus.loginProviders.addAll(providers);
            return this;
        }

        public Argus build() {
            return argus;
        }
    }

    /**
     * Is a user currently logged in.
     *
     * @return True if a user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return argusSessionManager.isLoggedIn();
    }

    /**
     * Get the currently logged in user.
     *
     * @return Currently logged in user, or null if no user is logged in.
     */
    @Nullable
    public ArgusUser getCurrentUser() {
        return argusSessionManager.getCurrentUser();
    }

    void setCurrentUser(ArgusUser user) {
        argusSessionManager.setCurrentUser(user);
    }
}
