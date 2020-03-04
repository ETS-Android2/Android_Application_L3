package fr.info.pl2020.plplg.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;


    @Transient
    private List<TeachingUnit> listUe;

    @Column(name = "name",length = 50)
    private String name;

    public Category(String name ) {
        this.name=name;
    }
    public Category() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name= name;
    }

    public List<TeachingUnit> getListUe() {
        return listUe;
    }

    public void setListUe(List<TeachingUnit> listUe) {
        this.listUe = listUe;
    }

}
