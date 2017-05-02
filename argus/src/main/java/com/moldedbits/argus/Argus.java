package com.moldedbits.argus;

import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.model.IArgusUser;
import com.moldedbits.argus.provider.LoginProvider;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Main interaction point for Argus
 */
public class Argus {

    private static Argus _instance;

    @Getter
    private NextScreenProvider nextScreenProvider;

    @Getter
    private List<LoginProvider> loginProviders;

    @Getter
    private int loginLayout;

    @Getter
    private Class<? extends IArgusUser> userClass;

    @Getter
    private ArgusConfig config;

    private Argus() {
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

    public void loginUser(IArgusUser user) {
        ArgusSessionManager.setCurrentUser(user);
    }

    public class ArgusConfig {
        boolean logEnabled;
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

        public Builder userClass(Class<? extends IArgusUser> userClass) {
            try {
                if (userClass.getConstructor() == null) {
                    throw new RuntimeException("IArgusUser implementation needs an" +
                            " empty constructor");
                }
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException("IArgusUser implementation needs an empty constructor");
            }
            argus.userClass = userClass;
            return this;
        }

        public Builder logEnabled(boolean logEnabled) {
            argus.config.logEnabled = logEnabled;
            return this;
        }

        public Argus build() {
            // Use ArgusUser by default
            if (argus.userClass == null) {
                argus.userClass = ArgusUser.class;
            }
            return argus;
        }
    }
}
