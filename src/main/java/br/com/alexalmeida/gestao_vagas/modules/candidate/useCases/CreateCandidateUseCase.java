package br.com.alexalmeida.gestao_vagas.modules.candidate.useCases;

import br.com.alexalmeida.gestao_vagas.exceptions.UserFoundException;
import br.com.alexalmeida.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.alexalmeida.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getUsername()).
                ifPresent((user) -> {
                    throw new UserFoundException("Company not found");
                });

        var password = passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }
}
