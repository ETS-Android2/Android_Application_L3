package fr.info.pl2020.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TeachingUnitListContent {

    public static final Map<Integer, TeachingUnit> TEACHING_UNITS = new HashMap<>();
    private static final Map<String, Set<TeachingUnit>> TEACHING_UNIT_BY_CATEGORY = new TreeMap<>();
    private static int lastOpenedTeachingUnit;

    public static void clear() {
        TEACHING_UNITS.clear();
        TEACHING_UNIT_BY_CATEGORY.values().forEach(Set::clear);
    }

    public static void addItem(TeachingUnit item) {
        TEACHING_UNITS.put(item.id, item);
        if (TEACHING_UNIT_BY_CATEGORY.containsKey(item.category)) {
            TEACHING_UNIT_BY_CATEGORY.get(item.category).add(item);
        } else {
            TEACHING_UNIT_BY_CATEGORY.put(item.category, new TreeSet<>(Collections.singleton(item)));
        }
    }

    public static Map<String, List<TeachingUnit>> getTeachingUnitByCategory() {
        Map<String, List<TeachingUnit>> listMap = new TreeMap<>();
        TEACHING_UNIT_BY_CATEGORY.entrySet().forEach(entry -> listMap.put(entry.getKey(), new ArrayList<TeachingUnit>(entry.getValue())));
        return listMap;
    }

    public static int getLastOpenedTeachingUnit() {
        return lastOpenedTeachingUnit;
    }

    public static void setLastOpenedTeachingUnit(int lastOpenedTeachingUnit) {
        TeachingUnitListContent.lastOpenedTeachingUnit = lastOpenedTeachingUnit;
    }

    public static class TeachingUnit implements Comparable<TeachingUnit> {

        private final int id;
        private final String name;
        private final String code;
        private final String description;
        private final int semester;
        private final String category;
        private boolean selected;

        public TeachingUnit(int id, String name, String code, String description, int semester, String category) {
            this.id = id;
            this.name = name;
            this.code = code;
            this.semester = semester;
            this.description = description;
            this.category = category;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public int getSemester() {
            return semester;
        }

        public String getCategory() {
            return category;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public int compareTo(TeachingUnit o) {
            return this.name.compareToIgnoreCase(o.name);
        }
    }
}
