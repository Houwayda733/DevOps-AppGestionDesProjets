package tn.esprit.backend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProjetDetailleDTO {
    private Long id;
    private String description;
    private String technologie;
    private Double coutProvisoire;
    private LocalDate dateDebut;
    private Long projetId;
}
