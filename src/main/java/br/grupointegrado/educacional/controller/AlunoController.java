package br.grupointegrado.educacional.controller;

import br.grupointegrado.educacional.dto.AlunoRequestDTO;
import br.grupointegrado.educacional.dto.DisciplinaResponseDTO;
import br.grupointegrado.educacional.dto.NotaResponseDTO;
import br.grupointegrado.educacional.dto.RelatorioNotasResponseDTO;
import br.grupointegrado.educacional.model.Aluno;
import br.grupointegrado.educacional.model.Matricula;
import br.grupointegrado.educacional.model.Nota;
import br.grupointegrado.educacional.model.Turma;
import br.grupointegrado.educacional.repository.AlunoRepository;
import br.grupointegrado.educacional.repository.MatriculaRepository;
import br.grupointegrado.educacional.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping
    public ResponseEntity<List<Aluno>> findAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }
        //    public List<Aluno> findAll(){
        //        return this.repository.findAll();
        //    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Integer id) {
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student Not Found"));

        return ResponseEntity.ok(aluno);
    }
        //    public Aluno findById(@PathVariable Integer id){
        //        return this.repository.findById(id)
        //                .orElseThrow(() -> new IllegalArgumentException("Not Found"));
        //    }

    @PostMapping
    public Aluno save(@RequestBody AlunoRequestDTO dto){
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setMatricula(dto.matricula());
        aluno.setDataNascimento(dto.data_nascimento());

        return this.repository.save(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> update(@PathVariable Integer id, @RequestBody AlunoRequestDTO dto){
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student Not Found"));

        aluno.setNome(dto.nome());
        aluno.setEmail(dto.email());
        aluno.setMatricula(dto.matricula());
        aluno.setDataNascimento(dto.data_nascimento());

        return ResponseEntity.ok(this.repository.save(aluno));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Aluno aluno = this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student Not Found"));

        this.repository.delete(aluno);
        return ResponseEntity.noContent().build();
    }

        //    public void delete(@PathVariable Integer id){
        //        Aluno aluno = this.repository.findById(id)
        //                .orElseThrow(() -> new IllegalArgumentException("Not Found"));
        //        this.repository.delete(aluno);
        //    }
        //}

    @PostMapping("/{aluno_id}/matricula")
    public ResponseEntity<Aluno> adicionaMatricula(@PathVariable Integer aluno_id, @RequestBody Integer turma_id){
        Aluno aluno = this.repository.findById(aluno_id)
                .orElseThrow(() -> new IllegalArgumentException("Student Not Found"));
        Turma turma = this.turmaRepository.findById(turma_id)
                .orElseThrow(() -> new IllegalArgumentException("Class Not Found"));

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setTurma(turma);

        boolean matriculaExistente = aluno.getMatriculas().stream()
                .anyMatch(matri -> matri.getTurma().getId().equals(turma_id));

        if (!matriculaExistente) {
            aluno.addMatricula(matricula);
        } else {
            throw new IllegalArgumentException("Student already enrolled in this class");
        }

        Aluno alunoNota = this.repository.save(aluno);
        return ResponseEntity.ok(alunoNota);
    }


    @GetMapping("/{aluno_id}/RelatorioNotas")
    public ResponseEntity<RelatorioNotasResponseDTO> getNotas(@PathVariable Integer aluno_id) {

        Aluno aluno = this.repository.findById(aluno_id)
                .orElseThrow(() -> new IllegalArgumentException("Student Not Found"));

        List<NotaResponseDTO> notas = new ArrayList<>();

        if (!aluno.getMatriculas().isEmpty()) {
            for (Matricula matricula : aluno.getMatriculas()) {
                for (Nota nota : matricula.getNotas()) {

                    notas.add(
                            new NotaResponseDTO(
                                    nota.getNota(),
                                    nota.getData_lancamento(),

                                    new DisciplinaResponseDTO(
                                            nota.getDisciplina().getNome(),
                                            nota.getDisciplina().getCodigo()
                                    )
                            )
                    );
                }
            }
        }
            return ResponseEntity.ok(new RelatorioNotasResponseDTO(notas));
    }


}


