package fr.info.pl2020.plplg.service;

import fr.info.pl2020.plplg.entity.Semester;
import fr.info.pl2020.plplg.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    public List<Semester> getAll() {
        return this.semesterRepository.findAll();
    }

    public Semester getById(int id) {
        return this.semesterRepository.findById(id).orElse(null);
    }

    public Semester addSemester() {
        Semester h = new Semester(); //TODO
        return this.semesterRepository.save(h);
    }
}
