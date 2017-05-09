package com.moldedbits.argus;

import android.support.annotation.Nullable;

import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.BaseProvider;
import com.moldedbits.argus.storage.ArgusStorage;

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
    private List<BaseProvider> signupProviders;
    @Getter
    private List<BaseProvider> loginProviders;

    @Getter
    private int loginLayout;
    @Getter
    private int signupLayout;

    @Getter
    private ArgusThemeBuilder argusTheme;

    private Argus() {
    }

    /**
     * Required Argus storage instance in order to store ArgusUser
     */
    private ArgusStorage argusStorage;


    public static Argus getInstance() {
        return _instance;
    }

    public void loginUser(ArgusUser user) {
        argusSessionManager.setCurrentUser(user);
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

    public static class Builder {

        private Argus argus;

        public Builder() {
            argus = new Argus();
        }

        public Builder nextScreenProvider(NextScreenProvider provider) {
            argus.nextScreenProvider = provider;
            return this;
        }

        public Builder loginLayout(int layout) {
            argus.loginLayout = layout;
            return this;
        }

        public Builder signupLayout(int layout) {
            argus.signupLayout = layout;
            return this;
        }

        public Builder loginProvider(BaseProvider provider) {
            if (argus.loginProviders == null) {
                argus.loginProviders = new ArrayList<>();
            }
            argus.loginProviders.add(provider);
            return this;
        }

        public Builder signupProviders(List<BaseProvider> providers) {
            if (argus.signupProviders == null) {
                argus.signupProviders = new ArrayList<>();
            }
            argus.signupProviders = providers;
            return this;
        }

        public Builder loginProviders(List<BaseProvider> providers) {
            if (argus.loginProviders == null) {
                argus.loginProviders = new ArrayList<>();
            }
            argus.loginProviders = providers;
            return this;
        }

        public Builder argusStorage(ArgusStorage argusStorage) {
            if (argusStorage == null) {
                throw new IllegalArgumentException("Argus Storage cannot be null");
            }

            argus.argusStorage = argusStorage;
            argus.argusSessionManager = new ArgusSessionManager(argusStorage);
            return this;
        }

        public Builder theme(ArgusThemeBuilder argusThemeBuilder) {
            argus.argusTheme = argusThemeBuilder;
            return this;
        }

        public Builder loginLogo(int logo) {
            argus.getArgusTheme().logo(logo);
            return this;
        }

        public Argus build() {
            if (argus.argusStorage == null) {
                throw new IllegalStateException("No ArgusStorage was provided.");
            }
            Argus._instance = argus;
            return Argus.getInstance();
        }
    }
}
