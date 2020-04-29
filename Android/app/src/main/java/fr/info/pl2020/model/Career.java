package fr.info.pl2020.model;

import java.util.List;

public class Career {

    private final int id;
    private final String name;
    private final boolean isPublicCareer;
    private final boolean isMainCareer;
    private List<TeachingUnitListContent.TeachingUnit> teachingUnits;

    public enum ExportFormat {
        TXT,
        PDF
    }

    public Career(int id, String name, boolean isPublic, boolean isMainCareer, List<TeachingUnitListContent.TeachingUnit> teachingUnits) {
        this.id = id;
        this.name = name;
        this.isPublicCareer = isPublic;
        this.isMainCareer = isMainCareer;
        this.teachingUnits = teachingUnits;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isPublicCareer() {
        return isPublicCareer;
    }

    public boolean isMainCareer() {
        return isMainCareer;
    }
}
