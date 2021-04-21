--liquibase formatted sql

--changeset alisson.souza:2020-06_19_15-15 labels:1.0.0
drop sequence if exists sq_tipo_de_para;
drop sequence if exists sq_tipo_de_para_aud;
drop table if exists  tipo_de_para;
drop table if exists  tipo_de_para_aud;

CREATE SEQUENCE sq_tipo_de_para
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE sq_tipo_de_para_aud
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE TABLE tipo_de_para
(
	id_tipo_de_para BIGINT NOT NULL default nextval('sq_tipo_de_para'),
	nome VARCHAR(100) NOT NULL,
	descricao VARCHAR(250) NOT NULL,
	ativo CHARACTER(1) NULL,
	data_atualizacao DATE NOT NULL,
	ind_forma_cadastro CHARACTER(1) NOT NULL,
	CONSTRAINT pk_tipo_de_para PRIMARY KEY (id_tipo_de_para)
);

COMMENT ON TABLE tipo_de_para IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de Tipo de De/Para.';
COMMENT ON COLUMN  tipo_de_para.id_tipo_de_para IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  tipo_de_para.nome IS 'Nome do registro.';
COMMENT ON COLUMN  tipo_de_para.descricao IS 'Descricao do registro.';
COMMENT ON COLUMN  tipo_de_para.ativo IS 'Indicador de status.  --  Valores validos: A - Ativo I - Inativo';
COMMENT ON COLUMN  tipo_de_para.data_atualizacao IS 'Data de atualizacao d. Lei de formacao: DD/MM/AAAA onde DD e Dia, MM e mes, AAAA e ano.';
COMMENT ON COLUMN  tipo_de_para.ind_forma_cadastro IS 'Indica a forma de cadastro.  --  Valores validos:  --  - I: Carga Inicial - C: Cadastro Manual - A: Carga Inicial Alterada';


CREATE TABLE tipo_de_para_aud (
	id_tipo_de_para BIGINT NOT NULL default nextval('sq_tipo_de_para_aud'),
	nome VARCHAR(100) NULL,
	descricao VARCHAR(250) NULL,
	ativo CHARACTER(1) NULL,
	data_atualizacao DATE NULL,
	ind_forma_cadastro CHARACTER(1) NULL,
	id_revisao BIGINT NOT NULL,
	rev_type BIGINT NOT NULL,
	CONSTRAINT pk_tipo_de_para_aud PRIMARY KEY (id_tipo_de_para, id_revisao),
	CONSTRAINT fk_revisao_tipo_de_para_aud FOREIGN KEY (id_revisao) REFERENCES revisao(id_revisao)
);

COMMENT ON TABLE tipo_de_para_aud IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de Tipo de De/Para.';
COMMENT ON COLUMN  tipo_de_para_aud.id_tipo_de_para IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  tipo_de_para_aud.nome IS 'Nome do registro.';
COMMENT ON COLUMN  tipo_de_para_aud.descricao IS 'Descricao do registro.';
COMMENT ON COLUMN  tipo_de_para_aud.id_revisao IS 'Chave primaria da tabela revisao que identifica o numero da revisao que esta sendo representada pelo historico de auditoria.';
COMMENT ON COLUMN  tipo_de_para_aud.rev_type IS 'Identificar do tipo de revisao feita para o registro no historico de auditoria. Valores validos: 0 – Inclusao 1- Alteracao 2 – Delecao fisica';
COMMENT ON COLUMN  tipo_de_para_aud.ativo IS 'Indicador de status.  --  Valores validos: A - Ativo I - Inativo';
COMMENT ON COLUMN  tipo_de_para_aud.data_atualizacao IS 'Data de atualizacao da registro. Lei de formacao: DD/MM/AAAA onde DD e Dia, MM e mes, AAAA e ano.';
COMMENT ON COLUMN  tipo_de_para_aud.ind_forma_cadastro IS 'Indica a forma de cadastro.  --  Valores validos:  --  - I: Carga Inicial - C: Cadastro Manual - A: Carga Inicial Alterada';

--rollback drop sequence if exists sq_tipo_de_para;
--rollback drop sequence if exists sq_tipo_de_para_aud;
--rollback drop table if exists  tipo_de_para;
--rollback drop table if exists  tipo_de_para_aud;