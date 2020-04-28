package fr.info.pl2020.plplg.dto;

public class CareerRequest {

    private String name;
    private boolean isPublic;
    private boolean mainCareer;

    public CareerRequest(String name, boolean isPublic, boolean mainCareer) {
        this.name = name;
        this.isPublic = isPublic;
        this.mainCareer = mainCareer;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public void setPublic(boolean aPublic) {
        this.isPublic = aPublic;
    }

    public boolean isMainCareer() {
        return this.mainCareer;
    }

    public void setMainCareer(boolean mainCareer) {
        this.mainCareer = mainCareer;
    }
}
