package com.esprit.examen.services;

import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.Reglement;
import com.esprit.examen.repositories.FactureRepository;
import com.esprit.examen.repositories.ReglementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReglementServiceImplTest {

    @InjectMocks
    private ReglementServiceImpl reglementService;

    @Mock
    private FactureRepository factureRepository;

    @Mock
    private ReglementRepository reglementRepository;

    private Reglement reglement1;
    private Reglement reglement2;
    private List<Reglement> reglements;

    @BeforeEach
    public void setUp() {
        reglement1 = new Reglement(1L, 100f, 0f, true, new Date(), null);
        reglement2 = new Reglement(2L, 50f, 50f, false, new Date(), null);
        reglements = Arrays.asList(reglement1, reglement2);
    }

    @Test
    public void testRetrieveAllReglements() {
        // Given
        when(reglementRepository.findAll()).thenReturn(reglements);

        // When
        List<Reglement> result = reglementService.retrieveAllReglements();

        // Then
        assertEquals(reglements, result);
        verify(reglementRepository, times(1)).findAll();
    }

    @Test
    public void testAddReglement() {
        // Given
        when(reglementRepository.save(reglement1)).thenReturn(reglement1);

        // When
        Reglement result = reglementService.addReglement(reglement1);

        // Then
        assertEquals(reglement1, result);
        verify(reglementRepository, times(1)).save(reglement1);
    }

    @Test
    public void testRetrieveReglement() {
        // Given
        when(reglementRepository.findById(reglement1.getIdReglement())).thenReturn(Optional.of(reglement1));

        // When
        Reglement result = reglementService.retrieveReglement(reglement1.getIdReglement());

        // Then
        assertEquals(reglement1, result);
        verify(reglementRepository, times(1)).findById(reglement1.getIdReglement());
    }

    @Test
    public void testRetrieveReglementByFacture() {
        // Given
        Long idFacture = 1L;
        when(reglementRepository.retrieveReglementByFacture(idFacture)).thenReturn(reglements);

        // When
        List<Reglement> result = reglementService.retrieveReglementByFacture(idFacture);

        // Then
        assertEquals(reglements, result);
        verify(reglementRepository, times(1)).retrieveReglementByFacture(idFacture);
    }

    @Test
    public void testGetChiffreAffaireEntreDeuxDate() {
        // Given
        Date startDate = new Date();
        Date endDate = new Date();
        float expectedChiffreAffaire = 150f;
        when(reglementRepository.getChiffreAffaireEntreDeuxDate(startDate, endDate)).thenReturn(expectedChiffreAffaire);

        // When
        float result = reglementService.getChiffreAffaireEntreDeuxDate(startDate,
                endDate);

        // Then
        assertEquals(expectedChiffreAffaire, result);
        verify(reglementRepository, times(1)).getChiffreAffaireEntreDeuxDate(startDate, endDate);
    }
}