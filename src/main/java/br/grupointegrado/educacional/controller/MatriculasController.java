package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.MatriculaRequestDTO;
import br.grupointegrado.educacional.model.Aluno;
import br.grupointegrado.educacional.model.Matricula;
import br.grupointegrado.educacional.model.Turma;
import br.grupointegrado.educacional.repository.AlunoRepository;
import br.grupointegrado.educacional.repository.MatriculaRepository;
import br.grupointegrado.educacional.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculasController {

    @Autowired
    MatriculaRepository repository;

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    TurmaRepository turmaRepository;

    @GetMapping
    public ResponseEntity<List<Matricula>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula> findById(@PathVariable Integer id) {
        Matricula matricula = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matriculation Not Found."));

        return ResponseEntity.ok(matricula);
    }

    @PostMapping
    public ResponseEntity<Matricula> save(@RequestBody MatriculaRequestDTO dto) {
        Matricula matricula = new Matricula();

        Aluno aluno = this.alunoRepository.findById(dto.aluno_id())
                .orElseThrow(() -> new IllegalArgumentException("Student Not Found."));

        matricula.setAluno(aluno);

        Turma turma = this.turmaRepository.findById(dto.turma_id())
                .orElseThrow(() -> new IllegalArgumentException("Class Not Found."));

        matricula.setTurma(turma);

        Matricula savedMatricula = this.repository.save(matricula);

        return ResponseEntity.ok(savedMatricula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matricula> update(@PathVariable Integer id, @RequestBody MatriculaRequestDTO dto) {
        Matricula matricula = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matriculation Not Found."));

        Aluno aluno = this.alunoRepository.findById(dto.aluno_id())
                .orElseThrow(() -> new IllegalArgumentException("Student Not Found."));

        matricula.setAluno(aluno);

        Turma turma = this.turmaRepository.findById(dto.aluno_id())
                .orElseThrow(() -> new IllegalArgumentException("Class Not Found."));

        matricula.setTurma(turma);
        Matricula savedMatricula = this.repository.save(matricula);

        return ResponseEntity.ok(savedMatricula);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Matricula matricula = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matriculation Not Found."));

        this.repository.delete(matricula);
        return ResponseEntity.noContent().build();
    }



}
