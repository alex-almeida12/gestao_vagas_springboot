package br.com.alexalmeida.gestao_vagas.modules.candidate.controllers;

import br.com.alexalmeida.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.alexalmeida.gestao_vagas.modules.candidate.DTO.ProfileCanditateResponseDTO;
import br.com.alexalmeida.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.alexalmeida.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.alexalmeida.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.alexalmeida.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name="Candidato", description = "Informações do Candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUserCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @PostMapping("/")
    @Operation(summary = "Cadastro de candidato", description = "Função responsável por cadsatrar um candidato.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content( schema = @Schema(implementation = CandidateEntity.class))
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe.")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity){
        try{
            var result = createCandidateUserCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Função responsável por buscar informações do candidato.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content( schema = @Schema(implementation = ProfileCanditateResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "User not found.")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request){

        var idCandidate = request.getAttribute("candidate_id");

        try{
            var profile = profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponível para o candidato", description = "Função responsável por Listar de vagas disponíveis por filtro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findByFilter(@RequestParam String filter){
        return listAllJobsByFilterUseCase.execute(filter);
    }

}
