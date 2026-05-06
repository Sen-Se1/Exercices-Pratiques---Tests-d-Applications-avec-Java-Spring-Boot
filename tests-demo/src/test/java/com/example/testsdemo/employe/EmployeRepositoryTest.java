package com.example.testsdemo.employe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository repository;

    @BeforeEach
    void setUp() {
        repository.save(new Employe(null, "Salah", "Informatique", 3500));
        repository.save(new Employe(null, "Eya", "RH", 2800));
        repository.save(new Employe(null, "Amar", "Informatique", 4200));
    }

    @Test
    void findByDepartement_informatique_retourneSalahEtAmar() {
        List<Employe> result = repository.findByDepartement("Informatique");

        assertThat(result)
                .hasSize(2)
                .extracting(Employe::getNom)
                .containsExactlyInAnyOrder("Salah", "Amar");
    }

    @Test
    void findBySalaireSuperieurA_seuil3000_retourneSalahEtAmar() {
        List<Employe> result = repository.findBySalaireSuperieurA(3000.0);

        assertThat(result)
                .extracting(Employe::getNom)
                .containsExactlyInAnyOrder("Salah", "Amar");

        assertThat(result)
                .extracting(Employe::getNom)
                .doesNotContain("Eya");
    }

    @Test
    void findByDepartement_comptabilite_retourneListeVide() {
        List<Employe> result = repository.findByDepartement("Comptabilité");

        assertThat(result).isEmpty();
    }
}