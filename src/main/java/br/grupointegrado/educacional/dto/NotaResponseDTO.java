package br.grupointegrado.educacional.dto;

public record NotaResponseDTO(Integer nota, java.time.LocalDate data_lancamento, DisciplinaResponseDTO disciplina) {
}
