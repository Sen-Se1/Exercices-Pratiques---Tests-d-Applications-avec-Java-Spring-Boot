package com.example.testsdemo.facturation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facture {

    private Client client;
    private double montant;
}