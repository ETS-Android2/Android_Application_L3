package fr.info.pl2020.plplg.export;

import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import fr.info.pl2020.plplg.entity.Career;
import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.entity.TeachingUnit;
import fr.info.pl2020.plplg.util.FunctionsUtils;
import org.jboss.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    public void sendCareerByMail(Student student, Career career) throws Exception {
        if (student == null || FunctionsUtils.isNullOrBlank(student.getEmail())) {
            logger.error("sendCareerByMail(" + career.getId() + ") - L'étudiant ou l'adresse email de l'étudiant est null.");
            throw new Exception("Echec de l'envoi du mail : l'adresse de destination n'existe pas.");
        }

        EmailBuilder emailBuilder = new EmailBuilder();
        emailBuilder.setTo(student.getEmail());
        emailBuilder.setSubject("Votre parcours enregistré : " + career.getName());
        emailBuilder.setBody("Madame, Monsieur,\nVous avez demandé l'envoi du parcours '" + career.getName() + "' par email. Vous trouverez ce parcours en pièce-jointe.\n\nCordialement\n\n\nCe message est envoyé automatiquement, merci de ne pas y répondre.");
        emailBuilder.addAttachment(exportCareerToPdf(career));
        emailBuilder.send();
    }

}
