package com.esprit.examen.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.esprit.examen.entities.CategorieProduit;
import com.esprit.examen.repositories.CategorieProduitRepository;

@ExtendWith(MockitoExtension.class)
class CategorieProduitServiceImplTest {

    @InjectMocks
    CategorieProduitServiceImpl categorieProduitService;

    @Mock
    CategorieProduitRepository categorieProduitRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRetrieveAllCategorieProduits() {
        CategorieProduit categorieProduit1 = new CategorieProduit();
        CategorieProduit categorieProduit2 = new CategorieProduit();
        when(categorieProduitRepository.findAll()).thenReturn(Arrays.asList(categorieProduit1, categorieProduit2));

        List<CategorieProduit> categorieProduits = categorieProduitService.retrieveAllCategorieProduits();

        assertEquals(2, categorieProduits.size());
        verify(categorieProduitRepository, times(1)).findAll();
    }

    @Test
    void testAddCategorieProduit() {
        CategorieProduit categorieProduit = new CategorieProduit();
        when(categorieProduitRepository.save(categorieProduit)).thenReturn(categorieProduit);

        CategorieProduit savedCategorieProduit = categorieProduitService.addCategorieProduit(categorieProduit);

        assertNotNull(savedCategorieProduit);
        verify(categorieProduitRepository, times(1)).save(categorieProduit);
    }

    @Test
    void testDeleteCategorieProduit() {
        Long id = 1L;
        doNothing().when(categorieProduitRepository).deleteById(id);

        categorieProduitService.deleteCategorieProduit(id);

        verify(categorieProduitRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdateCategorieProduit() {
        CategorieProduit categorieProduit = new CategorieProduit();
        when(categorieProduitRepository.save(categorieProduit)).thenReturn(categorieProduit);

        CategorieProduit updatedCategorieProduit = categorieProduitService.updateCategorieProduit(categorieProduit);

        assertNotNull(updatedCategorieProduit);
        verify(categorieProduitRepository, times(1)).save(categorieProduit);
    }

    @Test
    void testRetrieveCategorieProduit() {
        Long id = 1L;
        CategorieProduit categorieProduit = new CategorieProduit();
        when(categorieProduitRepository.findById(id)).thenReturn(Optional.of(categorieProduit));

        CategorieProduit retrievedCategorieProduit = categorieProduitService.retrieveCategorieProduit(id);

        assertNotNull(retrievedCategorieProduit);
        verify(categorieProduitRepository, times(1)).findById(id);
    }
}
