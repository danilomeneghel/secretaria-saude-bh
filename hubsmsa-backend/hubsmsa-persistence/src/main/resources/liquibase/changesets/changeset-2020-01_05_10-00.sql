--liquibase formatted sql

--changeset solon.soares:2020_05_01_10-00 labels:1.0.0
drop sequence if exists sq_exemplo;
drop sequence if exists sq_exemplo_aud;
drop table if exists  exemplo;
drop table if exists  exemplo_aud;

CREATE SEQUENCE sq_exemplo
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE SEQUENCE sq_exemplo_aud
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE TABLE exemplo
(
	id_exemplo BIGINT NOT NULL default nextval('sq_exemplo'),
	cod_exemplo  VARCHAR(4) NOT NULL,
	descricao_exemplo VARCHAR(100) NOT NULL,
	ind_status CHARACTER(1) NULL,
	data_atualizacao DATE NOT NULL,
	ind_forma_cadastro CHARACTER(1) NOT NULL,
	CONSTRAINT pk_id_exemplo PRIMARY KEY (id_exemplo)
);

COMMENT ON TABLE exemplo IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de exemplos.';
COMMENT ON COLUMN  exemplo.id_exemplo IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  exemplo.cod_exemplo IS 'Codigo do registro.';
COMMENT ON COLUMN  exemplo.descricao_exemplo IS 'Descricao do registro.';
COMMENT ON COLUMN  exemplo.ind_status IS 'Indicador de status.  --  Valores validos: A - Ativo I - Inativo';
COMMENT ON COLUMN  exemplo.data_atualizacao IS 'Data de atualizacao d. Lei de formacao: DD/MM/AAAA onde DD e Dia, MM e mes, AAAA e ano.';
COMMENT ON COLUMN  exemplo.ind_forma_cadastro IS 'Indica a forma de cadastro.  --  Valores validos:  --  - I: Carga Inicial - C: Cadastro Manual - A: Carga Inicial Alterada';


CREATE TABLE exemplo_aud (
	id_exemplo BIGINT NOT NULL default nextval('sq_exemplo_aud'),
	cod_exemplo VARCHAR(4) NULL,
	descricao_exemplo VARCHAR(100) NULL,
	id_revisao BIGINT NOT NULL,
	rev_type BIGINT NOT NULL,
	ind_status CHARACTER(1) NULL,
	data_atualizacao DATE NULL,
	ind_forma_cadastro CHARACTER(1) NULL,
	CONSTRAINT pk_exemplo_aud PRIMARY KEY (id_exemplo, id_revisao),
	CONSTRAINT fk_revisao_exemplo_aud FOREIGN KEY (id_revisao) REFERENCES revisao(id_revisao)
);

COMMENT ON TABLE exemplo_aud IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de exemplos.';
COMMENT ON COLUMN  exemplo_aud.id_exemplo IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  exemplo_aud.cod_exemplo IS 'Codigo do registro.';
COMMENT ON COLUMN  exemplo_aud.descricao_exemplo IS 'Descricao do registro.';
COMMENT ON COLUMN  exemplo_aud.id_revisao IS 'Chave primaria da tabela revisao que identifica o numero da revisao que esta sendo representada pelo historico de auditoria.';
COMMENT ON COLUMN  exemplo_aud.rev_type IS 'Identificar do tipo de revisao feita para o registro no historico de auditoria. Valores validos: 0 – Inclusao 1- Alteracao 2 – Delecao fisica';
COMMENT ON COLUMN  exemplo_aud.ind_status IS 'Indicador de status.  --  Valores validos: A - Ativo I - Inativo';
COMMENT ON COLUMN  exemplo_aud.data_atualizacao IS 'Data de atualizacao da registro. Lei de formacao: DD/MM/AAAA onde DD e Dia, MM e mes, AAAA e ano.';
COMMENT ON COLUMN  exemplo_aud.ind_forma_cadastro IS 'Indica a forma de cadastro.  --  Valores validos:  --  - I: Carga Inicial - C: Cadastro Manual - A: Carga Inicial Alterada';

--rollback drop sequence if exists sq_exemplo;
--rollback drop sequence if exists sq_exemplo_aud;
--rollback drop table if exists  exemplo;
--rollback drop table if exists  exemplo_aud;