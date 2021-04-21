--liquibase formatted sql

--changeset andre.moreira:2020-07-08_14-31.sql labels:1.0.1
DROP SEQUENCE IF EXISTS sq_log_acesso_usuario;
DROP SEQUENCE IF EXISTS sq_log_acesso_usuario_aud;
DROP TABLE IF EXISTS log_acesso_usuario;
DROP TABLE IF EXISTS log_acesso_usuario_aud;

CREATE SEQUENCE sq_log_acesso_usuario
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE SEQUENCE sq_log_acesso_usuario_aud
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE log_acesso_usuario (
	id_log_acesso_usuario BIGINT NOT NULL default nextval('sq_log_acesso_usuario'),
	id_usuario BIGINT NOT NULL,
	data_acesso timestamp NOT NULL,
	CONSTRAINT pk_id_log_acesso_usuario PRIMARY KEY (id_log_acesso_usuario),
	CONSTRAINT fk_usuario_log_acesso_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE INDEX ix_log_acesso_usuario_01
ON log_acesso_usuario(id_log_acesso_usuario);

COMMENT ON TABLE log_acesso_usuario IS 'Estrutura de dados responsavel pelo armazenamento das informacoes do log de acesso do usuario ';
COMMENT ON COLUMN  log_acesso_usuario.id_log_acesso_usuario IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  log_acesso_usuario.id_usuario IS 'Identificador do ussuario.';
COMMENT ON COLUMN  log_acesso_usuario.data_acesso IS 'Data de acesso.';

CREATE TABLE log_acesso_usuario_aud (
	id_log_acesso_usuario BIGINT NOT NULL default nextval('sq_log_acesso_usuario'),
	id_usuario BIGINT,
	data_acesso timestamp,
	id_revisao BIGINT NOT NULL,
	rev_type BIGINT NOT NULL,
	CONSTRAINT pk_log_acesso_usuario_aud PRIMARY KEY (id_log_acesso_usuario, id_revisao),
	CONSTRAINT fk_revisao_log_acesso_usuario_aud FOREIGN KEY (id_revisao) REFERENCES revisao(id_revisao),
	CONSTRAINT fk_usuario_log_acesso_usuario_aud FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

COMMENT ON TABLE log_acesso_usuario_aud IS 'Estrutura de dados responsavel pelo armazenamento das informacoes do log de acesso do usuario';
COMMENT ON COLUMN  log_acesso_usuario_aud.id_log_acesso_usuario IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  log_acesso_usuario_aud.id_usuario IS 'Identificador do ussuario.';
COMMENT ON COLUMN  log_acesso_usuario_aud.data_acesso IS 'Data de acesso.';
COMMENT ON COLUMN  log_acesso_usuario_aud.id_revisao IS 'Chave primaria da tabela revisao que identifica o numero da revisao que esta sendo representada pelo historico de auditoria.';
COMMENT ON COLUMN  log_acesso_usuario_aud.rev_type IS 'Identificar do tipo de revisao feita para o registro no historico de auditoria. Valores validos: 0 – Inclusao 1- Alteracao 2 – Delecao fisica';

--rollback drop sequence if exists sq_log_acesso_usuario;
--rollback drop sequence if exists sq_log_acesso_usuario_aud;
--rollback drop table if exists log_acesso_usuario;
--rollback drop table if exists log_acesso_usuario_aud;


