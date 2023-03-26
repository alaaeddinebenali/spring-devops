package com.esprit.examen.services;

import com.esprit.examen.entities.Facture;
import com.esprit.examen.repositories.FactureRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FactureServiceImplUTest {

    @InjectMocks
    private FactureServiceImpl factureService;
    @Mock
    private FactureRepository factureRepository;

    private List<Facture> factures = new ArrayList<>();
    private Facture facture = new Facture();

    @Test
    public void whenFetch_givenAllFactury_expectSuccess() {
        for (int i = 0; i < 5; i++) {
            facture.setIdFacture(i + 0L);
            facture.setMontantFacture(15 + i);
            facture.setArchivee(false);
            facture.setDateCreationFacture(new Date());
            facture.setDateDerniereModificationFacture(new Date());
            facture.setMontantRemise(2 + i);
            factures.add(facture);
        }
        Mockito.when(factureRepository.findAll()).thenReturn(factures);
        assertEquals(5, this.factureService.retrieveAllFactures().size());
    }
}