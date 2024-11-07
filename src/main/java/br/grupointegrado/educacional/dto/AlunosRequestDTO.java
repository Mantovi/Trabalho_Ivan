package br.grupointegrado.educacional.dto;

import java.util.Date;

public record AlunosRequestDTO(String nome, String email, String matricula, Date data_nascimento) {
}
