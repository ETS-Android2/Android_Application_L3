package fr.info.pl2020.plplg.export;

import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import fr.info.pl2020.plplg.entity.*;
import fr.info.pl2020.plplg.util.FunctionsUtils;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExportFacade {

    private static Logger logger = Logger.getLogger(ExportFacade.class);

    public ExportFacade() {
    }

    public enum ExportType {
        TXT,
        PDF;
    }

    public File exportCareerToPdf(Career career) throws IOException {
        try {
            Student student = career.getStudent();
            String[] recipientInfos = new String[]{student.getLastName().toUpperCase() + " " + student.getFirstName().toUpperCase(), student.getEmail()};
            Map<String, List<Paragraph>> tuMap = new TreeMap<>();
            for (TeachingUnit tu : career.getTeachingUnits()) {
                String key = tu.getSemester().getName() + " :";
                if (!tuMap.containsKey(key)) {
                    tuMap.put(key, new ArrayList<>());
                }
                Paragraph p = new Paragraph()
                        .add(new Text(tu.getName() + " (" + tu.getCode() + ")").addStyle(new Style().setMarginBottom(4).setUnderline()))
                        .add(new Text(" :\n").addStyle(new Style().setMarginBottom(4)))
                        .add(new Text(tu.getDescription()).addStyle(new Style().setFontSize(8).setTextAlignment(TextAlignment.JUSTIFIED)))
                        .setFixedLeading(10)
                        .setMarginBottom(10);

                tuMap.get(key).add(p);
            }

            return new PdfBuilder()
                    .setSenderInfos(PdfBuilder.DEFAULT_SENDER_INFOS)
                    .setRecipientInfos(recipientInfos)
                    .setSubject("Votre parcours")
                    .startBody()
                    .add(tuMap.isEmpty() ? "Votre parcours est actuellement vide." : "Vous trouverez ci-dessous le parcours que vous avez sélectionné :")
                    .addBlank()
                    .add(tuMap)
                    .endBody()
                    .build();

        } catch (IOException e) {
            logger.error("exportCareerToPdf(" + career.getId() + ") - La création du pdf a échoué.", e);
            throw e;
        }
    }

    public File exportCareerToTxt(Career career) throws IOException {
        try {
            return new TxtBuilder().addCareer(career).build();
        } catch (IOException e) {
            logger.error("exportCareerToTxt(" + career.getId() + ") - La création du fichier texte a échoué.", e);
            throw e;
        }
    }

    public void sendCareerByMail(Career career) throws Exception {
        Student student = career.getStudent();
        if (student == null || FunctionsUtils.isNullOrBlank(student.getEmail())) {
            logger.error("sendCareerByMail(" + career.getId() + ") - L'étudiant ou l'adresse email de l'étudiant est null.");
            throw new Exception("Echec de l'envoi du mail : l'adresse de destination n'existe pas.");
        }

        EmailBuilder emailBuilder = new EmailBuilder();
        emailBuilder.setTo(student.getEmail());
        emailBuilder.setSubject("Votre parcours enregistré : " + career.getName());
        emailBuilder.setBody("Madame, Monsieur,\nVous avez demandé l'envoi de votre parcours '" + career.getName() + "' par email. Vous trouverez ce parcours en pièce-jointe.\n\nCordialement\n\n\nCe message est envoyé automatiquement, merci de ne pas y répondre.");
        emailBuilder.addAttachment(exportCareerToPdf(career));
        emailBuilder.send();
    }

    public static void main(String[] args) {
        Student s = new Student("Kilian", "Clark", "toto@gmail.com", "");
        Semester se = new Semester();
        se.setName("Semestre 1");
        Semester se2 = new Semester();
        se2.setName("Semestre 2");
        Category c = new Category();
        List<TeachingUnit> teachingUnitList = Stream.of(new TeachingUnit("Structure Microscopique de la Matière", "SPUC10", "Atome, ion, molécule, gaz, solide sont les constituants et les états de la matière que nous découvrons dans ce module.", se, c),
                new TeachingUnit("Réactions et Réactivites Chimiques", "SPUC20", "L’objectif de l’UE est de mobiliser les connaissances acquises par les étudiants dans les cours de physique-chimie du lycée, pour les approfondir afin d'aborder de nouvelles notions sur la structure et la réactivité chimique.", se, c),
                new TeachingUnit("Méthodes expérimentales", "SLZP56", "L'objectif de cette UE est d'apprendre aux étudiants les techniques et les concepts à la base de la physique expérimentale. L'enseignement est basé sur des manipulations se déroulant sur plusieurs séances, ce qui permettra aux étudiants d'affronter les différents aspects du travail avec un certain recul et des pauses de réflexion. Le but est de transmettre un savoir-faire technique et méthodologique (manipulation, traitement des données, tenue d'un cahier d'expérience, etc...) que les étudiants pourront réinvestir dans des situations nécessitant l'acquisition de données ou de mesures (recherche scientifique, applications industrielles ou autre).", se, c),
                new TeachingUnit("Thermodynamique Chimique", "SPUC21", "La thermodynamique s’intéresse aux transferts thermiques et de travail. Pour compléter les notions vues au lycée à propos des chaleurs de combustion, de dissolution ou de changement d’états, il est nécessaire de définir un certain nombre de termes. ", se2, c),
                new TeachingUnit("Matériaux Terrestres", "SPUT33", "Ce cours aura pour but de présenter l'importance des géomatériaux à travers leur organisation structurale microscopique et les relations entre cette structure et leurs propriétés pour comprendre les processus géochimiques et géophysiques.", se2, c)).collect(Collectors.toList());
        Career ca = new Career("Mon Parcours", teachingUnitList, false, false);
        s.setCareers(Collections.singletonList(ca));
        try {
            new ExportFacade().exportCareerToPdf(ca);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
