package br.com.alexalmeida.gestao_vagas.modules.candidate.useCases;

import br.com.alexalmeida.gestao_vagas.exceptions.JobNotFoundException;
import br.com.alexalmeida.gestao_vagas.exceptions.UserFoundException;
import br.com.alexalmeida.gestao_vagas.exceptions.UserNotFoundException;
import br.com.alexalmeida.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.alexalmeida.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUse {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;


    public void execute(UUID idCandidate, UUID idJob){

        this.candidateRepository.findById(idCandidate)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        this.jobRepository.findById(idJob)
                .orElseThrow(() -> {
                    throw new JobNotFoundException();
                });



    }
}
