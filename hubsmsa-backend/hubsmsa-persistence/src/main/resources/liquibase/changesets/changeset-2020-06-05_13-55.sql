--liquibase formatted sql

--changeset andre.moreira:2020_06_05_13-55 labels:1.0.1
drop sequence if exists sq_empresa;
drop sequence if exists sq_empresa_aud;
drop table if exists  empresa;
drop table if exists empresa_aud;

CREATE SEQUENCE sq_empresa
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE sq_empresa_aud
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE TABLE empresa
(
	id_empresa BIGINT NOT NULL default nextval('sq_empresa'),
	nome_empresarial  VARCHAR(100) NOT NULL,
	nome_fantasia VARCHAR(100) NOT NULL,
	cnpj VARCHAR(14) NOT NULL,
	codigo_cnes BIGINT NULL,
	site VARCHAR(100) NULL,	
	ativo CHARACTER(1) NOT NULL,
	CONSTRAINT pk_id_empresa PRIMARY KEY (id_empresa)
);

COMMENT ON TABLE empresa IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de empresas.';
COMMENT ON COLUMN  empresa.id_empresa IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  empresa.nome_empresarial IS 'Nome Empresarial é a firma ou a denominação adotada para o exercício de empresa.';
COMMENT ON COLUMN  empresa.nome_fantasia IS 'Nome Fantasia é a designação popular de título de estabelecimento utilizada por uma instituição .';
COMMENT ON COLUMN  empresa.cnpj IS 'CNPJ da empresa';
COMMENT ON COLUMN  empresa.site IS 'O dominio em que se encontra o site da empresa';
COMMENT ON COLUMN  empresa.ativo IS 'Indica se a empresa está com a situação ativa ou inativa --  Valores validos: A - Ativo I - Inativo';


CREATE TABLE empresa_aud (
	id_empresa BIGINT default nextval('sq_empresa_aud'),
	nome_empresarial  VARCHAR(100) ,
	nome_fantasia VARCHAR(100) ,
	cnpj VARCHAR(14),
	codigo_cnes BIGINT ,
	site VARCHAR(100) ,	
	ativo CHARACTER(1) ,
	id_revisao BIGINT NOT NULL,
	rev_type BIGINT NOT NULL,
	CONSTRAINT pk_empresa_aud PRIMARY KEY (id_empresa, id_revisao),
	CONSTRAINT fk_revisao_empresa_aud FOREIGN KEY (id_revisao) REFERENCES revisao(id_revisao)
);

COMMENT ON TABLE empresa_aud IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de empresas.';
COMMENT ON COLUMN  empresa_aud.id_empresa IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  empresa_aud.nome_empresarial IS 'Nome Empresarial é a firma ou a denominação adotada para o exercício de empresa.';
COMMENT ON COLUMN  empresa_aud.nome_fantasia IS 'Nome Fantasia é a designação popular de título de estabelecimento utilizada por uma instituição .';
COMMENT ON COLUMN  empresa_aud.id_revisao IS 'Chave primaria da tabela revisao que identifica o numero da revisao que esta sendo representada pelo historico de auditoria.';
COMMENT ON COLUMN  empresa_aud.rev_type IS 'Identificar do tipo de revisao feita para o registro no historico de auditoria. Valores validos: 0 – Inclusao 1- Alteracao 2 – Delecao fisica';
COMMENT ON COLUMN  empresa_aud.cnpj IS 'CNPJ da empresa';
COMMENT ON COLUMN  empresa_aud.site IS 'O dominio em que se encontra o site da empresa';
COMMENT ON COLUMN  empresa_aud.ativo IS 'Indica se a empresa está com a situação ativa ou inativa --  Valores validos: A - Ativo I - Inativo';

--rollback drop sequence if exists sq_empresa;
--rollback drop sequence if exists sq_empresa_aud;
--rollback drop table if exists  empresa;
--rollback drop table if exists  empresa_aud;