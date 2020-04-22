package fr.info.pl2020.plplg.export;

import fr.info.pl2020.plplg.config.EmailConfig;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class EmailBuilder {

    private JavaMailSender mailSender;

    private boolean sendEnable;

    private final static String FROM = "parcours.univ@gmail.com";
    private String[] to;
    private String subject;
    private String body;
    private Set<File> attachmentList;

    public EmailBuilder() {
        this.attachmentList = new HashSet<>();
        this.mailSender = EmailConfig.getJavaMailSender();
        this.sendEnable = EmailConfig.IS_ENABLE;
    }

    public EmailBuilder(String[] to, String subject, String body, Set<File> attachmentList) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.attachmentList = attachmentList;
        this.mailSender = EmailConfig.getJavaMailSender();
        this.sendEnable = EmailConfig.IS_ENABLE;
    }

    public void setTo(String... to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void addAttachment(File attachment) {
        this.attachmentList.add(attachment);
    }

    public void send() throws MessagingException {
        MimeMessage mail = this.mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);

        helper.setFrom(FROM);
        helper.setTo(this.to);
        helper.setSubject(this.subject);
        helper.setText(this.body);

        for (File f : this.attachmentList) {
            FileSystemResource file = new FileSystemResource(f);
            helper.addAttachment(f.getName(), file);
        }

        if (this.sendEnable && !Arrays.asList(this.to).contains("toto@gmail.com")) {
            this.mailSender.send(mail);
        }
    }
}
