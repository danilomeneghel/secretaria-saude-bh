--liquibase formatted sql
 
--changeset bruno.andradec:2020-10-09_14-05 labels:1.0.5

ALTER TABLE empresa ALTER COLUMN cnpj DROP NOT NULL;

ALTER TABLE empresa_aud ALTER COLUMN cnpj DROP NOT NULL;

--rollback ALTER TABLE empresa ALTER COLUMN cnpj SET NOT NULL;
--rollback ALTER TABLE empresa_aud ALTER COLUMN cnpj SET NOT NULL;
