package tn.esprit.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.EquipeDTO;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.entity.Equipe;
import tn.esprit.backend.service.IEquipeService;

import java.util.List;

@RestController
@RequestMapping("/equipe")
@AllArgsConstructor
@CrossOrigin("*")
public class EquipeController {

    private final IEquipeService equipeService;

    private EquipeDTO toDTO(Equipe eq) {
        if (eq == null) return null;
        return EquipeDTO.builder()
                .id(eq.getId())
                .nom(eq.getNom())
                .specialite(eq.getSpecialite())
                .entrepriseId(eq.getEntreprise() != null ? eq.getEntreprise().getId() : null)
                .build();
    }

    private Equipe toEntity(EquipeDTO dto) {
        if (dto == null) return null;
        Equipe eq = new Equipe();
        eq.setId(dto.getId());
        eq.setNom(dto.getNom());
        eq.setSpecialite(dto.getSpecialite());
        if (dto.getEntrepriseId() != null) {
            Entreprise ent = new Entreprise();
            ent.setId(dto.getEntrepriseId());
            eq.setEntreprise(ent);
        }
        return eq;
    }

    @PostMapping("/add")
    public EquipeDTO addEquipe(@RequestBody EquipeDTO dto) {
        return toDTO(equipeService.addEquipe(toEntity(dto)));
    }

    @PutMapping("/update")
    public EquipeDTO updateEquipe(@RequestBody EquipeDTO dto) {
        return toDTO(equipeService.updateEquipe(toEntity(dto)));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEquipe(@PathVariable Long id) {
        equipeService.deleteEquipe(id);
    }

    @GetMapping("/get/{id}")
    public EquipeDTO getEquipeById(@PathVariable Long id) {
        return toDTO(equipeService.getEquipeById(id));
    }

    @GetMapping("/all")
    public List<EquipeDTO> getAllEquipes() {
        return equipeService.getAllEquipes().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/by-entreprise/{entrepriseId}")
    public List<EquipeDTO> getEquipesByEntreprise(@PathVariable Long entrepriseId) {
        return equipeService.getEquipesByEntreprise(entrepriseId).stream()
                .map(this::toDTO)
                .toList();
    }

    @PutMapping("/assign-entreprise/{equipeId}/{entrepriseId}")
    public EquipeDTO assignEquipeToEntreprise(@PathVariable Long equipeId, @PathVariable Long entrepriseId) {
        return toDTO(equipeService.assignEquipeToEntreprise(equipeId, entrepriseId));
    }

    @PutMapping("/assign-projet/{equipeId}/{projetId}")
    public EquipeDTO assignEquipeToProjet(@PathVariable Long equipeId, @PathVariable Long projetId) {
        return toDTO(equipeService.assignEquipeToProjet(equipeId, projetId));
    }
}
