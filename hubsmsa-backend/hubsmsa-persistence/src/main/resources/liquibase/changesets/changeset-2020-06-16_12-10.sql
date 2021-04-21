--liquibase formatted sql

--changeset andre.moreira:2020_06-16_12-10 labels:1.0.1
DROP SEQUENCE IF EXISTS sq_contato_empresa;
DROP SEQUENCE IF EXISTS sq_contato_empresa_aud;
DROP TABLE IF EXISTS contato_empresa;
DROP TABLE IF EXISTS contato_empresa_aud;

CREATE SEQUENCE sq_contato_empresa
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE SEQUENCE sq_contato_empresa_aud
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE contato_empresa(
	id_contato_empresa BIGINT NOT NULL default nextval('sq_contato_empresa'),
	id_empresa BIGINT NOT NULL,
	nome VARCHAR(100) NOT NULL,
	email VARCHAR(50) NOT NULL,
	telefone VARCHAR(14) NOT NULL,
	setor VARCHAR(50) NOT NULL,
	ativo CHARACTER(1) NOT NULL,
	CONSTRAINT pk_contato_empresa PRIMARY KEY (id_contato_empresa),
	CONSTRAINT fk_empresa_contato_empresa FOREIGN KEY (id_empresa) REFERENCES empresa(id_empresa)
);

CREATE INDEX idx_contato_empresa_01
ON contato_empresa(id_empresa);

COMMENT ON TABLE contato_empresa IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de contato_empresas';
COMMENT ON COLUMN  contato_empresa.id_contato_empresa IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  contato_empresa.nome IS 'Nome do registro.';
COMMENT ON COLUMN  contato_empresa.email IS 'Email do registro.';
COMMENT ON COLUMN  contato_empresa.telefone IS 'Telefone do registro.';
COMMENT ON COLUMN  contato_empresa.setor IS 'Setor do registro.';
COMMENT ON COLUMN  contato_empresa.id_empresa IS 'Chave estrangeira para tabela empresa.';

CREATE TABLE contato_empresa_aud(
	id_contato_empresa BIGINT  default nextval('sq_contato_empresa_aud'),
	id_empresa BIGINT,
	nome VARCHAR(100) ,
	email VARCHAR(50) ,
	telefone VARCHAR(14) ,
	setor VARCHAR(50) ,
	ativo CHARACTER(1) ,
	id_revisao BIGINT NOT NULL,
	rev_type BIGINT NOT NULL,
	data_atualizacao DATE NULL,
	ind_forma_cadastro CHARACTER(1) ,
	CONSTRAINT pk_contato_empresa_aud PRIMARY KEY (id_contato_empresa, id_revisao),
	CONSTRAINT fk_revisao_contato_empresa_aud FOREIGN KEY (id_revisao) REFERENCES revisao(id_revisao)
);

COMMENT ON TABLE contato_empresa_aud IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de contato_empresas.';
COMMENT ON COLUMN  contato_empresa_aud.id_contato_empresa IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  contato_empresa_aud.nome IS 'Nome do registro.';
COMMENT ON COLUMN  contato_empresa_aud.email IS 'Email do registro.';
COMMENT ON COLUMN  contato_empresa_aud.telefone IS 'Telefone do registro.';
COMMENT ON COLUMN  contato_empresa_aud.setor IS 'Setor do registro.';
COMMENT ON COLUMN  contato_empresa_aud.id_empresa IS 'Chave estrangeira para tabela empresa.';
COMMENT ON COLUMN  contato_empresa_aud.id_revisao IS 'Chave primaria da tabela revisao que identifica o numero da revisao que esta sendo representada pelo historico de auditoria.';
COMMENT ON COLUMN  contato_empresa_aud.rev_type IS 'Identificar do tipo de revisao feita para o registro no historico de auditoria. Valores validos: 0 – Inclusao 1- Alteracao 2 – Delecao fisica';
COMMENT ON COLUMN  contato_empresa_aud.data_atualizacao IS 'Data de atualizacao da registro. Lei de formacao: DD/MM/AAAA onde DD e Dia, MM e mes, AAAA e ano.';
COMMENT ON COLUMN  contato_empresa_aud.ind_forma_cadastro IS 'Indica a forma de cadastro.  --  Valores validos:  --  - I: Carga Inicial - C: Cadastro Manual - A: Carga Inicial Alterada';



--rollback drop sequence if exists sq_contato_empresa;
--rollback drop sequence if exists sq_contato_empresa_aud;
--rollback drop table if exists contato_empresa;
--rollback drop table if exists contato_empresa_aud;


