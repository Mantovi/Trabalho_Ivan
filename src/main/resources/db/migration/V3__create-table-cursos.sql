create table cursos (
    id int primary key not null auto_increment,
    nome varchar(100) not null unique,
    codigo varchar(20) not null unique,
    carga_horaria int not null
)