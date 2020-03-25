package fr.info.pl2020.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionsUtils {

    private FunctionsUtils() {
    }

    private final static String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        Matcher emailMatcher = EMAIL_PATTERN.matcher(email);
        return emailMatcher.matches();
    }

    public static boolean isGoodPassword(String password) {
        //TODO ?
        return true;
    }
}
