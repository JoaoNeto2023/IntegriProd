-- PARTE 1: TABELAS BASE
CREATE TABLE tipo_produto (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(10) UNIQUE NOT NULL,
    descricao VARCHAR(50) NOT NULL,
    natureza VARCHAR(20) CHECK (natureza IN ('MP', 'PA', 'IN', 'EM', 'SU'))
);

CREATE TABLE unidade_medida (
    sigla VARCHAR(10) PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL
);

CREATE TABLE familia_produto (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE posto_trabalho (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    setor VARCHAR(50),
    custo_hora DECIMAL(15,2),
    custo_fixo_hora DECIMAL(15,2),
    status VARCHAR(20) DEFAULT 'ATIVO'
);

CREATE TABLE empresa (
    id BIGSERIAL PRIMARY KEY,
    razao_social VARCHAR(200) NOT NULL,
    nome_fantasia VARCHAR(200),
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    inscricao_estadual VARCHAR(20),
    inscricao_municipal VARCHAR(20),
    regime_tributario CHAR(1) CHECK (regime_tributario IN ('1', '2', '3')),
    status VARCHAR(20) DEFAULT 'ATIVO',
    data_cadastro DATE DEFAULT CURRENT_DATE
);

CREATE TABLE filial (
    id BIGSERIAL PRIMARY KEY,
    empresa_id BIGINT REFERENCES empresa(id),
    codigo VARCHAR(10) NOT NULL,
    nome VARCHAR(200) NOT NULL,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    ie VARCHAR(20),
    endereco TEXT,
    cidade VARCHAR(100),
    uf CHAR(2),
    cep VARCHAR(10),
    telefone VARCHAR(20),
    email VARCHAR(100),
    gerente_responsavel VARCHAR(100),
    status VARCHAR(20) DEFAULT 'ATIVO',
    UNIQUE(empresa_id, codigo)
);

CREATE TABLE pessoa (
    id BIGSERIAL PRIMARY KEY,
    tipo_pessoa CHAR(2) CHECK (tipo_pessoa IN ('PF', 'PJ')),
    nome_razao VARCHAR(200) NOT NULL,
    nome_fantasia VARCHAR(200),
    cpf_cnpj VARCHAR(18) UNIQUE NOT NULL,
    rg_ie VARCHAR(20),
    data_nascimento DATE,
    sexo CHAR(1) CHECK (sexo IN ('M', 'F')),
    estado_civil VARCHAR(20),
    profissao VARCHAR(100),
    email VARCHAR(100),
    telefone1 VARCHAR(20),
    telefone2 VARCHAR(20),
    celular VARCHAR(20),
    site VARCHAR(100),
    observacoes TEXT,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    situacao VARCHAR(20) DEFAULT 'ATIVO'
);