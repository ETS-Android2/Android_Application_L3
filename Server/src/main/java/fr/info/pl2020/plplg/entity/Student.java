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

    @Column
    private String email;

    @Column
    private String password;

    @ApiModelProperty(hidden = true)
    @ManyToMany
    @JoinTable(
            name = "career",
            joinColumns = {@JoinColumn(name = "student_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "teaching_unit_id", referencedColumnName = "id", nullable = false)}
    )
    private List<TeachingUnit> career;

    public Student() {
    }

    public Student(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.career = Collections.emptyList();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TeachingUnit> getCareer() {
        return career;
    }

    public void setCareer(List<TeachingUnit> career) {
        this.career = career;
    }
}
