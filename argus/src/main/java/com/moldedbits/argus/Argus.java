package com.moldedbits.argus;

import android.util.Patterns;

import java.util.regex.Pattern;

import lombok.Getter;

/**
 * Main interaction point for Argus
 */
public class Argus {

    public static final Pattern EMAIL = Patterns.EMAIL_ADDRESS;

    private static Argus _instance;

    @Getter private Class nextScreen;

    @Getter private NextScreenProvider nextScreenProvider;

    private Pattern usernameRegex;

    private Pattern passwordRegex;

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

    public static class Builder {

        private Argus argus;

        public Builder() {
            argus = new Argus();
        }

        public Builder nextScreen(Class nextScreen) {
            if (argus.nextScreenProvider != null) {
                throw new RuntimeException("You can only specify one of nextScreen and" +
                        " nextScreenProvider");
            }
            argus.nextScreen = nextScreen;
            return this;
        }

        public Builder nextScreenProvider(NextScreenProvider provider) {
            if (argus.nextScreen != null) {
                throw new RuntimeException("You can only specify one of nextScreen and" +
                        " nextScreenProvider");
            }
            argus.nextScreenProvider = provider;
            return this;
        }

        public Builder usernameRegex(String regex) {
            argus.usernameRegex = Pattern.compile(regex);
            return this;
        }

        public Builder usernameRegex(Pattern pattern) {
            argus.usernameRegex = pattern;
            return this;
        }

        public Builder passwordRegex(String regex) {
            argus.passwordRegex = Pattern.compile(regex);
            return this;
        }

        public Builder passwordRegex(Pattern pattern) {
            argus.passwordRegex = pattern;
            return this;
        }

        public Argus build() {
            if (argus.nextScreen == null && argus.nextScreenProvider == null) {
                throw new RuntimeException("You need to specify exactly one from nextScreen and" +
                        " nextScreenProvider");
            }

            return argus;
        }
    }
}
