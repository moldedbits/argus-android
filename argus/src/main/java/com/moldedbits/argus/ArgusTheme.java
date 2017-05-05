package com.moldedbits.argus;

import lombok.Getter;

public class ArgusTheme {

    private static ArgusTheme _instance;

    @Getter
    private int buttonColor;
    @Getter
    private int logo;

    public static ArgusTheme getInstance() {
        return _instance;
    }

    public static class Builder {

        ArgusTheme argusTheme;

        public Builder() {
            argusTheme = new ArgusTheme();
        }

        public Builder buttonColor(int color) {
            argusTheme.buttonColor = color;
            return this;
        }

        public Builder logo(int logo) {
            argusTheme.logo = logo;
            return this;
        }

        public ArgusTheme build() {
            ArgusTheme._instance = argusTheme;
            return ArgusTheme.getInstance();
        }
    }
}
