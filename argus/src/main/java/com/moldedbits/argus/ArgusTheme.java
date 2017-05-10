package com.moldedbits.argus;

import lombok.Getter;

public class ArgusTheme {

    @Getter
    private int buttonColor;
    @Getter
    private int logo;

    public ArgusTheme buttonColor(int color) {
       this.buttonColor = color;
        return this;
    }

    public ArgusTheme logo(int logo) {
        this.logo = logo;
        return this;
    }

    public ArgusTheme build() {
        return this;
    }
}
