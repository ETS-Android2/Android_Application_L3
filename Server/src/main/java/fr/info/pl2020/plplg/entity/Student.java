package fr.info.pl2020.plplg.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @ApiModelProperty(hidden = true)
    @OneToMany(mappedBy = "student")
    private List<Career> careers;

    @ApiModelProperty(hidden = true)
    @OneToMany
    private List<TeachingUnit> validateCareer;

    public Student() {
    }

    public Student(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.careers = Collections.emptyList();
        this.validateCareer = Collections.emptyList();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Career> getCareers() {
        return this.careers;
    }

    public void setCareers(List<Career> careers) {
        this.careers = careers;
    }

    public List<TeachingUnit> getValidateCareer() {
        return this.validateCareer;
    }

    public void setValidateCareer(List<TeachingUnit> validateTeachingUnit) {
        this.validateCareer = validateTeachingUnit;
    }
}
