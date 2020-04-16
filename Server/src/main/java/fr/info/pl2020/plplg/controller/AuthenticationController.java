package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.dto.*;
import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.security.JwtTokenProvider;
import fr.info.pl2020.plplg.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
        try {
            checkCredentials(credentials);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = this.jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest userInfos) {
        if (this.studentService.getByEmail(userInfos.getEmail()) != null) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.CONFLICT.value(), "Cet email est déjà enregistré."), HttpStatus.CONFLICT);
        }

        Student student = this.studentService.addStudent(userInfos.getFirstname(), userInfos.getLastname(), userInfos.getEmail(), userInfos.getPassword());
        return new ResponseEntity<>(new StandardResponse("Nouvel étudiant ajouté avec succès."), HttpStatus.CREATED);
    }

    @ApiIgnore
    @GetMapping(value = "/logoutSuccess")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("Vous êtes deconnecté", HttpStatus.OK);
    }

    /**
     * Si le paramètre envoyé ne passe pas la validation @Valid alors une RuntimeException est renvoyée.
     *
     * @param credentials
     * @throws RuntimeException si les credentials ne respectent pas les validations dans LoginRequest
     */
    private void checkCredentials(@Valid LoginRequest credentials) throws RuntimeException {
    }
}
