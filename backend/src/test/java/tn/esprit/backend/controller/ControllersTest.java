package tn.esprit.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.dto.*;
import tn.esprit.backend.entity.*;
import tn.esprit.backend.service.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllersTest {

    @Mock private IEntrepriseService entrepriseService;
    @Mock private IEquipeService equipeService;
    @Mock private IProjetService projetService;
    @Mock private IProjetDetailleService projetDetailleService;

    @InjectMocks private EntrepriseController entrepriseController;
    @InjectMocks private EquipeController equipeController;
    @InjectMocks private ProjetController projetController;
    @InjectMocks private ProjetDetailleController projetDetailleController;

    @Test
    void testEntrepriseController() {
        Entreprise e = Entreprise.builder().id(1L).nom("Esprit").adresse("Tunis").build();
        EntrepriseDTO dto = EntrepriseDTO.builder().id(1L).nom("Esprit").adresse("Tunis").build();
        when(entrepriseService.addEntreprise(any())).thenReturn(e);
        when(entrepriseService.updateEntreprise(any())).thenReturn(e);
        when(entrepriseService.getEntrepriseById(1L)).thenReturn(e);
        when(entrepriseService.getAllEntreprises()).thenReturn(List.of(e));

        assertEquals("Esprit", entrepriseController.addEntreprise(dto).getNom());
        assertEquals("Esprit", entrepriseController.updateEntreprise(dto).getNom());
        assertEquals(1L, entrepriseController.getEntrepriseById(1L).getId());
        assertEquals(1, entrepriseController.getAllEntreprises().size());
        entrepriseController.deleteEntreprise(1L);
        verify(entrepriseService).deleteEntreprise(1L);
    }

    @Test
    void testEquipeController() {
        Entreprise ent = Entreprise.builder().id(2L).build();
        Equipe eq = Equipe.builder().id(1L).nom("Team1").specialite("Dev").entreprise(ent).build();
        EquipeDTO dto = EquipeDTO.builder().id(1L).nom("Team1").specialite("Dev").entrepriseId(2L).build();
        when(equipeService.addEquipe(any())).thenReturn(eq);
        when(equipeService.updateEquipe(any())).thenReturn(eq);
        when(equipeService.getEquipeById(1L)).thenReturn(eq);
        when(equipeService.getAllEquipes()).thenReturn(List.of(eq));
        when(equipeService.getEquipesByEntreprise(2L)).thenReturn(List.of(eq));
        when(equipeService.assignEquipeToEntreprise(1L, 2L)).thenReturn(eq);
        when(equipeService.assignEquipeToProjet(1L, 3L)).thenReturn(eq);

        assertEquals("Team1", equipeController.addEquipe(dto).getNom());
        assertEquals("Team1", equipeController.updateEquipe(dto).getNom());
        assertEquals(1L, equipeController.getEquipeById(1L).getId());
        assertEquals(1, equipeController.getAllEquipes().size());
        assertEquals(1, equipeController.getEquipesByEntreprise(2L).size());
        assertEquals(2L, equipeController.assignEquipeToEntreprise(1L, 2L).getEntrepriseId());
        assertEquals(1L, equipeController.assignEquipeToProjet(1L, 3L).getId());
        equipeController.deleteEquipe(1L);
        verify(equipeService).deleteEquipe(1L);
    }

    @Test
    void testProjetController() {
        Projet p = Projet.builder().id(1L).sujet("PFE").build();
        ProjetDTO dto = ProjetDTO.builder().id(1L).sujet("PFE").build();
        when(projetService.addProjet(any())).thenReturn(p);
        when(projetService.updateProjet(any())).thenReturn(p);
        when(projetService.getProjetById(1L)).thenReturn(p);
        when(projetService.getAllProjets()).thenReturn(List.of(p));

        assertEquals("PFE", projetController.addProjet(dto).getSujet());
        assertEquals("PFE", projetController.updateProjet(dto).getSujet());
        assertEquals(1L, projetController.getProjetById(1L).getId());
        assertEquals(1, projetController.getAllProjets().size());
        projetController.deleteProjet(1L);
        verify(projetService).deleteProjet(1L);
    }

    @Test
    void testProjetDetailleController() {
        Projet p = Projet.builder().id(4L).build();
        ProjetDetaille pd = ProjetDetaille.builder().id(1L).description("Desc").technologie("Java").coutProvisoire(100.0).dateDebut(LocalDate.now()).projet(p).build();
        ProjetDetailleDTO dto = ProjetDetailleDTO.builder().id(1L).description("Desc").technologie("Java").coutProvisoire(100.0).dateDebut(LocalDate.now()).projetId(4L).build();
        when(projetDetailleService.addProjetDetaille(any())).thenReturn(pd);
        when(projetDetailleService.updateProjetDetaille(any())).thenReturn(pd);
        when(projetDetailleService.getProjetDetailleById(1L)).thenReturn(pd);
        when(projetDetailleService.getAllProjetsDetailles()).thenReturn(List.of(pd));
        when(projetDetailleService.getProjetDetaillesByProjet(4L)).thenReturn(List.of(pd));
        when(projetDetailleService.assignProjetDetailleToProjet(1L, 4L)).thenReturn(pd);

        assertEquals("Desc", projetDetailleController.addProjetDetaille(dto).getDescription());
        assertEquals("Desc", projetDetailleController.updateProjetDetaille(dto).getDescription());
        assertEquals(1L, projetDetailleController.getProjetDetailleById(1L).getId());
        assertEquals(1, projetDetailleController.getAllProjetsDetailles().size());
        assertEquals(1, projetDetailleController.getProjetDetaillesByProjet(4L).size());
        assertEquals(4L, projetDetailleController.assignProjetDetailleToProjet(1L, 4L).getProjetId());
        projetDetailleController.deleteProjetDetaille(1L);
        verify(projetDetailleService).deleteProjetDetaille(1L);
    }
}
