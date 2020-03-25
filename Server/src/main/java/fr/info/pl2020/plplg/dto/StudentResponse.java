package fr.info.pl2020.plplg.dto;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;

import java.util.List;

public class StudentResponse {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<TeachingUnitResponse> career;

    public StudentResponse(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
        this.career = TeachingUnitResponse.TeachingUnitListToTeachingUnitResponseList(student.getCareer());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TeachingUnitResponse> getCareer() {
        return career;
    }

    public void setCareer(List<TeachingUnitResponse> career) {
        this.career = career;
    }
}
