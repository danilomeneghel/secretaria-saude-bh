--liquibase formatted sql
 
--changeset anderson.guedes:2020-12-22_15-50.sql labels:1.0.0

ALTER TABLE contato_empresa_servico DROP CONSTRAINT fk_contato_empre_contato_serv;
ALTER TABLE contato_empresa_servico ADD CONSTRAINT contato_empresa_servico_fk FOREIGN KEY (id_contato_empresa) REFERENCES contato_empresa(id_contato_empresa);


