package com.moldedbits.argus;

import android.support.annotation.DrawableRes;

import lombok.Getter;

public class ArgusTheme {

    @Getter @DrawableRes
    private int logo;

    @Getter @DrawableRes
    private int backgroundDrawable;

    @Getter @DrawableRes
    private int buttonDrawable;


    public static class Builder {

        public ArgusTheme argusTheme;

        public Builder() {
            argusTheme = new ArgusTheme();
        }

        public Builder buttonDrawable(@DrawableRes int buttonDrawable) {
            argusTheme.buttonDrawable = buttonDrawable;
            return this;
        }

        public Builder logo(@DrawableRes int logo) {
            argusTheme.logo = logo;
            return this;
        }

        public Builder backgroundDrawable(@DrawableRes int backgroundDrawable) {
            argusTheme.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public ArgusTheme build() {
            return argusTheme;
        }
    }
}
