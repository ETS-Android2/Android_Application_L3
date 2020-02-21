package fr.info.pl2020.plplg.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Semester {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    public Semester() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
