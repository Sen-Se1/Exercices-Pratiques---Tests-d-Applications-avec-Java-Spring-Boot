package com.example.testsdemo.service;

import java.time.LocalDate;
import java.util.Random;

public class ReservationService {

    public double calculerPrixTotal(double prixParNuit, int nombreNuits) {
        if (prixParNuit < 0) {
            throw new IllegalArgumentException("Prix par nuit invalide");
        }

        double total = prixParNuit * nombreNuits;

        if (nombreNuits >= 7) {
            total = total * 0.9;
        }

        return total;
    }

    public boolean verifierDisponibilite(LocalDate debut, LocalDate fin) {
        if (debut == null || fin == null) {
            throw new IllegalArgumentException("Dates invalides");
        }

        return fin.isAfter(debut);
    }

    public String genererCodeConfirmation() {
        int number = new Random().nextInt(1_000_000);
        return String.format("HOTEL-%06d", number);
    }
}