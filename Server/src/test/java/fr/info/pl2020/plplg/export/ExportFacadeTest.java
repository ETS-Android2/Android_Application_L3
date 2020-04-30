package fr.info.pl2020.plplg.export;

import fr.info.pl2020.plplg.entity.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExportFacadeTest {

    @Test
    public void exportCareerToPdfTest() throws IOException {
        Student s = new Student("Kilian", "Clark", "toto@gmail.com", "");
        Semester se = new Semester();
        se.setName("Semestre 1");
        Semester se2 = new Semester();
        se2.setName("Semestre 2");
        Category c = new Category();
        List<TeachingUnit> teachingUnitList = Stream.of(new TeachingUnit("Structure Microscopique de la Matière", "SPUC10", "Atome, ion, molécule, gaz, solide sont les constituants et les états de la matière que nous découvrons dans ce module.", se, c),
                new TeachingUnit("Réactions et Réactivites Chimiques", "SPUC20", "L’objectif de l’UE est de mobiliser les connaissances acquises par les étudiants dans les cours de physique-chimie du lycée, pour les approfondir afin d'aborder de nouvelles notions sur la structure et la réactivité chimique.", se, c),
                new TeachingUnit("Méthodes expérimentales", "SLZP56", "L'objectif de cette UE est d'apprendre aux étudiants les techniques et les concepts à la base de la physique expérimentale. L'enseignement est basé sur des manipulations se déroulant sur plusieurs séances, ce qui permettra aux étudiants d'affronter les différents aspects du travail avec un certain recul et des pauses de réflexion. Le but est de transmettre un savoir-faire technique et méthodologique (manipulation, traitement des données, tenue d'un cahier d'expérience, etc...) que les étudiants pourront réinvestir dans des situations nécessitant l'acquisition de données ou de mesures (recherche scientifique, applications industrielles ou autre).", se, c),
                new TeachingUnit("Thermodynamique Chimique", "SPUC21", "La thermodynamique s’intéresse aux transferts thermiques et de travail. Pour compléter les notions vues au lycée à propos des chaleurs de combustion, de dissolution ou de changement d’états, il est nécessaire de définir un certain nombre de termes. ", se2, c),
                new TeachingUnit("Matériaux Terrestres", "SPUT33", "Ce cours aura pour but de présenter l'importance des géomatériaux à travers leur organisation structurale microscopique et les relations entre cette structure et leurs propriétés pour comprendre les processus géochimiques et géophysiques.", se2, c)).collect(Collectors.toList());
        Career ca = new Career("Mon Parcours", teachingUnitList, false, false);

        s.setCareers(Collections.singletonList(ca));
        ca.setStudent(s);

        File file = new ExportFacade().exportCareerToPdf(ca);
        assertNotNull(file);

    }

    @Test
    public void exportCareerToTxtTest() throws IOException {
        Student s = new Student("Kilian", "Clark", "toto@gmail.com", "");
        Semester se = new Semester();
        se.setName("Semestre 1");
        Semester se2 = new Semester();
        se2.setName("Semestre 2");
        Category c = new Category();
        List<TeachingUnit> teachingUnitList = Stream.of(new TeachingUnit("Structure Microscopique de la Matière", "SPUC10", "Atome, ion, molécule, gaz, solide sont les constituants et les états de la matière que nous découvrons dans ce module.", se, c),
                new TeachingUnit("Réactions et Réactivites Chimiques", "SPUC20", "L’objectif de l’UE est de mobiliser les connaissances acquises par les étudiants dans les cours de physique-chimie du lycée, pour les approfondir afin d'aborder de nouvelles notions sur la structure et la réactivité chimique.", se, c),
                new TeachingUnit("Méthodes expérimentales", "SLZP56", "L'objectif de cette UE est d'apprendre aux étudiants les techniques et les concepts à la base de la physique expérimentale. L'enseignement est basé sur des manipulations se déroulant sur plusieurs séances, ce qui permettra aux étudiants d'affronter les différents aspects du travail avec un certain recul et des pauses de réflexion. Le but est de transmettre un savoir-faire technique et méthodologique (manipulation, traitement des données, tenue d'un cahier d'expérience, etc...) que les étudiants pourront réinvestir dans des situations nécessitant l'acquisition de données ou de mesures (recherche scientifique, applications industrielles ou autre).", se, c),
                new TeachingUnit("Thermodynamique Chimique", "SPUC21", "La thermodynamique s’intéresse aux transferts thermiques et de travail. Pour compléter les notions vues au lycée à propos des chaleurs de combustion, de dissolution ou de changement d’états, il est nécessaire de définir un certain nombre de termes. ", se2, c),
                new TeachingUnit("Matériaux Terrestres", "SPUT33", "Ce cours aura pour but de présenter l'importance des géomatériaux à travers leur organisation structurale microscopique et les relations entre cette structure et leurs propriétés pour comprendre les processus géochimiques et géophysiques.", se2, c)).collect(Collectors.toList());
        Career ca = new Career("Mon Parcours", teachingUnitList, false, false);

        s.setCareers(Collections.singletonList(ca));
        ca.setStudent(s);

        File file = new ExportFacade().exportCareerToTxt(ca);
        assertNotNull(file);

    }
}
