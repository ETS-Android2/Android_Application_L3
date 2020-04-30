package fr.info.pl2020.plplg.controller;

import fr.info.pl2020.plplg.dto.CareerRequest;
import fr.info.pl2020.plplg.dto.CareerResponse;
import fr.info.pl2020.plplg.dto.CareerTeachingUnitRequest;
import fr.info.pl2020.plplg.entity.Career;
import fr.info.pl2020.plplg.entity.Student;
import fr.info.pl2020.plplg.exception.ClientRequestException;
import fr.info.pl2020.plplg.export.ExportFacade;
import fr.info.pl2020.plplg.service.AuthenticationService;
import fr.info.pl2020.plplg.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class CareerController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CareerService careerService;

    private ExportFacade exportFacade = new ExportFacade();

    public enum CareerFilter {
        STUDENT,
        PUBLIC,
        MAIN,
        LAST
    }

    //TODO supprimer
    @PutMapping(value = "/career/main")
    @ResponseBody
    public ResponseEntity<?> updateStudentMainCareer(@RequestBody @NotNull @NotEmpty List<Integer> teachingUnitIdList, @RequestParam(name = "semester", defaultValue = "0") int currentSemesterId) {
        try {
            Student loggedStudent = this.authenticationService.getLoggedStudent();
            this.careerService.updateCareer(this.careerService.getMainCareer(loggedStudent.getId()), teachingUnitIdList, currentSemesterId);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }


    /*** Gestion de la liste des parcours d'un étudiant ***/

    @GetMapping(value = "/career")
    @ResponseBody
    public ResponseEntity<?> getCareers(@RequestParam(name = "filter", defaultValue = "STUDENT") CareerFilter filter) {
        Student loggedStudent = this.authenticationService.getLoggedStudent();
        try {
            switch (filter) {
                case STUDENT:
                    List<CareerResponse> careers = CareerResponse.careerListToCareerResponseList(loggedStudent.getCareers());
                    return new ResponseEntity<>(careers, HttpStatus.OK);

                case PUBLIC:
                    List<Career> publicCareers = this.careerService.getAllPublicCareer();
                    return new ResponseEntity<>(CareerResponse.careerListToCareerResponseList(publicCareers, true), HttpStatus.OK);

                case MAIN:
                    Career mainCareer = this.careerService.getMainCareer(loggedStudent.getId());
                    return new ResponseEntity<>(new CareerResponse(mainCareer), HttpStatus.OK);

                case LAST:
                    Career lastCareer = this.careerService.getLastCreatedCareer(loggedStudent);
                    if (lastCareer == null) {
                        throw new ClientRequestException("Vous n'avez aucun parcours", HttpStatus.NOT_FOUND);
                    }

                    return new ResponseEntity<>(new CareerResponse(lastCareer), HttpStatus.OK);

                default:
                    //Aucune raison d'arriver ici...
                    throw new RuntimeException();
            }
        } catch (ClientRequestException e) {
            return new ResponseEntity<>(e.getClientMessage(), e.getStatus());
        }
    }

    @PostMapping(value = "/career")
    @ResponseBody
    public ResponseEntity<?> createCareer(@RequestBody @NotNull CareerRequest body) {
        try {
            Student loggedStudent = this.authenticationService.getLoggedStudent();
            Career career = this.careerService.createCareer(loggedStudent, body);
            return new ResponseEntity<>(new CareerResponse(career), HttpStatus.CREATED);
        } catch (ClientRequestException e) {
            return new ResponseEntity<>(e.getClientMessage(), e.getStatus());
        }
    }


    /*** Gestion d'un parcours en particulier ***/

    @GetMapping(value = "/career/{careerId}")
    @ResponseBody
    public ResponseEntity<?> getCareer(@PathVariable int careerId) {
        try {
            Career c = getCareerByIdAndCheckOwner(careerId);
            return new ResponseEntity<>(new CareerResponse(c), HttpStatus.OK);
        } catch (ClientRequestException e) {
            return new ResponseEntity<>(e.getClientMessage(), e.getStatus());
        }
    }

    @PostMapping(value = "/career/{careerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> addTeachingUnitInCareer(@PathVariable int careerId, @RequestBody @NotNull @Valid CareerTeachingUnitRequest body) {
        try {
            this.careerService.addTeachingUnitInCareer(getCareerByIdAndCheckOwner(careerId), body.getTeachingUnitId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    @PutMapping(value = "/career/{careerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCareer(@PathVariable int careerId, @RequestBody @NotNull @NotEmpty List<Integer> teachingUnitIdList, @RequestParam(name = "semester", defaultValue = "0") int currentSemesterId) {
        try {
            this.careerService.updateCareer(getCareerByIdAndCheckOwner(careerId), teachingUnitIdList, currentSemesterId);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    @DeleteMapping(value = "/career/{careerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCareer(@PathVariable int careerId) {
        try {
            Career career = getCareerByIdAndCheckOwner(careerId);
            this.careerService.removeCareer(career.getStudent().getId(), career.getId());
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    @GetMapping(value = "/career/{careerId}/export")
    @ResponseBody
    public ResponseEntity<?> exportCareer(@PathVariable int careerId, @RequestParam(name = "format") ExportFacade.ExportType exportType) {
        try {
            Career c = getCareerByIdAndCheckOwner(careerId);
            switch (exportType) {
                case PDF:
                    try {
                        File pdfFile = this.exportFacade.exportCareerToPdf(c);
                        Resource resource = new UrlResource(pdfFile.toURI());

                        return ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + c.getName() + ".pdf\"")
                                .body(resource);
                    } catch (IOException e) {
                        throw new ClientRequestException("La création du pdf a échoué. Veuillez réessayer ultérieurement.", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                case TXT:
                    try {
                        File txtFile = this.exportFacade.exportCareerToTxt(c);
                        Resource resource = new UrlResource(txtFile.toURI());

                        return ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + c.getName() + ".txt\"")
                                .body(resource);
                    } catch (IOException e) {
                        throw new ClientRequestException("La création du fichier texte a échoué. Veuillez réessayer ultérieurement.", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }

                default:
                    // On est pas censé arriver ici car le RequestParam est obligatoire et RestController renvoi tout seul un 400 si le type est incorrect
                    throw new ClientRequestException("Format de l'export non renseigné ou non reconnu.", HttpStatus.BAD_REQUEST);
            }
        } catch (ClientRequestException e) {
            return new ResponseEntity<>(e.getClientMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "/career/{careerId}/send")
    public ResponseEntity<?> sendCareer(@PathVariable int careerId) {
        try {
            Career c = getCareerByIdAndCheckOwner(careerId);
            try {
                this.exportFacade.sendCareerByMail(c);
                return ResponseEntity.noContent().build();
            } catch (Exception e) {
                throw new ClientRequestException("L'envoi du mail à échoué, veuillez réessayer ultérieurement.", "Echec de l'envoi du parcours par mail (careerId = '" + c.getId() + "') - " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ClientRequestException cre) {
            return new ResponseEntity<>(cre.getClientMessage(), cre.getStatus());
        }
    }

    private Career getCareerByIdAndCheckOwner(int careerId) throws ClientRequestException {
        Career career = this.careerService.findById(careerId);
        if (career == null) {
            throw new ClientRequestException("Le parcours demandé n'existe pas", HttpStatus.NOT_FOUND);
        }

        Student loggedStudent = this.authenticationService.getLoggedStudent();
        if (loggedStudent.getId() != career.getStudent().getId()) {
            throw new ClientRequestException("Le parcours demandé n'existe pas", HttpStatus.NOT_FOUND);
        }

        return career;
    }
}
