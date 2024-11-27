package br.com.alexalmeida.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException(String companyNotFound){
        super("Usuário já existe.");
    }
}
