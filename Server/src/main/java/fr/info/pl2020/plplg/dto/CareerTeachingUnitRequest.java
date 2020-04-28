package fr.info.pl2020.plplg.dto;

import javax.validation.constraints.Positive;

public class CareerTeachingUnitRequest {

    @Positive
    private int teachingUnitId;

    public CareerTeachingUnitRequest() {
    }

    public CareerTeachingUnitRequest(@Positive int teachingUnitId) {
        this.teachingUnitId = teachingUnitId;
    }

    public int getTeachingUnitId() {
        return this.teachingUnitId;
    }

    public void setTeachingUnitId(int teachingUnitId) {
        this.teachingUnitId = teachingUnitId;
    }
}
