package fr.info.pl2020.plplg.dto;

import fr.info.pl2020.plplg.entity.Career;

import java.util.ArrayList;
import java.util.List;

public class CareerResponse {

    private int id;
    private String name;
    private List<TeachingUnitResponse> teachingUnits;
    private boolean isPublic;
    private boolean isMainCareer;
    private Owner owner;

    private static class Owner {
        private String firstname;
        private String lastname;

        public Owner(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public String getFirstname() {
            return this.firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return this.lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }
    }

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

    public Owner getOwner() {
        return this.owner;
    }

    public void setOwner(String firstname, String lastname) {
        this.owner = new Owner(firstname, lastname);
    }

    public static List<CareerResponse> careerListToCareerResponseList(List<Career> careerList) {
        return careerListToCareerResponseList(careerList, false);
    }

    public static List<CareerResponse> careerListToCareerResponseList(List<Career> careerList, boolean setOwner) {
        List<CareerResponse> careerResponseList = new ArrayList<>();
        for (Career c : careerList) {
            CareerResponse cr = new CareerResponse(c);
            if (setOwner) {
                cr.setOwner(c.getStudent().getFirstName(), c.getStudent().getLastName());
            }
            careerResponseList.add(cr);
        }
        return careerResponseList;
    }
}
