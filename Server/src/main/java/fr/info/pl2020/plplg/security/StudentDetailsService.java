package fr.info.pl2020.plplg.security;

import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StudentDetailsService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.loadUserByEmail(email);
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email : '" + email + "'"));
        return StudentDetails.create(student);
    }

    public UserDetails loadUserById(int id) throws UsernameNotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id : '" + id + "'"));
        return StudentDetails.create(student);
    }

    public StudentDetails getFromUser(User user) {
        Student student = studentRepository.findByEmail(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with email : '" + user.getUsername() + "'"));
        return new StudentDetails(student.getId(), student.getEmail(), "****");
    }
}
