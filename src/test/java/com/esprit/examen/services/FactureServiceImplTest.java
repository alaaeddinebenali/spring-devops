package com.esprit.examen.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.Fournisseur;
import com.esprit.examen.entities.Operateur;
import com.esprit.examen.repositories.DetailFactureRepository;
import com.esprit.examen.repositories.FactureRepository;
import com.esprit.examen.repositories.FournisseurRepository;
import com.esprit.examen.repositories.OperateurRepository;
import com.esprit.examen.repositories.ProduitRepository;

@ExtendWith(MockitoExtension.class)
class FactureServiceImplTest {

    @InjectMocks
    FactureServiceImpl factureService;

    @Mock
    FactureRepository factureRepository;
    @Mock
    OperateurRepository operateurRepository;
    @Mock
    DetailFactureRepository detailFactureRepository;
    @Mock
    FournisseurRepository fournisseurRepository;
    @Mock
    ProduitRepository produitRepository;
    @Mock
    ReglementServiceImpl reglementService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRetrieveAllFactures() {
        Facture facture1 = new Facture();
        Facture facture2 = new Facture();
        when(factureRepository.findAll()).thenReturn(Arrays.asList(facture1, facture2));

        List<Facture> factures = factureService.retrieveAllFactures();

        assertEquals(2, factures.size());
        verify(factureRepository, times(1)).findAll();
    }

    @Test
    void testAddFacture() {
        Facture facture = new Facture();
        when(factureRepository.save(facture)).thenReturn(facture);

        Facture savedFacture = factureService.addFacture(facture);

        assertNotNull(savedFacture);
        verify(factureRepository, times(1)).save(facture);
    }

    @Test
    void testCancelFacture() {
        Long factureId = 1L;
        Facture facture = new Facture();
        when(factureRepository.findById(factureId)).thenReturn(Optional.of(facture));

        factureService.cancelFacture(factureId);

        assertTrue(facture.getArchivee());
        verify(factureRepository, times(1)).findById(factureId);
        verify(factureRepository, times(1)).save(facture);
        verify(factureRepository, times(1)).updateFacture(factureId);
    }

    @Test
    void testRetrieveFacture() {
        Long factureId = 1L;
        Facture facture = new Facture();
        when(factureRepository.findById(factureId)).thenReturn(Optional.of(facture));

        Facture retrievedFacture = factureService.retrieveFacture(factureId);

        assertNotNull(retrievedFacture);
        verify(factureRepository, times(1)).findById(factureId);
    }



    @Test
    void testPourcentageRecouvrement() {
        Date startDate = new Date();
        Date endDate = new Date();
        float totalFacturesEntreDeuxDates = 1000f;
        float totalRecouvrementEntreDeuxDates = 800f;
        when(factureRepository.getTotalFacturesEntreDeuxDates(startDate, endDate)).thenReturn(totalFacturesEntreDeuxDates);
        when(reglementService.getChiffreAffaireEntreDeuxDate(startDate, endDate)).thenReturn(totalRecouvrementEntreDeuxDates);

        float pourcentage = factureService.pourcentageRecouvrement(startDate, endDate);

        assertEquals(80f, pourcentage);
        verify(factureRepository, times(1)).getTotalFacturesEntreDeuxDates(startDate, endDate);
        verify(reglementService, times(1)).getChiffreAffaireEntreDeuxDate(startDate, endDate);
    }
}
