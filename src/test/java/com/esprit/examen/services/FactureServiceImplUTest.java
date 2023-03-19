package com.esprit.examen.services;

import com.esprit.examen.entities.Facture;
import com.esprit.examen.repositories.FactureRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FactureServiceImplUTest {

    @InjectMocks
    private FactureServiceImpl factureService;
    @Mock
    private FactureRepository factureRepository;

    @Autowired
    private IFactureService iFactureService;

    private List<Facture> factures;
    private Facture facture;

    @BeforeEach
    void setUp() {
        this.factureService= new FactureServiceImpl();
        for (int i = 0; i < 5; i++) {
            Facture facture = new Facture();
            facture.setIdFacture(i + 0L);
            facture.setMontantFacture(15 + i);
            facture.setArchivee(false);
            facture.setDateCreationFacture(new Date());
            facture.setDateDerniereModificationFacture(new Date());
            facture.setMontantRemise(2 + i);
            this.factures.add(facture);
        }
    }

    @Test
    public void whenFetch_givenAllFactury_expectSuccess() {
        Mockito.when(factureRepository.findAll()).thenReturn(factures);
        assertEquals(this.iFactureService.retrieveAllFactures(), 5);
    }
}