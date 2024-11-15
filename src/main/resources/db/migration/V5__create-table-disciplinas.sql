CREATE TABLE disciplinas (
    id int not null auto_increment primary key,
    nome varchar(100) not null,
    codigo varchar(20) not null unique,
    curso_id int not null,
    professor_id int not null,
    FOREIGN KEY (curso_id) REFERENCES cursos(id),
    FOREIGN KEY (professor_id) REFERENCES professores(id)
)