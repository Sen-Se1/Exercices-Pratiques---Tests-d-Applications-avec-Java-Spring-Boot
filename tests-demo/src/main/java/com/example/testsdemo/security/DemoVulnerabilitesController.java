package com.example.testsdemo.security;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DemoVulnerabilitesController {

    @Autowired
    private final EntityManager em;

    // Faille 1 : injection SQL via concaténation directe
    @GetMapping("/search")
    public List<?> rechercher(@RequestParam String nom) {

        return em.createQuery(
                "SELECT e FROM Employe e WHERE e.nom = '" + nom + "'"
        ).getResultList();
    }

    // Faille 2 : secret en dur dans le code
    private static final String API_SECRET =
            "MonSecretSuperConfidentiel123!";
}