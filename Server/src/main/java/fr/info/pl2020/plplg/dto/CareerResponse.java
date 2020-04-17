package fr.info.pl2020.plplg.dto;

import fr.info.pl2020.plplg.entity.Career;

import java.util.List;

public class CareerResponse {

    private int id;
    private String name;
    private List<TeachingUnitResponse> teachingUnits;
    private boolean isPublic;
    private boolean isMainCareer;

    public CareerResponse(Career career) {
        this.id = career.getId();
        this.name = career.getName();
        this.teachingUnits = TeachingUnitResponse.TeachingUnitListToTeachingUnitResponseList(career.getTeachingUnits());
        this.isPublic = career.isPublic();
        this.isMainCareer = career.isMainCareer();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TeachingUnitResponse> getTeachingUnits() {
        return this.teachingUnits;
    }

    public void setTeachingUnits(List<TeachingUnitResponse> teachingUnits) {
        this.teachingUnits = teachingUnits;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public void setPublic(boolean aPublic) {
        this.isPublic = aPublic;
    }

    public boolean isMainCareer() {
        return this.isMainCareer;
    }

    public void setMainCareer(boolean mainCareer) {
        this.isMainCareer = mainCareer;
    }
}
