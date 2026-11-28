package tn.esprit.backend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EquipeDTO {
    private Long id;
    private String nom;
    private String specialite;
    private Long entrepriseId;
}
