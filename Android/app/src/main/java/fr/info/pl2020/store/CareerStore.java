package fr.info.pl2020.store;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

import fr.info.pl2020.model.Career;
import fr.info.pl2020.model.TeachingUnit;
import fr.info.pl2020.util.FunctionsUtils;

public class CareerStore {

    private static Career CURRENT_CAREER;

    public static Career getCurrentCareer() {
        return CURRENT_CAREER;
    }

    public static void setCurrentCareer(@NonNull Career currentCareer) {
        CURRENT_CAREER = currentCareer;
    }

    public static void clear() {
        CURRENT_CAREER = null;
    }

    public static Map<Integer, List<TeachingUnit>> getTeachingUnitBySemester() {
        return FunctionsUtils.groupTeachingUnitBySemester(CURRENT_CAREER.getTeachingUnits());
    }

    public static boolean isCurrentCareerContainsTeachingUnit(int teachingUnitId) {
        return CURRENT_CAREER.getTeachingUnits().stream().anyMatch(teachingUnit -> teachingUnit.getId() == teachingUnitId);
    }

    public static void addTeachingUnit(TeachingUnit teachingUnit) {
        CURRENT_CAREER.getTeachingUnits().add(teachingUnit);
    }
}
