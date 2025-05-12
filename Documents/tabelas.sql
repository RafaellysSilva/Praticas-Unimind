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

CREATE TABLE unimind.Categoria (
    idCategoria INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE unimind.Prova (
    idProva INT IDENTITY(1,1) PRIMARY KEY,
    idFonte INT,
    ano INT NOT NULL,
    qntdQuestoes INT NOT NULL,
    fase VARCHAR(50),
    FOREIGN KEY (idFonte) REFERENCES unimind.Fonte(idFonte)
);

CREATE TABLE unimind.Questao (
    idQuestao INT IDENTITY(1,1) PRIMARY KEY,
    idProva INT,
    idCategoria INT,
    questao TEXT NOT NULL,
    resposta TEXT NOT NULL,
    FOREIGN KEY (idProva) REFERENCES unimind.Prova(idProva),
    FOREIGN KEY (idCategoria) REFERENCES unimind.Categoria(idCategoria)
);

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

CREATE TABLE unimind.Flashcard (
    idFlashcard INT IDENTITY(1,1) PRIMARY KEY,
    idUsuario INT,
    perguntaUsuario TEXT NOT NULL,
    respostaUsuario TEXT NOT NULL,
    FOREIGN KEY (idUsuario) REFERENCES unimind.Usuario(idUsuario)
);

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
