package fr.info.pl2020.plplg.dto;

import fr.info.pl2020.plplg.entity.TeachingUnit;

import java.util.ArrayList;
import java.util.List;

public class TeachingUnitResponse {

    private int id;
    private String name;
    private String code;
    private String description;
    private int semester;
    private String category;

    public TeachingUnitResponse(TeachingUnit teachingUnit) {
        this.id = teachingUnit.getId();
        this.name = teachingUnit.getName();
        this.code = teachingUnit.getCode();
        this.description = teachingUnit.getDescription();
        this.semester = teachingUnit.getSemester().getId();
        this.category = teachingUnit.getCategory().getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static List<TeachingUnitResponse> TeachingUnitListToTeachingUnitResponseList(List<TeachingUnit> teachingUnitList) {
        List<TeachingUnitResponse> teachingUnitResponseList = new ArrayList<>();
        for (TeachingUnit tu : teachingUnitList) {
            teachingUnitResponseList.add(new TeachingUnitResponse(tu));
        }
        return teachingUnitResponseList;
    }
}
