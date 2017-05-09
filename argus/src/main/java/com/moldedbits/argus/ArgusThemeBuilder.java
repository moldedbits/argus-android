package com.moldedbits.argus;

import lombok.Getter;

public class ArgusThemeBuilder {

    @Getter
    private int buttonColor;
    @Getter
    private int logo;

    public ArgusThemeBuilder buttonColor(int color) {
       this.buttonColor = color;
        return this;
    }

    public ArgusThemeBuilder logo(int logo) {
        this.logo = logo;
        return this;
    }

    public ArgusThemeBuilder build() {
        return this;
    }
}
