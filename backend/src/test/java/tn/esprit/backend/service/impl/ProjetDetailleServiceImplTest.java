package tn.esprit.backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.entity.ProjetDetaille;
import tn.esprit.backend.repository.ProjetDetailleRepository;
import tn.esprit.backend.repository.ProjetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetDetailleServiceImplTest {

    @Mock
    private ProjetDetailleRepository projetDetailleRepository;

    @Mock
    private ProjetRepository projetRepository;

    @InjectMocks
    private ProjetDetailleServiceImpl projetDetailleService;

    @Test
    void testAllProjetDetailleMethods() {
        Projet p = Projet.builder().id(5L).sujet("CI/CD").build();
        ProjetDetaille pd = ProjetDetaille.builder()
                .id(1L).description("Pipeline").technologie("Jenkins")
                .coutProvisoire(1500.0).dateDebut(LocalDate.now()).build();

        when(projetDetailleRepository.save(pd)).thenReturn(pd);
        when(projetDetailleRepository.findById(1L)).thenReturn(Optional.of(pd));
        when(projetDetailleRepository.findAll()).thenReturn(List.of(pd));
        when(projetDetailleRepository.findByProjetId(5L)).thenReturn(List.of(pd));
        when(projetRepository.findById(5L)).thenReturn(Optional.of(p));

        assertEquals("Pipeline", projetDetailleService.addProjetDetaille(pd).getDescription());
        assertEquals("Pipeline", projetDetailleService.updateProjetDetaille(pd).getDescription());
        assertEquals(1L, projetDetailleService.getProjetDetailleById(1L).getId());
        assertEquals(1, projetDetailleService.getAllProjetsDetailles().size());
        assertEquals(1, projetDetailleService.getProjetDetaillesByProjet(5L).size());

        ProjetDetaille assigned = projetDetailleService.assignProjetDetailleToProjet(1L, 5L);
        assertEquals(5L, assigned.getProjet().getId());

        projetDetailleService.deleteProjetDetaille(1L);
        verify(projetDetailleRepository, times(1)).deleteById(1L);
    }
}
