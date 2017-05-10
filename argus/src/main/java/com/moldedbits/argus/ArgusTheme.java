package com.moldedbits.argus;

import lombok.Getter;

public class ArgusTheme {

    @Getter
    private int buttonColor;
    @Getter
    private int logo;


    public static class Builder {

        public ArgusTheme argusTheme;

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
            return argusTheme;
        }
    }
}
