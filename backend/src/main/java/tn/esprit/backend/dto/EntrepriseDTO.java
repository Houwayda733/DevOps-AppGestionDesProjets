package tn.esprit.backend.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EntrepriseDTO {
    private Long id;
    private String nom;
    private String adresse;
}
