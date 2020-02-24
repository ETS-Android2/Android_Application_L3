package fr.info.pl2020.plplg.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Semester {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Transient
    private List<Category> listCat;

    public Semester() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Category> getListCat() {
        return listCat;
    }

    public void setListCat(List<Category> listCat) {
        this.listCat = listCat;
    }
}
