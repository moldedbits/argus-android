package com.moldedbits.argus;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.view.View;

import lombok.Getter;

public class ArgusTheme {

    @Getter
    @DrawableRes
    private int logo;

    @Getter
    @DrawableRes
    private int backgroundDrawable;

    @Getter
    @DrawableRes
    private int buttonDrawable;

    @Getter
    private String welcomeText;

    @Getter
    private float welcomeTextSize;

    @Getter
    private int welcomeTextVisibility;

    @Getter
    @ColorInt
    private int welcomeTextColor;

    @Getter
    private boolean showEditTextDrawables;

    public static class Builder {

        ArgusTheme argusTheme;

        public Builder() {
            argusTheme = new ArgusTheme();
            argusTheme.welcomeTextSize = 14;
            argusTheme.welcomeTextColor = Color.BLACK;
            argusTheme.welcomeTextVisibility = View.VISIBLE;
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

        public Builder welcomeText(String welcomeText) {
            argusTheme.welcomeText = welcomeText;
            return this;
        }

        public Builder welcomeTextSize(float welcomeTextSize) {
            argusTheme.welcomeTextSize = welcomeTextSize;
            return this;
        }

        public Builder welcomeTextColor(int welcomeTextColor) {
            argusTheme.welcomeTextColor = welcomeTextColor;
            return this;
        }

        public Builder showEditTextDrawables(boolean show) {
            argusTheme.showEditTextDrawables = show;
            return this;
        }

        public Builder showWelcomeText() {
            argusTheme.welcomeTextVisibility = View.VISIBLE;
            return this;
        }

        public Builder hideWelcomeText() {
            argusTheme.welcomeTextVisibility = View.GONE;
            return this;
        }

        public ArgusTheme build() {
            return argusTheme;
        }
    }
}
