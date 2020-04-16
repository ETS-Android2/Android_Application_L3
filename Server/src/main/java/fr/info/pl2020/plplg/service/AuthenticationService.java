package fr.info.pl2020.plplg.service;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.security.StudentDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private StudentService studentService;

    public Student getLoggedStudent() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return this.studentService.getByLoggedUser(((User) principal));
        } else if (principal instanceof StudentDetails) {
            return this.studentService.getById(((StudentDetails) principal).getId());
        } else {
            throw new RuntimeException("Echec de détéction du type de User lors de l'authification");
        }
    }
}
