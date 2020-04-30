package fr.info.pl2020.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import fr.info.pl2020.model.TeachingUnit;
import fr.info.pl2020.util.FunctionsUtils;

public class TeachingUnitListStore {

    public static final Map<Integer, TeachingUnit> TEACHING_UNITS = new HashMap<>();
    private static final Map<String, Set<TeachingUnit>> TEACHING_UNIT_BY_CATEGORY = new TreeMap<>();
    private static int lastOpenedTeachingUnit;

    public static void clear() {
        TEACHING_UNITS.clear();
        TEACHING_UNIT_BY_CATEGORY.clear();
    }

    public static void addItem(TeachingUnit item) {
        TEACHING_UNITS.put(item.getId(), item);
        if (TEACHING_UNIT_BY_CATEGORY.containsKey(item.getCategory())) {
            TEACHING_UNIT_BY_CATEGORY.get(item.getCategory()).add(item);
        } else {
            TEACHING_UNIT_BY_CATEGORY.put(item.getCategory(), new TreeSet<>(Collections.singleton(item)));
        }
    }

    public static Map<String, List<TeachingUnit>> getTeachingUnitByCategory() {
        Map<String, List<TeachingUnit>> listMap = new TreeMap<>();
        TEACHING_UNIT_BY_CATEGORY.entrySet().forEach(entry -> listMap.put(entry.getKey(), new ArrayList<TeachingUnit>(entry.getValue())));
        return listMap;
    }

    public static Map<Integer, List<TeachingUnit>> getTeachingUnitBySemester() {
        return FunctionsUtils.groupTeachingUnitBySemester(TEACHING_UNITS.values());
    }

    public static int getLastOpenedTeachingUnit() {
        return lastOpenedTeachingUnit;
    }

    public static void setLastOpenedTeachingUnit(int lastOpenedTeachingUnit) {
        TeachingUnitListStore.lastOpenedTeachingUnit = lastOpenedTeachingUnit;
    }

}
