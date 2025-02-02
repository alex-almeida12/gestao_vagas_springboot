package br.com.alexalmeida.gestao_vagas.modules.candidate.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "Maria luiza")
    private String name;

    @Schema(example = "maria")
    private String username;

    @Schema(example = "maria@gmail.com")
    private String email;

    @Schema(example = "Desensevolvedora Java")
    private String description;

}
