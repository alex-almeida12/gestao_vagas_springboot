package br.com.alexalmeida.gestao_vagas.modules.candidate.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCanditateResponseDTO {

    private UUID id;
    private String name;
    private String username;
    private String email;
    private String description;

}
