package tn.esprit.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.ProjetDetailleDTO;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.entity.ProjetDetaille;
import tn.esprit.backend.service.IProjetDetailleService;

import java.util.List;

@RestController
@RequestMapping("/projet-detaille")
@AllArgsConstructor
@CrossOrigin("*")
public class ProjetDetailleController {

    private final IProjetDetailleService projetDetailleService;

    private ProjetDetailleDTO toDTO(ProjetDetaille pd) {
        if (pd == null) return null;
        return ProjetDetailleDTO.builder()
                .id(pd.getId())
                .description(pd.getDescription())
                .technologie(pd.getTechnologie())
                .coutProvisoire(pd.getCoutProvisoire())
                .dateDebut(pd.getDateDebut())
                .projetId(pd.getProjet() != null ? pd.getProjet().getId() : null)
                .build();
    }

    private ProjetDetaille toEntity(ProjetDetailleDTO dto) {
        if (dto == null) return null;
        ProjetDetaille pd = new ProjetDetaille();
        pd.setId(dto.getId());
        pd.setDescription(dto.getDescription());
        pd.setTechnologie(dto.getTechnologie());
        pd.setCoutProvisoire(dto.getCoutProvisoire());
        pd.setDateDebut(dto.getDateDebut());
        if (dto.getProjetId() != null) {
            Projet p = new Projet();
            p.setId(dto.getProjetId());
            pd.setProjet(p);
        }
        return pd;
    }

    @PostMapping("/add")
    public ProjetDetailleDTO addProjetDetaille(@RequestBody ProjetDetailleDTO dto) {
        return toDTO(projetDetailleService.addProjetDetaille(toEntity(dto)));
    }

    @PutMapping("/update")
    public ProjetDetailleDTO updateProjetDetaille(@RequestBody ProjetDetailleDTO dto) {
        return toDTO(projetDetailleService.updateProjetDetaille(toEntity(dto)));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProjetDetaille(@PathVariable Long id) {
        projetDetailleService.deleteProjetDetaille(id);
    }

    @GetMapping("/get/{id}")
    public ProjetDetailleDTO getProjetDetailleById(@PathVariable Long id) {
        return toDTO(projetDetailleService.getProjetDetailleById(id));
    }

    @GetMapping("/all")
    public List<ProjetDetailleDTO> getAllProjetsDetailles() {
        return projetDetailleService.getAllProjetsDetailles().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/by-projet/{projetId}")
    public List<ProjetDetailleDTO> getProjetDetaillesByProjet(@PathVariable Long projetId) {
        return projetDetailleService.getProjetDetaillesByProjet(projetId).stream()
                .map(this::toDTO)
                .toList();
    }

    @PutMapping("/assign-projet/{projetDetailleId}/{projetId}")
    public ProjetDetailleDTO assignProjetDetailleToProjet(@PathVariable Long projetDetailleId, @PathVariable Long projetId) {
        return toDTO(projetDetailleService.assignProjetDetailleToProjet(projetDetailleId, projetId));
    }
}
