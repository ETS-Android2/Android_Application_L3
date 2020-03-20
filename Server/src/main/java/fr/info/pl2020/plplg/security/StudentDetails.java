package fr.info.pl2020.plplg.security;

import fr.info.pl2020.plplg.entity.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class StudentDetails implements UserDetails {

    private int id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public StudentDetails(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static StudentDetails create(Student student) {
        return new StudentDetails(student.getId(), student.getEmail(), student.getPassword());
    }
}
