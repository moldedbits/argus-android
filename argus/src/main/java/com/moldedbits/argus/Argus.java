package com.moldedbits.argus;

import com.moldedbits.argus.model.ArgusUser;
import com.moldedbits.argus.provider.login.LoginProvider;
import com.moldedbits.argus.provider.signup.SignupProvider;

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
    private int signupLayout;

    @Getter
    private List<SignupProvider> signupProviders;

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

    public void loginUser(ArgusUser user) {
        ArgusSessionManager.setCurrentUser(user);
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

        public Builder setSignupLayout(int layout) {
            argus.signupLayout = layout;
            return this;
        }

        public Builder loginProvider(LoginProvider provider) {
            if (argus.loginProviders == null) {
                argus.loginProviders = new ArrayList<>();
            }
            argus.loginProviders.add(provider);
            return this;
        }

        public Builder signupProvider(List<SignupProvider> provider) {
           if(argus.signupProviders == null) {
               argus.signupProviders = new ArrayList<>();
           }
           argus.signupProviders = provider;
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
}
