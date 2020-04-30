package fr.info.pl2020.util;

import android.widget.TextView;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.info.pl2020.model.TeachingUnit;

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
        for (Object entry : set) {
            if (entry.equals(value)) {
                return result;
            }
            result++;
        }
        return -1;
    }

    public static String readTextView(TextView v) {
        return new String(v.getText().toString().trim().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    public static Map<Integer, List<TeachingUnit>> groupTeachingUnitBySemester(Collection<TeachingUnit> teachingUnitList) {
        Map<Integer, List<TeachingUnit>> listMap = new TreeMap<>();
        teachingUnitList.forEach(teachingUnit -> {
            if (!listMap.containsKey(teachingUnit.getSemester())) {
                listMap.put(teachingUnit.getSemester(), new ArrayList<>());
            }
            listMap.get(teachingUnit.getSemester()).add(teachingUnit);
        });
        return listMap;
    }
}
