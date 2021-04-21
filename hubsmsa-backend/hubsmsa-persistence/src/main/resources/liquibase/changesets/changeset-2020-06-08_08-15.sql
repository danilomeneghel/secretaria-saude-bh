--liquibase formatted sql

--changeset alisson.souza:2020_06-08_08-15 labels:1.0.1
DROP SEQUENCE IF EXISTS sq_sistema;
DROP SEQUENCE IF EXISTS sq_sistema_aud;
DROP TABLE IF EXISTS sistema;
DROP TABLE IF EXISTS sistema_aud;

CREATE SEQUENCE sq_sistema
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE SEQUENCE sq_sistema_aud
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE sistema (
	id_sistema BIGINT NOT NULL default nextval('sq_sistema'),
	id_empresa BIGINT NOT NULL,
	nome VARCHAR(100) NOT NULL,
	descricao VARCHAR(250) NOT NULL,
	ativo CHARACTER(1) NOT NULL,
	data_atualizacao DATE NOT NULL,
	ind_forma_cadastro CHARACTER(1) NOT NULL,
	CONSTRAINT pk_sistema PRIMARY KEY (id_sistema),
	CONSTRAINT fk_sistema_empresa FOREIGN KEY (id_empresa) REFERENCES empresa(id_empresa)
);

CREATE INDEX ix_sistema_01
ON sistema(id_empresa);

COMMENT ON TABLE sistema IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de sistemas';
COMMENT ON COLUMN  sistema.id_sistema IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  sistema.nome IS 'Nome do registro.';
COMMENT ON COLUMN  sistema.descricao IS 'Descricao do registro.';
COMMENT ON COLUMN  sistema.ativo IS 'Status do registro.';

CREATE TABLE sistema_aud (
	id_sistema BIGINT NOT NULL default nextval('sq_sistema_aud'),
	id_empresa BIGINT,
	nome VARCHAR(100),
	descricao VARCHAR(250),
	ativo CHARACTER(1),
	data_atualizacao DATE NULL,
	ind_forma_cadastro CHARACTER(1) NULL,
	id_revisao BIGINT NOT NULL,
	rev_type BIGINT NOT NULL,
	CONSTRAINT pk_sistema_aud PRIMARY KEY (id_sistema, id_revisao),
	CONSTRAINT fk_revisao_sistema_aud FOREIGN KEY (id_revisao) REFERENCES revisao(id_revisao)
);

COMMENT ON TABLE sistema_aud IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de sistemas.';
COMMENT ON COLUMN  sistema_aud.id_sistema IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  sistema_aud.nome IS 'Nome do registro.';
COMMENT ON COLUMN  sistema_aud.descricao IS 'Descricao do registro.';
COMMENT ON COLUMN  sistema_aud.ativo IS 'Indicador de status.';
COMMENT ON COLUMN  sistema_aud.data_atualizacao IS 'Data de atualizacao da registro. Lei de formacao: DD/MM/AAAA onde DD e Dia, MM e mes, AAAA e ano.';
COMMENT ON COLUMN  sistema_aud.ind_forma_cadastro IS 'Indica a forma de cadastro.  --  Valores validos:  --  - I: Carga Inicial - C: Cadastro Manual - A: Carga Inicial Alterada';
COMMENT ON COLUMN  sistema_aud.id_revisao IS 'Chave primaria da tabela revisao que identifica o numero da revisao que esta sendo representada pelo historico de auditoria.';
COMMENT ON COLUMN  sistema_aud.rev_type IS 'Identificar do tipo de revisao feita para o registro no historico de auditoria. Valores validos: 0 – Inclusao 1- Alteracao 2 – Delecao fisica';


--rollback drop sequence if exists sq_sistema;
--rollback drop sequence if exists sq_sistema_aud;
--rollback drop table if exists sistema;
--rollback drop table if exists sistema_aud;


