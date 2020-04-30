package fr.info.pl2020.model;

public class TeachingUnit implements Comparable<TeachingUnit> {

    private final int id;
    private final String name;
    private final String code;
    private final String description;
    private final int semester;
    private final String category;
    private boolean selected;

    public TeachingUnit(int id, String name, String code, String description, int semester, String category) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.semester = semester;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getSemester() {
        return semester;
    }

    public String getCategory() {
        return category;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public int compareTo(TeachingUnit o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
