package fr.info.pl2020.plplg.export;

import fr.info.pl2020.plplg.entity.Career;
import fr.info.pl2020.plplg.entity.TeachingUnit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class TxtBuilder {

    private final static String LS = System.lineSeparator();
    private final static String LIST_SYMBOL = "-";

    private final File file;
    private StringBuilder content;

    public TxtBuilder() throws IOException {
        this.file = File.createTempFile("plplg", ".txt");
        this.file.deleteOnExit();

        this.content = new StringBuilder();
    }

    public TxtBuilder addCareer(Career career) {
        addCareerHeader(career);
        addCareerContent(career);
        return this;
    }

    public File build() throws IOException {
        FileWriter writer = new FileWriter(this.file);
        writer.write(this.content.toString());
        writer.close();

        return this.file;
    }

    private void addCareerHeader(Career career) {
        this.content.append("Parcours '")
                .append(career.getName())
                .append("' de l'étudiant ")
                .append(career.getStudent().getFirstName())
                .append(" ")
                .append(career.getStudent().getLastName())
                .append(" : ")
                .append(LS).append(LS);
    }

    private void addCareerContent(Career career) {
        if (career.getTeachingUnits().isEmpty()) {
            this.content.append("Ce parcours est vide.").append(LS).append(LS);
            return;
        }

        Map<String, List<TeachingUnit>> teachingUnitBySemester = new TreeMap<>(career.getTeachingUnits().stream().collect(Collectors.groupingBy(t -> t.getSemester().getName())));
        for (Map.Entry<String, List<TeachingUnit>> entry : teachingUnitBySemester.entrySet()) {
            this.content.append(entry.getKey().toUpperCase()).append(" : ").append(LS);
            for (TeachingUnit tu : entry.getValue()) {
                this.content.append(LIST_SYMBOL).append(" ").append(tu.getCode()).append(" : ").append(tu.getName()).append(LS);
            }
            this.content.append(LS);
        }

        this.content.append(LS);
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }

}
