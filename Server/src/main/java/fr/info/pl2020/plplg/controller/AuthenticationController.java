package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.dto.*;
import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.service.StudentService;
import fr.info.pl2020.plplg.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginRequest credentials) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest userInfos) {
        if (this.studentService.getByEmail(userInfos.getEmail()) != null) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Cet email est déjà enregistré."), HttpStatus.BAD_REQUEST);
        }

        Student student = this.studentService.addStudent(userInfos.getFirstname(), userInfos.getLastname(), userInfos.getEmail(), userInfos.getPassword());
        return new ResponseEntity<>(new StandardResponse("Nouvel étudiant ajouté avec succès."), HttpStatus.CREATED);
    }
}
