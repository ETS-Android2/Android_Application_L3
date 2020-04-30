package fr.info.pl2020.plplg.entity;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(hidden = true)
    @ManyToOne
    private Student student;

    @Column
    private String name;

    @ApiModelProperty(hidden = true)
    @ManyToMany
    @JoinTable(
            name = "career_content",
            joinColumns = {@JoinColumn(name = "career_id", referencedColumnName = "id")},
            uniqueConstraints = @UniqueConstraint(name = "pk", columnNames = {"career_id", "teaching_unit_id"}),
            inverseJoinColumns = {@JoinColumn(name = "teaching_unit_id", referencedColumnName = "id", nullable = false)}
    )
    private List<TeachingUnit> teachingUnits;

    @Column
    private boolean shared;

    @Column
    private boolean mainCareer;

    public Career() {
    }

    public Career(String name, List<TeachingUnit> teachingUnits, boolean isPublic, boolean isMainCareer) {
        this.name = name;
        this.teachingUnits = teachingUnits;
        this.shared = isPublic;
        this.mainCareer = isMainCareer;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TeachingUnit> getTeachingUnits() {
        return this.teachingUnits;
    }

    public void setTeachingUnits(List<TeachingUnit> teachingUnits) {
        this.teachingUnits = teachingUnits;
    }

    public boolean isPublic() {
        return this.shared;
    }

    public void setPublic(boolean aPublic) {
        this.shared = aPublic;
    }

    public boolean isMainCareer() {
        return this.mainCareer;
    }

    public void setMainCareer(boolean mainCareer) {
        this.mainCareer = mainCareer;
    }
}
