package com.example.testsdemo.facturation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturationServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private FacturationService service;

    @Test
    void genererFacture_clientExiste_retourneFacture() {
        Client client = new Client(1L, "contact@acme.com");

        when(clientRepository.findById(1L))
                .thenReturn(Optional.of(client));

        Facture facture = service.genererFacture(1L, 250.0);

        assertThat(facture).isNotNull();
        assertThat(facture.getMontant()).isEqualTo(250.0);
    }

    @Test
    void genererFacture_clientExiste_envoieNotification() {
        Client client = new Client(1L, "contact@acme.com");

        when(clientRepository.findById(1L))
                .thenReturn(Optional.of(client));

        service.genererFacture(1L, 250.0);

        verify(notificationService, times(1))
                .envoyerEmail("contact@acme.com", "Votre facture : 250.0€");
    }

    @Test
    void genererFacture_clientInexistant_lanceException() {
        when(clientRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> {
            service.genererFacture(99L, 250.0);
        });
    }

    @Test
    void genererFacture_clientInexistant_nePasEnvoyerNotification() {
        when(clientRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> {
            service.genererFacture(99L, 250.0);
        });

        verify(notificationService, never())
                .envoyerEmail(anyString(), anyString());
    }
}