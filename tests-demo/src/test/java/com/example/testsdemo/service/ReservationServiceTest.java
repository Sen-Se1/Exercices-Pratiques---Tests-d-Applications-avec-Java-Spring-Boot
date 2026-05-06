package com.example.testsdemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReservationServiceTest {

    private ReservationService service;

    @BeforeEach
    void setUp() {
        service = new ReservationService();
    }

    @Test
    @DisplayName("Séjour de 3 nuits sans remise")
    void calculerPrixTotal_sejourCourt_retournePrixPlein() {
        double result = service.calculerPrixTotal(100, 3);

        assertThat(result).isEqualTo(300);
    }

    @Test
    @DisplayName("Séjour de 7 nuits avec remise")
    void calculerPrixTotal_sejourLong_appliqueRemise() {
        double result = service.calculerPrixTotal(100, 7);

        assertThat(result).isEqualTo(630);
    }

    @Test
    @DisplayName("Prix négatif lance exception")
    void calculerPrixTotal_prixNegatif_lanceException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.calculerPrixTotal(-100, 3);
        });
    }

    @Test
    @DisplayName("Dates valides retournent true")
    void verifierDisponibilite_datesValides_retourneTrue() {
        LocalDate debut = LocalDate.of(2026, 5, 1);
        LocalDate fin = LocalDate.of(2026, 5, 5);

        boolean result = service.verifierDisponibilite(debut, fin);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Date null lance exception")
    void verifierDisponibilite_dateNull_lanceException() {
        assertThrows(IllegalArgumentException.class, () -> {
            service.verifierDisponibilite(null, LocalDate.now());
        });
    }

    @Test
    @DisplayName("Code confirmation format HOTEL-XXXXXX")
    void genererCodeConfirmation_formatCorrect() {
        String code = service.genererCodeConfirmation();

        assertThat(code).startsWith("HOTEL-");
        assertThat(code).hasSize(12);
    }
}