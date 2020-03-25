package fr.info.pl2020.plplg.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TeachingUnitRequest {

    private String name;
    private String code;

    @Size(max = 1024)
    private String description;

    @Positive
    private int semesterId;

    @Positive
    private int categoryId;

    public TeachingUnitRequest() {
    }

    public TeachingUnitRequest(String name, String code, @Size(max = 1024) String description, @Positive int semesterId, @Positive int categoryId) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.semesterId = semesterId;
        this.categoryId = categoryId;
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

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
