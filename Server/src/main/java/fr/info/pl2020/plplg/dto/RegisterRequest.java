package fr.info.pl2020.plplg.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Size(min = 2, max = 49)
    private String firstname;

    @NotBlank
    @Size(min = 2, max = 49)
    private String lastname;

    @NotBlank
    @Size(max = 49)
    @Email
    private String email;

    @NotBlank
    @Size(min = 5, max = 49)
    private String password;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
