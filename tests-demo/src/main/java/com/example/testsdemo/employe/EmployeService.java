package com.example.testsdemo.employe;

import java.util.List;

public interface EmployeService {

    List<Employe> listerTous();

    Employe trouverParId(Long id);

    Employe creer(EmployeDTO dto);

    void supprimer(Long id);
}