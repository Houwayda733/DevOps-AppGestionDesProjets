package tn.esprit.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.EntrepriseDTO;
import tn.esprit.backend.entity.Entreprise;
import tn.esprit.backend.service.IEntrepriseService;

import java.util.List;

@RestController
@RequestMapping("/entreprise")
@AllArgsConstructor
@CrossOrigin(origins = "${app.cors.allowed-origins:http://localhost:4200}")
public class EntrepriseController {

    private final IEntrepriseService entrepriseService;

    private EntrepriseDTO toDTO(Entreprise e) {
        if (e == null) return null;
        return EntrepriseDTO.builder()
                .id(e.getId())
                .nom(e.getNom())
                .adresse(e.getAdresse())
                .build();
    }

    private Entreprise toEntity(EntrepriseDTO dto) {
        if (dto == null) return null;
        Entreprise e = new Entreprise();
        e.setId(dto.getId());
        e.setNom(dto.getNom());
        e.setAdresse(dto.getAdresse());
        return e;
    }

    @PostMapping("/add")
    public EntrepriseDTO addEntreprise(@RequestBody EntrepriseDTO dto) {
        return toDTO(entrepriseService.addEntreprise(toEntity(dto)));
    }

    @PutMapping("/update")
    public EntrepriseDTO updateEntreprise(@RequestBody EntrepriseDTO dto) {
        return toDTO(entrepriseService.updateEntreprise(toEntity(dto)));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEntreprise(@PathVariable Long id) {
        entrepriseService.deleteEntreprise(id);
    }

    @GetMapping("/get/{id}")
    public EntrepriseDTO getEntrepriseById(@PathVariable Long id) {
        return toDTO(entrepriseService.getEntrepriseById(id));
    }

    @GetMapping("/all")
    public List<EntrepriseDTO> getAllEntreprises() {
        return entrepriseService.getAllEntreprises().stream()
                .map(this::toDTO)
                .toList();
    }
}
