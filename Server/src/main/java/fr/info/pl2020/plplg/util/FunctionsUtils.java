package fr.info.pl2020.plplg.util;

public class FunctionsUtils {

    private FunctionsUtils() {
    }

    public static boolean isNullOrBlank(String s) {
        return (s == null || s.trim().isEmpty());
    }
}
