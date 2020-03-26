package fr.info.pl2020.plplg.dto;

import javax.validation.constraints.Positive;

public class CareerRequest {

    @Positive
    private int teachingUnitId;

    public CareerRequest() {
    }

    public CareerRequest(@Positive int teachingUnitId) {
        this.teachingUnitId = teachingUnitId;
    }

    public int getTeachingUnitId() {
        return teachingUnitId;
    }

    public void setTeachingUnitId(int teachingUnitId) {
        this.teachingUnitId = teachingUnitId;
    }
}
