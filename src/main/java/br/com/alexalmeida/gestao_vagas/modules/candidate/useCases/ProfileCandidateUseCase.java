package br.com.alexalmeida.gestao_vagas.modules.candidate.useCases;

import br.com.alexalmeida.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.alexalmeida.gestao_vagas.modules.candidate.DTO.ProfileCanditateResponseDTO;

import org.openengsb.core.usermanagement.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCanditateResponseDTO execute(UUID idCanditade){

        var candidate = this.candidateRepository.findById(idCandidate)
        .orElseThrow(() -> {
          throw new UserNotFoundException();
        });

        var candidateDTO = ProfileCanditateResponseDTO.builder().
                id(candidate.getId()).
                name(candidate.getName()).
                username(candidate.getUsername()).
                email(candidate.getEmail()).
                description(candidate.getDescription()).
                build();

        return candidateDTO;



    }

}
