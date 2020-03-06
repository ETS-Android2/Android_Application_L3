package fr.info.pl2020.model;

public class Semester {

    private final int id;
    private final String name;

    public Semester(int id) {
        this.id = id;
        this.name = "Semestre n°" + id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
