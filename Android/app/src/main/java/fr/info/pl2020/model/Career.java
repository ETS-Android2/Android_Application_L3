package fr.info.pl2020.model;

import java.util.List;

public class Career {

    private final int id;
    private String name;
    private boolean isPublicCareer;
    private boolean isMainCareer;

    private List<TeachingUnit> teachingUnits;

    public enum ExportFormat {
        TXT,
        PDF
    }

    public Career() {
        this.id = 0;
    }

    public Career(int id, String name, boolean isPublic, boolean isMainCareer, List<TeachingUnit> teachingUnits) {
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

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublicCareer() {
        return isPublicCareer;
    }

    public void setPublicCareer(boolean publicCareer) {
        isPublicCareer = publicCareer;
    }

    public boolean isMainCareer() {
        return isMainCareer;
    }

    public void setMainCareer(boolean mainCareer) {
        isMainCareer = mainCareer;
    }

    public List<TeachingUnit> getTeachingUnits() {
        return teachingUnits;
    }

    public void setTeachingUnits(List<TeachingUnit> teachingUnits) {
        this.teachingUnits = teachingUnits;
    }
}
