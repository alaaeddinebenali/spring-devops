package com.esprit.examen.services;

import static org.junit.jupiter.api.Assertions.*;

import com.esprit.examen.entities.SecteurActivite;
import com.esprit.examen.repositories.SecteurActiviteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SecteurActiviteServiceImplTest {

    @InjectMocks
    SecteurActiviteServiceImpl secteurActiviteService;

    @Mock
    SecteurActiviteRepository secteurActiviteRepository;

    @Test
    public void retrieveAllSecteurActiviteTest() {
        // Given
        List<SecteurActivite> expectedSecteurActivites = Arrays.asList(new SecteurActivite(), new SecteurActivite());
        when(secteurActiviteRepository.findAll()).thenReturn(expectedSecteurActivites);

        // When
        List<SecteurActivite> result = secteurActiviteService.retrieveAllSecteurActivite();

        // Then
        assertEquals(expectedSecteurActivites, result);
        verify(secteurActiviteRepository, times(1)).findAll();
    }

    @Test
    public void addSecteurActiviteTest() {
        // Given
        SecteurActivite secteurActivite = new SecteurActivite();
        when(secteurActiviteRepository.save(secteurActivite)).thenReturn(secteurActivite);

        // When
        SecteurActivite result = secteurActiviteService.addSecteurActivite(secteurActivite);

        // Then
        assertEquals(secteurActivite, result);
        verify(secteurActiviteRepository, times(1)).save(secteurActivite);
    }

    @Test
    public void deleteSecteurActiviteTest() {
        // Given
        Long id = 1L;

        // When
        secteurActiviteService.deleteSecteurActivite(id);

        // Then
        verify(secteurActiviteRepository, times(1)).deleteById(id);
    }

    @Test
    public void updateSecteurActiviteTest() {
        // Given
        SecteurActivite secteurActivite = new SecteurActivite();
        when(secteurActiviteRepository.save(secteurActivite)).thenReturn(secteurActivite);

        // When
        SecteurActivite result = secteurActiviteService.updateSecteurActivite(secteurActivite);

        // Then
        assertEquals(secteurActivite, result);
        verify(secteurActiviteRepository, times(1)).save(secteurActivite);
    }

    @Test
    public void retrieveSecteurActiviteTest() {
        // Given
        Long id = 1L;
        SecteurActivite expectedSecteurActivite = new SecteurActivite();
        when(secteurActiviteRepository.findById(id)).thenReturn(Optional.of(expectedSecteurActivite));

        // When
        SecteurActivite result = secteurActiviteService.retrieveSecteurActivite(id);

        // Then
        assertEquals(expectedSecteurActivite, result);
        verify(secteurActiviteRepository, times(1)).findById(id);
    }
}
