--liquibase formatted sql
 
--changeset bruno.andradec:2020-09-24_15-30 labels:1.0.5

ALTER TABLE de_para_aud 
	ALTER COLUMN codigo_primario TYPE varchar(10),
	ALTER COLUMN codigo_secundario TYPE varchar(10);

ALTER TABLE de_para_primario 
	ALTER COLUMN codigo TYPE varchar(10);

ALTER TABLE de_para_primario_aud 
	ALTER COLUMN codigo TYPE varchar(10);

ALTER TABLE de_para_secundario 
	ALTER COLUMN codigo TYPE varchar(10);

ALTER TABLE de_para_secundario_aud 
	ALTER COLUMN codigo TYPE varchar(10);


--rollback ALTER TABLE de_para_aud ALTER COLUMN codigo_primario TYPE integer USING (trim(codigo_primario)::integer), ALTER COLUMN codigo_secundario TYPE integer USING (trim(codigo_secundario)::integer);
--rollback ALTER TABLE de_para_primario ALTER COLUMN codigo TYPE integer USING (trim(codigo)::integer);
--rollback ALTER TABLE de_para_primario_aud ALTER COLUMN codigo TYPE integer USING (trim(codigo)::integer);
--rollback ALTER TABLE de_para_secundario ALTER COLUMN codigo TYPE integer USING (trim(codigo)::integer);
--rollback ALTER TABLE de_para_secundario_aud ALTER COLUMN codigo TYPE integer USING (trim(codigo)::integer);
