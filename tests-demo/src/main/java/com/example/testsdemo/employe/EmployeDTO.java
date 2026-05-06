package com.example.testsdemo.employe;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeDTO {

    @NotBlank
    private String nom;

    @NotBlank
    private String departement;

    @Positive
    private double salaire;
}