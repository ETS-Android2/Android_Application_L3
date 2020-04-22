package fr.info.pl2020.plplg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    public final static boolean IS_ENABLE = true;

    // TODO Récuperer ces valeurs depuis application.properties !!!!!
    public static JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("parcours.univ@gmail.com");
        mailSender.setPassword("aihsfeowrarqyxnz");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.debug", "true");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
