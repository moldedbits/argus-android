package com.moldedbits.argus.validations;

import android.text.TextUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by abhishek
 * on 09/05/17.
 */

public class LengthValidator implements Validator {
    @Getter @Setter
    private int maxLength;

    @Getter @Setter
    private int minLength;

    public LengthValidator(int min, int max) {
        if(min > max) {
            throw new IllegalArgumentException("Minimum length must be less than max value");
        }

        this.minLength = min;
        this.maxLength = max;
    }

    @Override
    public boolean validate(String str) {
        return isInCharacterLimit(str);
    }

    private boolean isInCharacterLimit(String str) {
        int count = getCharacterCount(str);
        return count >= minLength && count <= maxLength;
    }

    private int getCharacterCount(String str) {
        if(TextUtils.isEmpty(str)) {
            return 0;
        }

        return str.length();
    }
}
