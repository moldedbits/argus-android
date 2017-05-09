package com.moldedbits.argus.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by abhishek
 * on 09/05/17.
 */

public class RegexValidator implements Validator {

    @Setter @Getter
    private Pattern pattern;

    public RegexValidator(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean validate(String str) {
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
