package tn.esprit.backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.repository.EntrepriseRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntrepriseServiceImplTest {

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @InjectMocks
    private EntrepriseServiceImpl entrepriseService;

    @Test
    void testCrudOperations() {
        Entreprise e = Entreprise.builder().id(1L).nom("Esprit").adresse("Ariana").build();
        when(entrepriseRepository.save(e)).thenReturn(e);
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.of(e));
        when(entrepriseRepository.findAll()).thenReturn(List.of(e));

        assertEquals("Esprit", entrepriseService.addEntreprise(e).getNom());
        assertEquals("Esprit", entrepriseService.updateEntreprise(e).getNom());
        assertEquals(1L, entrepriseService.getEntrepriseById(1L).getId());
        assertEquals(1, entrepriseService.getAllEntreprises().size());

        entrepriseService.deleteEntreprise(1L);
        verify(entrepriseRepository, times(1)).deleteById(1L);
    }
}
