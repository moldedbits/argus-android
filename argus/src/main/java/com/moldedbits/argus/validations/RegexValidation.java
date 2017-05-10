package com.moldedbits.argus.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by abhishek
 * on 09/05/17.
 */

public class RegexValidation extends AbstractValidation {

    @Setter @Getter
    private Pattern pattern;

    public RegexValidation(String regex, String errorMessage) {
        super(errorMessage);
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean validate(String str) {
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
