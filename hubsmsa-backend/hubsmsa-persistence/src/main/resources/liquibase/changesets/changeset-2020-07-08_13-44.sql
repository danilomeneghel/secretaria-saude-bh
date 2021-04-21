--liquibase formatted sql

--changeset andre.moreira:2020-07-08_13-44.sql labels:1.0.1
DROP SEQUENCE IF EXISTS sq_usuario;
DROP SEQUENCE IF EXISTS sq_usuario_aud;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS usuario_aud;

CREATE SEQUENCE sq_usuario
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
CREATE SEQUENCE sq_usuario_aud
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

CREATE TABLE usuario (
	id_usuario BIGINT NOT NULL default nextval('sq_usuario'),
	nome VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	login VARCHAR(30) NOT NULL,
	CONSTRAINT pk_id_usuario PRIMARY KEY (id_usuario)

);


COMMENT ON TABLE usuario IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de usuario';
COMMENT ON COLUMN  usuario.id_usuario IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  usuario.nome IS 'Nome do usuario.';
COMMENT ON COLUMN  usuario.email IS 'Email do usuario.';
COMMENT ON COLUMN  usuario.login IS 'Login de acesso do usuario.';

CREATE TABLE usuario_aud (
	id_usuario BIGINT NOT NULL default nextval('sq_usuario_aud'),
	nome VARCHAR(50)  NULL,
	email VARCHAR(50)  NULL,
	login VARCHAR(30)  NULL,
	id_revisao BIGINT NOT NULL,
	rev_type BIGINT NOT NULL,
	CONSTRAINT pk_usuario_aud PRIMARY KEY (id_usuario, id_revisao),
	CONSTRAINT fk_revisao_usuario_aud FOREIGN KEY (id_revisao) REFERENCES revisao(id_revisao)
);

COMMENT ON TABLE usuario_aud IS 'Estrutura de dados responsavel pelo armazenamento das informacoes de Usuario';
COMMENT ON COLUMN  usuario_aud.id_usuario IS 'Estrutura sequencial nao significativa que identifica unicamente um registro na estrutura da tabela.';
COMMENT ON COLUMN  usuario_aud.nome IS 'Nome do usuario.';
COMMENT ON COLUMN  usuario_aud.email IS 'Email do usuario.';
COMMENT ON COLUMN  usuario_aud.login IS 'Login do usuario.';
COMMENT ON COLUMN  usuario_aud.id_revisao IS 'Chave primaria da tabela revisao que identifica o numero da revisao que esta sendo representada pelo historico de auditoria.';
COMMENT ON COLUMN  usuario_aud.rev_type IS 'Identificar do tipo de revisao feita para o registro no historico de auditoria. Valores validos: 0 – Inclusao 1- Alteracao 2 – Delecao fisica';

--rollback drop sequence if exists sq_usuario;
--rollback drop sequence if exists sq_usuario_aud;
--rollback drop table if exists usuario;
--rollback drop table if exists usuario_aud;


