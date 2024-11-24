CREATE TABLE alunos (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL unique,
    matricula VARCHAR(20) NOT NULL unique,
    data_nascimento DATE NOT NULL
);
