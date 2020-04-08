package fr.info.pl2020.util;

import java.util.Set;
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

    public static boolean isNullOrBlank(String s) {
        return (s == null || s.trim().isEmpty());
    }

    public static int getIndex(Set<?> set, Object value) {
        int result = 0;
        for (Object entry:set) {
            if (entry.equals(value)) return result;
            result++;
        }
        return -1;
    }
}
