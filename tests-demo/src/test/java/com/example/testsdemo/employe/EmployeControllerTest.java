package com.example.testsdemo.employe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.http.MediaType;

@WebMvcTest(EmployeController.class)
class EmployeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeService service;

    @Test
    void listerTous_retourneStatus200_etJson() throws Exception {
        when(service.listerTous()).thenReturn(List.of(
                new Employe(1L, "Salah", "Informatique", 3500),
                new Employe(2L, "Eya", "RH", 2800)
        ));

        mockMvc.perform(get("/api/employes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Salah"))
                .andExpect(jsonPath("$[1].nom").value("Eya"));
    }

    @Test
    void trouverParId_idInexistant_retourne404() throws Exception {
        when(service.trouverParId(99L))
                .thenThrow(new EmployeNotFoundException("Employé introuvable"));

        mockMvc.perform(get("/api/employes/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void creer_bodyInvalide_retourne400_etServiceNonAppele() throws Exception {
        mockMvc.perform(post("/api/employes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nom": "",
                                  "departement": "",
                                  "salaire": -500
                                }
                                """))
                .andExpect(status().isBadRequest());

        verify(service, never()).creer(any());
    }
}