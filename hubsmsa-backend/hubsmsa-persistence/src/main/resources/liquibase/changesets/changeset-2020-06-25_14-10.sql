--liquibase formatted sql

--changeset alisson.souza:2020-06-25_14-10.sql labels:2.1.0
DROP SEQUENCE IF EXISTS sq_de_para;
DROP SEQUENCE IF EXISTS sq_de_para_aud;
DROP SEQUENCE IF EXISTS sq_de_para_primario;
DROP SEQUENCE IF EXISTS sq_de_para_primario_aud;
DROP SEQUENCE IF EXISTS sq_de_para_secundario;
DROP SEQUENCE IF EXISTS sq_de_para_secundario_aud;
DROP TABLE IF EXISTS de_para;
DROP TABLE IF EXISTS de_para_aud;
DROP TABLE IF EXISTS de_para_primario;
DROP TABLE IF EXISTS de_para_primario_aud;
DROP TABLE IF EXISTS de_para_secundario;
DROP TABLE IF EXISTS de_para_secundario_aud;

CREATE SEQUENCE sq_de_para
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE sq_de_para_aud
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE SEQUENCE sq_de_para_primario
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE sq_de_para_primario_aud
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE SEQUENCE sq_de_para_secundario
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE sq_de_para_secundario_aud
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE de_para (
	id_de_para BIGINT NOT NULL default nextval('sq_de_para'),
	id_tipo_de_para BIGINT NOT NULL,
	id_sistema_primario BIGINT NOT NULL,
	id_sistema_secundario BIGINT NOT NULL,
	nome VARCHAR(100) NOT NULL,
	ativo CHARACTER(1) NOT NULL,
	data_atualizacao DATE NOT NULL,
	ind_forma_cadastro CHARACTER(1) NOT NULL,
	CONSTRAINT pk_de_para PRIMARY KEY (id_de_para),
	CONSTRAINT fk_tipo_de_para_de_para FOREIGN KEY (id_tipo_de_para) REFERENCES tipo_de_para(id_tipo_de_para),
	CONSTRAINT fk_sistema_de_para_primario FOREIGN KEY (id_sistema_primario) REFERENCES sistema(id_sistema),
	CONSTRAINT fk_sistema_de_para_secundario FOREIGN KEY (id_sistema_secundario) REFERENCES sistema(id_sistema)
	
);

CREATE INDEX ix_de_para_01
ON de_para(id_tipo_de_para);

CREATE INDEX ix_de_para_02
ON de_para(id_sistema_primario);

CREATE INDEX ix_de_para_03
ON de_para(id_sistema_secundario);

COMMENT ON TABLE de_para IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de De/Para';
COMMENT ON COLUMN  de_para.id_de_para IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  de_para.id_tipo_de_para IS 'Chave primaria da tabela tipo_de_para.';
COMMENT ON COLUMN  de_para.id_sistema_primario IS 'Chave primaria da tabela sistema que identifica um sistema primario.';
COMMENT ON COLUMN  de_para.id_sistema_secundario IS 'Chave primaria da tabela sistema que identifica um sistema secundario.';
COMMENT ON COLUMN  de_para.nome IS 'Status do registro.';
COMMENT ON COLUMN  de_para.ativo IS 'Indicador de status.  --  Valores validos: A - Ativo I - Inativo.';
COMMENT ON COLUMN  de_para.data_atualizacao IS 'Data de atualizacao d. Lei de formacao: DD/MM/AAAA onde DD e Dia, MM e mes, AAAA e ano.';
COMMENT ON COLUMN  de_para.ind_forma_cadastro IS 'Indica a forma de cadastro.  --  Valores validos:  --  - I: Carga Inicial - C: Cadastro Manual - A: Carga Inicial Alterada.';

CREATE TABLE de_para_aud (
	id_de_para BIGINT NOT NULL default nextval('sq_de_para_aud'),
	id_tipo_de_para BIGINT NULL,
	id_sistema_primario BIGINT NULL,
	id_sistema_secundario BIGINT NULL,
	nome VARCHAR(100) NULL,
	codigo_primario BIGINT NULL,
	codigo_secundario BIGINT NULL,
	descricao_primario VARCHAR(75) NULL,
	descricao_secundario VARCHAR(75) NULL,
	ativo CHARACTER(1) NULL,
	data_atualizacao DATE  NULL,
	ind_forma_cadastro CHARACTER(1) NULL,
	id_revisao BIGINT NOT NULL,
	rev_type BIGINT NOT NULL,
	CONSTRAINT pk_de_para_aud PRIMARY KEY (id_de_para, id_revisao),
	CONSTRAINT fk_revisao_de_para_aud FOREIGN KEY (id_revisao) REFERENCES revisao(id_revisao)
);

COMMENT ON TABLE de_para_aud IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de De/Para';
COMMENT ON COLUMN  de_para_aud.id_de_para IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  de_para_aud.id_tipo_de_para IS 'Chave primaria da tabela tipo_de_para.';
COMMENT ON COLUMN  de_para_aud.id_sistema_primario IS 'Chave primaria da tabela sistema que identifica um sistema primario.';
COMMENT ON COLUMN  de_para_aud.id_sistema_secundario IS 'Chave primaria da tabela sistema que identifica um sistema secundario.';
COMMENT ON COLUMN  de_para_aud.nome IS 'Status do registro.';
COMMENT ON COLUMN  de_para_aud.codigo_primario IS 'Codigo primario do registro.';
COMMENT ON COLUMN  de_para_aud.codigo_secundario IS 'Codigo secundario do registro.';
COMMENT ON COLUMN  de_para_aud.descricao_primario IS 'Descricao primaria do registro.';
COMMENT ON COLUMN  de_para_aud.descricao_secundario IS 'Descricao secundaria do registro.';
COMMENT ON COLUMN  de_para_aud.ativo IS 'Indicador de status.  --  Valores validos: A - Ativo I - Inativo.';
COMMENT ON COLUMN  de_para_aud.data_atualizacao IS 'Data de atualizacao d. Lei de formacao: DD/MM/AAAA onde DD e Dia, MM e mes, AAAA e ano.';
COMMENT ON COLUMN  de_para_aud.ind_forma_cadastro IS 'Indica a forma de cadastro.  --  Valores validos:  --  - I: Carga Inicial - C: Cadastro Manual - A: Carga Inicial Alterada.';
COMMENT ON COLUMN  de_para_aud.id_revisao IS 'Chave primaria da tabela revisao que identifica o numero da revisao que esta sendo representada pelo historico de auditoria.';
COMMENT ON COLUMN  de_para_aud.rev_type IS 'Identificar do tipo de revisao feita para o registro no historico de auditoria. Valores validos: 0 – Inclusao 1- Alteracao 2 – Delecao fisica';

CREATE TABLE de_para_primario (
	id_de_para_primario BIGINT NOT NULL default nextval('sq_de_para_primario'),
	id_de_para BIGINT NOT NULL,
	codigo BIGINT NOT NULL,
	descricao VARCHAR(75) NOT NULL,
	CONSTRAINT pk_de_para_primario PRIMARY KEY (id_de_para_primario),
	CONSTRAINT fk_de_para_de_para_primario FOREIGN KEY (id_de_para) REFERENCES de_para(id_de_para)
);

CREATE INDEX ix_de_para_primario_01
ON de_para_primario(codigo);

COMMENT ON TABLE de_para_primario IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de De/Para Primario';
COMMENT ON COLUMN  de_para_primario.id_de_para_primario IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  de_para_primario.id_de_para IS 'Campo com o ID do De/Para relacionado.';
COMMENT ON COLUMN  de_para_primario.codigo IS 'Codigo do De/Para.';
COMMENT ON COLUMN  de_para_primario.descricao IS 'Descricaoo do De/Para.';


CREATE TABLE de_para_primario_aud (
	id_de_para_primario BIGINT NOT NULL default nextval('sq_de_para_primario_aud'),
	id_de_para BIGINT NULL,
	codigo BIGINT NULL,
	descricao VARCHAR(75) NULL,
	data_atualizacao DATE  NULL,
	ind_forma_cadastro CHARACTER(1) NULL,
	id_revisao BIGINT NOT NULL,
	rev_type BIGINT NOT NULL,
	CONSTRAINT pk_de_para_primario_aud PRIMARY KEY (id_de_para_primario, id_revisao),
	CONSTRAINT fk_revisao_de_para_primario_aud FOREIGN KEY (id_revisao) REFERENCES revisao(id_revisao)
);

CREATE TABLE de_para_secundario (
	id_de_para_secundario BIGINT NOT NULL default nextval('sq_de_para_secundario'),
	id_de_para BIGINT NOT NULL,
	codigo BIGINT NOT NULL,
	descricao VARCHAR(75) NOT NULL,
	CONSTRAINT pk_de_para_secundario PRIMARY KEY (id_de_para_secundario),
	CONSTRAINT fk_de_para_de_para_secundario FOREIGN KEY (id_de_para) REFERENCES de_para(id_de_para)
);

CREATE INDEX ix_de_para_secundario_01
ON de_para_secundario(codigo);

COMMENT ON TABLE de_para_secundario IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de De/Para Secundario';
COMMENT ON COLUMN  de_para_secundario.id_de_para_secundario IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  de_para_secundario.id_de_para IS 'Campo com o ID do De/Para relacionado.';
COMMENT ON COLUMN  de_para_secundario.codigo IS 'Codigo do De/Para.';
COMMENT ON COLUMN  de_para_secundario.descricao IS 'Descricaoo do De/Para.';

CREATE TABLE de_para_secundario_aud (
	id_de_para_secundario BIGINT NOT NULL default nextval('sq_de_para_secundario_aud'),
	id_de_para BIGINT  NULL,
	codigo BIGINT  NULL,
	descricao VARCHAR(75) NULL,
	data_atualizacao DATE NULL,
	ind_forma_cadastro CHARACTER(1) NULL,
	id_revisao BIGINT NOT NULL,
	rev_type BIGINT NOT NULL,
	CONSTRAINT pk_de_para_secundario_aud PRIMARY KEY (id_de_para_secundario, id_revisao),
	CONSTRAINT fk_revisao_de_para_secundario_aud FOREIGN KEY (id_revisao) REFERENCES revisao(id_revisao)
	
);


--rollback drop sequence if exists sq_de_para;
--rollback drop sequence if exists sq_de_para_aud;
--rollback drop sequence if exists sq_de_para_primario;
--rollback drop sequence if exists sq_de_para_primario_aud;
--rollback drop sequence if exists sq_de_para_secundario;
--rollback drop sequence if exists sq_de_para_secundario_aud;
--rollback drop table if exists de_para_primario;
--rollback drop table if exists de_para_primario_aud;
--rollback drop table if exists de_para_secundario;
--rollback drop table if exists de_para_secundario_aud;
--rollback drop table if exists de_para;
--rollback drop table if exists de_para_aud;


