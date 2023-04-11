package com.esprit.examen.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.esprit.examen.entities.DetailFournisseur;
import com.esprit.examen.entities.Fournisseur;
import com.esprit.examen.entities.SecteurActivite;
import com.esprit.examen.repositories.DetailFournisseurRepository;
import com.esprit.examen.repositories.FournisseurRepository;
import com.esprit.examen.repositories.SecteurActiviteRepository;

@ExtendWith(SpringExtension.class)
class FournisseurServiceImplTest {

    @InjectMocks
    FournisseurServiceImpl fournisseurService;

    @Mock
    FournisseurRepository fournisseurRepository;
    @Mock
    DetailFournisseurRepository detailFournisseurRepository;
    @Mock
    SecteurActiviteRepository secteurActiviteRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRetrieveAllFournisseurs() {
        Fournisseur fournisseur1 = new Fournisseur();
        Fournisseur fournisseur2 = new Fournisseur();
        when(fournisseurRepository.findAll()).thenReturn(Arrays.asList(fournisseur1, fournisseur2));

        List<Fournisseur> fournisseurs = fournisseurService.retrieveAllFournisseurs();

        assertEquals(2, fournisseurs.size());
        verify(fournisseurRepository, times(1)).findAll();
    }

    @Test
    void testAddFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        when(fournisseurRepository.save(fournisseur)).thenReturn(fournisseur);

        Fournisseur savedFournisseur = fournisseurService.addFournisseur(fournisseur);

        assertNotNull(savedFournisseur);
        verify(fournisseurRepository, times(1)).save(fournisseur);
    }

    @Test
    void testUpdateFournisseur() {
        Fournisseur fournisseur = new Fournisseur();
        DetailFournisseur detailFournisseur = new DetailFournisseur();
        fournisseur.setDetailFournisseur(detailFournisseur);
        when(detailFournisseurRepository.save(detailFournisseur)).thenReturn(detailFournisseur);
        when(fournisseurRepository.save(fournisseur)).thenReturn(fournisseur);

        Fournisseur updatedFournisseur = fournisseurService.updateFournisseur(fournisseur);

        assertNotNull(updatedFournisseur);
        verify(detailFournisseurRepository, times(1)).save(detailFournisseur);
        verify(fournisseurRepository, times(1)).save(fournisseur);
    }

    @Test
    void testDeleteFournisseur() {
        Long id = 1L;
        doNothing().when(fournisseurRepository).deleteById(id);

        fournisseurService.deleteFournisseur(id);

        verify(fournisseurRepository, times(1)).deleteById(id);
    }

    @Test
    void testRetrieveFournisseur() {
        Long id = 1L;
        Fournisseur fournisseur = new Fournisseur();
        when(fournisseurRepository.findById(id)).thenReturn(Optional.of(fournisseur));

        Fournisseur retrievedFournisseur = fournisseurService.retrieveFournisseur(id);

        assertNotNull(retrievedFournisseur);
        verify(fournisseurRepository, times(1)).findById(id);
    }
}