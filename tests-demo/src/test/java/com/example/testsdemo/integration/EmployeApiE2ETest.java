package com.example.testsdemo.integration;

import com.example.testsdemo.employe.Employe;
import com.example.testsdemo.employe.EmployeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeApiE2ETest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void creerEtRecupererEmploye() {
        EmployeDTO dto = new EmployeDTO("Salah", "Informatique", 3500);

        ResponseEntity<Employe> postResponse =
                restTemplate.postForEntity("/api/employes", dto, Employe.class);

        assertThat(postResponse.getStatusCode().value()).isEqualTo(201);
        assertThat(postResponse.getBody()).isNotNull();

        Long id = postResponse.getBody().getId();

        ResponseEntity<Employe> getResponse =
                restTemplate.getForEntity("/api/employes/" + id, Employe.class);

        assertThat(getResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getNom()).isEqualTo("Salah");
    }

    @Test
    void supprimerEmploye_puisGetRetourne404() {
        EmployeDTO dto = new EmployeDTO("Eya", "RH", 2800);

        ResponseEntity<Employe> postResponse =
                restTemplate.postForEntity("/api/employes", dto, Employe.class);

        Long id = postResponse.getBody().getId();

        restTemplate.delete("/api/employes/" + id);

        ResponseEntity<Employe> getResponse =
                restTemplate.getForEntity("/api/employes/" + id, Employe.class);

        assertThat(getResponse.getStatusCode().value()).isEqualTo(404);
    }
}