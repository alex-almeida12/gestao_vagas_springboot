package br.com.alexalmeida.gestao_vagas.modules.company.controllers;

import br.com.alexalmeida.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.alexalmeida.gestao_vagas.modules.company.entities.JobEntity;
import br.com.alexalmeida.gestao_vagas.modules.company.useCases.CreateJobUseCase;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    CreateJobUseCase createJobUseCase;

    @PostMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name="Vagas", description = "Informações das Vagas")
    @Operation(summary = "Cadastro de Vagas",
            description = "Função responsável por cadastrar as vagas dentro da empresa.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = JobEntity.class))
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest resquest){

        try{
            var compamnyID = resquest.getAttribute("company_id");

            var jobEntity = JobEntity.builder().
                    companyId(UUID.fromString(compamnyID.toString())).
                    benefits(createJobDTO.getBenefits()).
                    description(createJobDTO.getDescription()).
                    level(createJobDTO.getLevel()).build();

            var result = this.createJobUseCase.execute(jobEntity);

            return ResponseEntity.ok().body(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
        }

}
