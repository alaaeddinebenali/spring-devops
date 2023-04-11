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

import com.esprit.examen.entities.Produit;
import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.ProduitRepository;
import com.esprit.examen.repositories.StockRepository;

@ExtendWith(MockitoExtension.class)
class ProduitServiceImplTest {

    @InjectMocks
    ProduitServiceImpl produitService;

    @Mock
    ProduitRepository produitRepository;

    @Mock
    StockRepository stockRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testRetrieveAllProduits() {
        // Given
        Produit produit1 = new Produit();
        Produit produit2 = new Produit();
        when(produitRepository.findAll()).thenReturn(Arrays.asList(produit1, produit2));

        // When
        List<Produit> produits = produitService.retrieveAllProduits();

        // Then
        assertEquals(2, produits.size());
        verify(produitRepository, times(1)).findAll();
    }

    @Test
    void testAddProduit() {
        // Given
        Produit produit = new Produit();
        when(produitRepository.save(produit)).thenReturn(produit);

        // When
        Produit savedProduit = produitService.addProduit(produit);

        // Then
        assertNotNull(savedProduit);
        verify(produitRepository, times(1)).save(produit);
    }

    @Test
    void testDeleteProduit() {
        // Given
        Long id = 1L;

        // When
        produitService.deleteProduit(id);

        // Then
        verify(produitRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdateProduit() {
        // Given
        Produit produit = new Produit();
        produit.setIdProduit(1L);
        when(produitRepository.save(produit)).thenReturn(produit);

        // When
        Produit updatedProduit = produitService.updateProduit(produit);

        // Then
        assertNotNull(updatedProduit);
        verify(produitRepository, times(1)).save(produit);
    }

    @Test
    void testRetrieveProduit() {
        // Given
        Long id = 1L;
        Produit produit = new Produit();
        produit.setIdProduit(id);
        when(produitRepository.findById(id)).thenReturn(Optional.of(produit));

        // When
        Produit retrievedProduit = produitService.retrieveProduit(id);

        // Then
        assertNotNull(retrievedProduit);
        assertEquals(id, retrievedProduit.getIdProduit());
        verify(produitRepository, times(1)).findById(id);
    }

}
