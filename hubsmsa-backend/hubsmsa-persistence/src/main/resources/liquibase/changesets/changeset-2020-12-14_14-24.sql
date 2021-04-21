--liquibase formatted sql
 
--changeset solon.soares:2020-12-14_14-24 labels:1.0.0

-- TABELA: empresa
INSERT INTO empresa (id_empresa,nome_empresarial,nome_fantasia,cnpj,codigo_cnes,site,ativo) VALUES (nextval('sq_empresa'),'Secretaria Municipal de Saúde','SMSA','18715516002474',5853370,'http://prefeitura.pbh.gov.br/saude','A');

-- TABELA: sistema
INSERT INTO sistema (id_sistema,id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (nextval('sq_sistema'),(select id_empresa from empresa where nome_fantasia='SMSA'),'SISREDE','Sistema SISREDE','A','2020-12-07','C');
INSERT INTO sistema (id_sistema,id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (nextval('sq_sistema'),(select id_empresa from empresa where nome_fantasia='SMSA'),'SIGRAH','Sistema SIGRAH','A','2020-12-07','C');
INSERT INTO sistema (id_sistema,id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (nextval('sq_sistema'),(select id_empresa from empresa where nome_fantasia='SMSA'),'SISCONSULTA','Sistema de consulta','A','2020-12-15','C');
INSERT INTO sistema (id_sistema,id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (nextval('sq_sistema'),(select id_empresa from empresa where nome_fantasia='SMSA'),'SISExame','Sistema de cadastro de exames','I','2020-12-15','C');
INSERT INTO sistema (id_sistema,id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (nextval('sq_sistema'),(select id_empresa from empresa where nome_fantasia='SMSA'),'SISCOMUN','Sistema de comunicação','I','2020-12-16','C');
INSERT INTO sistema (id_sistema,id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (nextval('sq_sistema'),(select id_empresa from empresa where nome_fantasia='SMSA'),'SISTEMA REDE','rede','A','2020-12-16','C');