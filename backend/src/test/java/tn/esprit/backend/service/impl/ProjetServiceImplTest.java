package tn.esprit.backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.repository.ProjetRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetServiceImplTest {

    @Mock
    private ProjetRepository projetRepository;

    @InjectMocks
    private ProjetServiceImpl projetService;

    @Test
    void testCrudOperations() {
        Projet p = Projet.builder().id(1L).sujet("DevOps").build();
        when(projetRepository.save(p)).thenReturn(p);
        when(projetRepository.findById(1L)).thenReturn(Optional.of(p));
        when(projetRepository.findAll()).thenReturn(List.of(p));

        assertEquals("DevOps", projetService.addProjet(p).getSujet());
        assertEquals("DevOps", projetService.updateProjet(p).getSujet());
        assertEquals(1L, projetService.getProjetById(1L).getId());
        assertEquals(1, projetService.getAllProjets().size());

        projetService.deleteProjet(1L);
        verify(projetRepository, times(1)).deleteById(1L);
    }
}
