package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.TurmaRequestDTO;
import br.grupointegrado.educacional.model.Curso;
import br.grupointegrado.educacional.model.Matricula;
import br.grupointegrado.educacional.model.Nota;
import br.grupointegrado.educacional.model.Turma;
import br.grupointegrado.educacional.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    @Autowired
    private TurmaRepository repository;
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    MatriculaRepository matriculaRepository;

    @GetMapping
    public ResponseEntity<List<Turma>> findAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> findById(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Class Not Found"));

        return ResponseEntity.ok(turma);
    }

    @GetMapping("/{id}/notas")
    public ResponseEntity<List<Nota>> getNotes(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrado."));

        List<Nota> nota = new ArrayList<>();
        for(Matricula matricula : turma.getMatriculas()) {
            nota.addAll(matricula.getNotas());
        }

        return ResponseEntity.ok(nota);
    }

    @PostMapping
    public ResponseEntity<Turma> save(@RequestBody TurmaRequestDTO dto) {

        Turma turma = new Turma();
        turma.setSemestre(dto.semestre());
        turma.setAno(dto.ano());

        Curso curso= this.cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Curse Not Found"));


        turma.setCurso(curso);
        Turma turmaSaved = this.repository.save(turma);
        return ResponseEntity.ok(turmaSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> update(@PathVariable Integer id, @RequestBody TurmaRequestDTO dto) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Class Not Found"));

        turma.setAno(dto.ano());
        turma.setSemestre(dto.semestre());

        Curso curso = cursoRepository.findById(dto.curso_id())
                .orElseThrow(() -> new IllegalArgumentException("Course Not Found"));

        turma.setCurso(curso);

        Turma turmaSaved = this.repository.save(turma);

        return ResponseEntity.ok(turmaSaved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Turma turma = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Class with ID" + id + "Not Found"));

        this.repository.delete(turma);
        return ResponseEntity.noContent().build();
    }
}
