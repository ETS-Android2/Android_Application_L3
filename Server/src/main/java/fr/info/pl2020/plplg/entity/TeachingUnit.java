package fr.info.pl2020.plplg.entity;

import javax.persistence.*;

@Entity
public class TeachingUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name="semester", nullable = false, referencedColumnName = "id")
    private Semester semester;

    @ManyToOne
    @JoinColumn(name="category", nullable = false, referencedColumnName = "id")
    private Category category;

    public TeachingUnit() {
    }
    public TeachingUnit(String name,String code,Semester semester,Category category ) {
        this.name=name;
        this.code=code;
        this.semester=semester;
        this.category=category;

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

    public void setCode(String reference) {
        this.code = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
