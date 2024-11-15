create table matriculas (
    id int not null primary key auto_increment,
    aluno_id int not null,
    turma_id int not null,
    FOREIGN KEY (aluno_id) REFERENCES alunos(id),
    FOREIGN KEY (turma_id) REFERENCES turmas(id)
)