package fr.info.pl2020.model;

public class TeachingUnit {
    private final int id;
    private final String name;
    private String code;
    private String description;
    private boolean selectedByStudent;

    public TeachingUnit(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TeachingUnit(int id, String name, String code, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSelectedByStudent() {
        return selectedByStudent;
    }

    public void setSelectedByStudent(boolean selectedByStudent) {
        this.selectedByStudent = selectedByStudent;
    }
}
