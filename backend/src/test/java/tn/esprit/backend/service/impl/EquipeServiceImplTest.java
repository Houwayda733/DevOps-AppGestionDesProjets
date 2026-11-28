package tn.esprit.backend.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.entity.Equipe;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.repository.EntrepriseRepository;
import tn.esprit.backend.repository.EquipeRepository;
import tn.esprit.backend.repository.ProjetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipeServiceImplTest {

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @Mock
    private ProjetRepository projetRepository;

    @InjectMocks
    private EquipeServiceImpl equipeService;

    @Test
    void testAllEquipeMethods() {
        Entreprise ent = Entreprise.builder().id(10L).nom("Tech").build();
        Projet proj = Projet.builder().id(20L).sujet("Cloud").build();
        Equipe eq = Equipe.builder().id(1L).nom("Alpha").specialite("DevOps").projets(new ArrayList<>()).build();

        when(equipeRepository.save(eq)).thenReturn(eq);
        when(equipeRepository.findById(1L)).thenReturn(Optional.of(eq));
        when(equipeRepository.findAll()).thenReturn(List.of(eq));
        when(equipeRepository.findByEntrepriseId(10L)).thenReturn(List.of(eq));
        when(entrepriseRepository.findById(10L)).thenReturn(Optional.of(ent));
        when(projetRepository.findById(20L)).thenReturn(Optional.of(proj));

        assertEquals("Alpha", equipeService.addEquipe(eq).getNom());
        assertEquals("Alpha", equipeService.updateEquipe(eq).getNom());
        assertEquals(1L, equipeService.getEquipeById(1L).getId());
        assertEquals(1, equipeService.getAllEquipes().size());
        assertEquals(1, equipeService.getEquipesByEntreprise(10L).size());

        Equipe assignedEnt = equipeService.assignEquipeToEntreprise(1L, 10L);
        assertEquals(10L, assignedEnt.getEntreprise().getId());

        Equipe assignedProj = equipeService.assignEquipeToProjet(1L, 20L);
        assertEquals(1, assignedProj.getProjets().size());

        equipeService.deleteEquipe(1L);
        verify(equipeRepository, times(1)).deleteById(1L);
    }
}
