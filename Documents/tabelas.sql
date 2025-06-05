CREATE SCHEMA unimind;

SELECT table_name 
FROM information_schema.tables
WHERE table_schema = 'unimind';

SELECT * FROM unimind.NivelEstudo;
SELECT * FROM unimind.Usuario;
SELECT * FROM unimind.Fonte;
SELECT * FROM unimind.Categoria;
SELECT * FROM unimind.Prova;
SELECT * FROM unimind.Questao;
SELECT * FROM unimind.ListaPersonalizada;
SELECT * FROM unimind.Flashcard;
SELECT * FROM unimind.Competicao;



CREATE TABLE unimind.NivelEstudo (
    idNivel INT PRIMARY KEY, -- sem IDENTITY
    tipo VARCHAR(50) NOT NULL
);

INSERT INTO unimind.NivelEstudo (tipo)
VALUES ('Cotuca');


CREATE TABLE unimind.Usuario (
    idUsuario INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    idNivel INT,
    acertosQuestoes INT DEFAULT 0,
    errosQuestoes INT DEFAULT 0,
    tempoEstudo INT DEFAULT 0, -- tempo em minutos
    competicoesRealizadas INT DEFAULT 0,
    FOREIGN KEY (idNivel) REFERENCES unimind.NivelEstudo(idNivel)
);

INSERT INTO unimind.Usuario (nome, email, senha, idNivel)
VALUES ('marietti', 'marietti@gmail.com', 'marietti123', 1);

SELECT * FROM unimind.Usuario WHERE email = 'marietti@gmail.com' AND senha = 'marietti123'


CREATE TABLE unimind.Fonte (
    idFonte INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

INSERT INTO unimind.Fonte (nome)
VALUES ('ENEM'),
       ('FUVEST'),
       ('UNICAMP'),
       ('ITA');


CREATE TABLE unimind.Categoria (
    idCategoria INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

INSERT INTO unimind.Categoria (nome)
VALUES ('Matemática'),
       ('Física'),
       ('Química'),
       ('Biologia'),
       ('História'),
       ('Geografia'),
       ('Inglês'),
       ('Português');


CREATE TABLE unimind.Prova (
    idProva INT IDENTITY(1,1) PRIMARY KEY,
    idFonte INT,
    ano INT NOT NULL,
    qntdQuestoes INT NOT NULL,
    fase VARCHAR(50),
    FOREIGN KEY (idFonte) REFERENCES unimind.Fonte(idFonte)
);

INSERT INTO unimind.Prova (idFonte, ano, qntdQuestoes, fase)
VALUES (1, 2022, 90, NULL),
       (2, 2021, 60, '1ª fase'),
       (3, 2020, 72, '2ª fase'),
       (4, 2023, 50, NULL);


CREATE TABLE unimind.Questao (
    idQuestao INT IDENTITY(1,1) PRIMARY KEY,
    idProva INT,
    idCategoria INT,
    questao TEXT NOT NULL,
    resposta TEXT NOT NULL,
    FOREIGN KEY (idProva) REFERENCES unimind.Prova(idProva),
    FOREIGN KEY (idCategoria) REFERENCES unimind.Categoria(idCategoria)
);

INSERT INTO unimind.Questao (idProva, idCategoria, questao, resposta)
VALUES 
(1, 1, 'Qual é o valor de x na equação 2x + 3 = 7?', 'x = 2'),
(1, 2, 'O que é aceleração?', 'Variação da velocidade no tempo'),
(2, 3, 'Qual é o número atômico do oxigênio?', '8'),
(2, 4, 'O que é fotossíntese?', 'Processo de produção de glicose pelas plantas'),
(3, 5, 'Quem foi o primeiro presidente do Brasil?', 'Deodoro da Fonseca'),
(3, 6, 'O que é longitude?', 'Distância em graus a leste ou oeste do meridiano de Greenwich'),
(4, 7, 'Traduza: "She is reading a book."', 'Ela está lendo um livro.'),
(4, 8, 'O que é uma oração subordinada?', 'É uma oração que exerce uma função sintática dentro de outra.');


CREATE TABLE unimind.ListaPersonalizada (
    idLista INT IDENTITY(1,1) PRIMARY KEY,
    idUsuario INT,
    idCategoria INT,
    idFonte INT NULL,
    ano INT NULL,
    FOREIGN KEY (idUsuario) REFERENCES unimind.Usuario(idUsuario),
    FOREIGN KEY (idCategoria) REFERENCES unimind.Categoria(idCategoria),
    FOREIGN KEY (idFonte) REFERENCES unimind.Fonte(idFonte)
);

INSERT INTO unimind.ListaPersonalizada (idUsuario, idCategoria, idFonte, ano)
VALUES 
(3, 1, 1, 2022),
(11, 2, 2, 2021),
(11, 3, 3, 2020),
(3, 4, NULL, NULL);


CREATE TABLE unimind.Flashcard (
    idFlashcard INT IDENTITY(1,1) PRIMARY KEY,
    idUsuario INT,
    perguntaUsuario TEXT NOT NULL,
    respostaUsuario TEXT NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES unimind.Usuario(idUsuario)
);

INSERT INTO unimind.Flashcard (idUsuario, perguntaUsuario, respostaUsuario)
VALUES 
(11, 'O que é força?', 'É uma interação capaz de alterar o movimento de um corpo.'),
(3, 'Qual é a fórmula da velocidade média?', 'Vm = ?s/?t'),
(3, 'Traduza: "Good morning"', 'Bom dia'),
(11, 'O que é uma célula?', 'Unidade estrutural e funcional dos seres vivos.');


CREATE TABLE unimind.Competicao (
    idCompeticao INT IDENTITY(1,1) PRIMARY KEY,
    data DATE NOT NULL,
    idUsuario1 INT,
    idUsuario2 INT,
    idNivel INT,
    FOREIGN KEY (idUsuario1) REFERENCES unimind.Usuario(idUsuario),
    FOREIGN KEY (idUsuario2) REFERENCES unimind.Usuario(idUsuario),
    FOREIGN KEY (idNivel) REFERENCES unimind.NivelEstudo(idNivel)
);

INSERT INTO unimind.Competicao (data, idUsuario1, idUsuario2, idNivel)
VALUES 
('2024-05-20', 11, 3, 1),
('2024-05-21', 3, 11, 1);

