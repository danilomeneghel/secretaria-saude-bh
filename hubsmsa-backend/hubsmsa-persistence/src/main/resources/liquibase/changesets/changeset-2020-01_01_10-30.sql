--liquibase formatted sql

--changeset solon.soares:2020_01_01_10-30 labels:1.0.0
DROP SEQUENCE IF EXISTS sq_revisao;
DROP TABLE IF EXISTS revisao;

CREATE SEQUENCE sq_revisao
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE revisao (
	id_revisao BIGINT NOT NULL,
	dt_revisao TIMESTAMP NOT NULL,
	usr_login VARCHAR(255) NOT NULL,
	CONSTRAINT pk_revisao PRIMARY KEY (id_revisao)
);

COMMENT ON TABLE revisao IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de exemplos.';
COMMENT ON COLUMN revisao.dt_revisao IS 'Data de inclusao de um registro na tabela, nao pode ser menor que a data atual, lei de formacao: DD/MM/AAAA onde DD  Dia, MM  mes, AAAA  ano.';
COMMENT ON COLUMN revisao.usr_login IS 'Matricula que identifica o usuario responsavel pela alteracao do registro no sistema.';

--rollback drop sequence if exists sq_revisao;
--rollback drop table if exists revisao;
