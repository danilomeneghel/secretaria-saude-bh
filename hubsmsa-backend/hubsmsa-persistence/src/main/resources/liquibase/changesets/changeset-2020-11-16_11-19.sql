--liquibase formatted sql
 
--changeset weverton.lucena:2020-11-16_11-19 labels:1.0.5

-- TABELA: servico
create sequence sq_servico;

CREATE TABLE servico (
	ID_SERVICO int8 NOT NULL DEFAULT nextval('sq_servico'::regclass),
	ID_SISTEMA_PRIMARIO int8 NOT NULL,
	ID_SISTEMA_SECUNDARIO int8 NOT NULL,
	NOME varchar(50) NOT NULL,
	DESCRICAO varchar(300) NOT NULL,
	ATIVO bpchar(1) NOT null,
	CONSTRAINT pk_servico PRIMARY KEY (id_servico)
);
CREATE INDEX ix_servico_01 ON servico USING btree (ID_SISTEMA_PRIMARIO);
CREATE INDEX ix_servico_02 ON servico USING btree (ID_SISTEMA_SECUNDARIO);


ALTER TABLE servico ADD CONSTRAINT fk_sistema_primario_servico FOREIGN KEY (ID_SISTEMA_PRIMARIO) REFERENCES sistema(id_sistema);
ALTER TABLE servico ADD CONSTRAINT fk_sistema_secundario_servico FOREIGN KEY (ID_SISTEMA_SECUNDARIO) REFERENCES sistema(id_sistema);


-- TABELA: log_servico
create sequence sq_log_servico;

CREATE TABLE log_servico (
	ID_LOG_SERVICO int8 NOT NULL DEFAULT nextval('sq_log_servico'::regclass),
	ID_SERVICO int8 NOT NULL,
	DATA_INICIO_EVENTO timestamp NOT NULL,
	DATA_FIM_EVENTO timestamp NOT NULL,
	STATUS char(1) NOT null,
	REQUISICAO text not null,
	RESPOSTA text not null,
	CONSTRAINT pk_log_servico PRIMARY KEY (ID_LOG_SERVICO)
);
CREATE INDEX ix_log_servico_01 ON log_servico USING btree (ID_SERVICO);

ALTER TABLE log_servico ADD CONSTRAINT fk_log_servico_servico FOREIGN KEY (ID_SERVICO) REFERENCES servico(id_servico);


-- TABELA: contato_empresa_servico
create sequence sq_contato_empresa_servico;

CREATE TABLE contato_empresa_servico (
	ID_CONTATO_EMPRESA_SERVICO int8 NOT NULL DEFAULT nextval('sq_contato_empresa_servico'::regclass),
	ID_CONTATO_EMPRESA int8 not null,
	ID_SERVICO int8 NOT NULL,
	NOTIFICACAO_SUCESSO char(1) NOT null,
	NOTIFICACAO_FALHA char(1) not null,
	CONSTRAINT pk_contato_empresa_servico PRIMARY KEY (ID_CONTATO_EMPRESA_SERVICO)
);
CREATE INDEX ix_contato_empresa_servico_01 ON contato_empresa_servico USING btree (ID_CONTATO_EMPRESA);
CREATE INDEX ix_contato_empresa_servico_02 ON contato_empresa_servico USING btree (ID_SERVICO);

ALTER TABLE contato_empresa_servico ADD CONSTRAINT fk_contato_empre_contato_serv FOREIGN KEY (ID_CONTATO_EMPRESA_SERVICO) REFERENCES contato_empresa(id_contato_empresa);
ALTER TABLE contato_empresa_servico ADD CONSTRAINT fk_servico_contato_servico FOREIGN KEY (ID_SERVICO) REFERENCES servico(id_servico);


-- TABELA: log_contato_notificado 
create sequence sq_log_contato_notificado;

CREATE TABLE log_contato_notificado (
	ID_LOG_CONTATO_NOTIFICADO int8 NOT NULL DEFAULT nextval('sq_log_contato_notificado'::regclass),
	ID_LOG_SERVICO int8 NOT NULL,
	ID_CONTATO_EMPRESA_SERVICO int8 not null,
	NOTIFICACAO_SUCESSO char(1) NOT null,
	NOTIFICACAO_FALHA char(1) not null,
	CONSTRAINT pk_log_contato_notificado PRIMARY KEY (ID_LOG_CONTATO_NOTIFICADO)
);
CREATE INDEX ix_log_contato_notificado_01 ON log_contato_notificado USING btree (ID_LOG_SERVICO);
CREATE INDEX ix_log_contato_notificado_02 ON log_contato_notificado USING btree (ID_CONTATO_EMPRESA_SERVICO);

ALTER TABLE log_contato_notificado ADD CONSTRAINT fk_log_cont_notif_con_emp_ser FOREIGN KEY (ID_CONTATO_EMPRESA_SERVICO) REFERENCES contato_empresa_servico(ID_CONTATO_EMPRESA_SERVICO);
ALTER TABLE log_contato_notificado ADD CONSTRAINT fk_log_contato_notif_log_serv FOREIGN KEY (ID_LOG_SERVICO) REFERENCES log_servico(id_log_servico);


