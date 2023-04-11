package com.esprit.examen.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.esprit.examen.entities.Operateur;
import com.esprit.examen.repositories.OperateurRepository;

@ExtendWith(MockitoExtension.class)
class OperateurServiceImplTest {

    @InjectMocks
    OperateurServiceImpl operateurService;

    @Mock
    OperateurRepository operateurRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRetrieveAllOperateurs() {
        // Given
        Operateur operateur1 = new Operateur();
        Operateur operateur2 = new Operateur();
        when(operateurRepository.findAll()).thenReturn(Arrays.asList(operateur1, operateur2));

        // When
        List<Operateur> operateurs = operateurService.retrieveAllOperateurs();

        // Then
        assertEquals(2, operateurs.size());
        verify(operateurRepository, times(1)).findAll();
    }

    @Test
    void testAddOperateur() {
        // Given
        Operateur operateur = new Operateur();
        when(operateurRepository.save(operateur)).thenReturn(operateur);

        // When
        Operateur savedOperateur = operateurService.addOperateur(operateur);

        // Then
        assertNotNull(savedOperateur);
        verify(operateurRepository, times(1)).save(operateur);
    }

    @Test
    void testDeleteOperateur() {
        // Given
        Long id = 1L;

        // When
        operateurService.deleteOperateur(id);

        // Then
        verify(operateurRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdateOperateur() {
        // Given
        Operateur operateur = new Operateur();
        operateur.setIdOperateur(1L);
        when(operateurRepository.save(operateur)).thenReturn(operateur);

        // When
        Operateur updatedOperateur = operateurService.updateOperateur(operateur);

        // Then
        assertNotNull(updatedOperateur);
        verify(operateurRepository, times(1)).save(operateur);
    }

    @Test
    void testRetrieveOperateur() {
        // Given
        Long id = 1L;
        Operateur operateur = new Operateur();
        operateur.setIdOperateur(id);
        when(operateurRepository.findById(id)).thenReturn(Optional.of(operateur));

        // When
        Operateur retrievedOperateur = operateurService.retrieveOperateur(id);

        // Then
        assertNotNull(retrievedOperateur);
        assertEquals(id, retrievedOperateur.getIdOperateur());
        verify(operateurRepository, times(1)).findById(id);
    }
}
