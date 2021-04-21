--liquibase formatted sql
 
--changeset weverton.lucena:2021-01-06_09-41.sql labels:1.0.0

ALTER TABLE log_servico
   ADD COLUMN id_reenvio_servico int8;
 
  ALTER TABLE log_servico
   ADD CONSTRAINT FK_envio FOREIGN KEY (id_reenvio_servico)
      REFERENCES log_servico(id_log_servico)