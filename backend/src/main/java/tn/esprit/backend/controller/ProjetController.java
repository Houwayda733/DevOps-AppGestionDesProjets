package tn.esprit.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.backend.dto.ProjetDTO;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.service.IProjetService;

import java.util.List;

@RestController
@RequestMapping("/projet")
@AllArgsConstructor
@CrossOrigin("*")
public class ProjetController {

    private final IProjetService projetService;

    private ProjetDTO toDTO(Projet p) {
        if (p == null) return null;
        return ProjetDTO.builder()
                .id(p.getId())
                .sujet(p.getSujet())
                .build();
    }

    private Projet toEntity(ProjetDTO dto) {
        if (dto == null) return null;
        Projet p = new Projet();
        p.setId(dto.getId());
        p.setSujet(dto.getSujet());
        return p;
    }

    @PostMapping("/add")
    public ProjetDTO addProjet(@RequestBody ProjetDTO dto) {
        return toDTO(projetService.addProjet(toEntity(dto)));
    }

    @PutMapping("/update")
    public ProjetDTO updateProjet(@RequestBody ProjetDTO dto) {
        return toDTO(projetService.updateProjet(toEntity(dto)));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProjet(@PathVariable Long id) {
        projetService.deleteProjet(id);
    }

    @GetMapping("/get/{id}")
    public ProjetDTO getProjetById(@PathVariable Long id) {
        return toDTO(projetService.getProjetById(id));
    }

    @GetMapping("/all")
    public List<ProjetDTO> getAllProjets() {
        return projetService.getAllProjets().stream()
                .map(this::toDTO)
                .toList();
    }
}
