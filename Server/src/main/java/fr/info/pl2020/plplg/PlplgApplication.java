package fr.info.pl2020.plplg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class PlplgApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PlplgApplication.class);
        //app.addListeners(new ApplicationPidFileWriter("./application.pid"));
        app.run(args);
    }
}
