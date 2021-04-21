--liquibase formatted sql
 
--changeset solon.assis:2020-09-14_11-35 context:dev
INSERT INTO sistema (id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (6,'a1','a1','A','2020-08-28','C');
INSERT INTO sistema (id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (5,'a1','a1 34','A','2020-08-28','C');
INSERT INTO sistema (id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (5,'Sist Inativo','inativo','I','2020-08-28','C');
INSERT INTO sistema (id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (4,'a1','1fsdf','I','2020-08-28','C');
INSERT INTO sistema (id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (10,'SIGRAH','sigrah','A','2020-08-28','C');
INSERT INTO sistema (id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (10,'SISREDE','sisrede','A','2020-08-28','C');
INSERT INTO sistema (id_empresa,nome,descricao,ativo,data_atualizacao,ind_forma_cadastro) VALUES (10,'SIEST','siest','A','2020-08-28','C');

INSERT INTO de_para_primario (id_de_para,codigo,descricao) VALUES (1,100,'Dipirona 100');
INSERT INTO de_para_primario (id_de_para,codigo,descricao) VALUES (6,101,'desc 101');
INSERT INTO de_para_primario (id_de_para,codigo,descricao) VALUES (5,2,'desc 2');
INSERT INTO de_para_primario (id_de_para,codigo,descricao) VALUES (5,3,'desc 3');
INSERT INTO de_para_primario (id_de_para,codigo,descricao) VALUES (5,1,'desc 1');
INSERT INTO de_para_primario (id_de_para,codigo,descricao) VALUES (6,100,'desc 100');
INSERT INTO de_para_primario (id_de_para,codigo,descricao) VALUES (8,110,'desc 110');
INSERT INTO de_para_primario (id_de_para,codigo,descricao) VALUES (8,111,'desc 111');
INSERT INTO de_para_primario (id_de_para,codigo,descricao) VALUES (8,109,'desc 109');

INSERT INTO de_para_secundario (id_de_para,codigo,descricao) VALUES (1,200,'Dipirona 200');
INSERT INTO de_para_secundario (id_de_para,codigo,descricao) VALUES (5,4,'desc 4');
INSERT INTO de_para_secundario (id_de_para,codigo,descricao) VALUES (5,5,'desc 5');
INSERT INTO de_para_secundario (id_de_para,codigo,descricao) VALUES (6,6,'6');
INSERT INTO de_para_secundario (id_de_para,codigo,descricao) VALUES (8,180,'desc 180');
INSERT INTO de_para_secundario (id_de_para,codigo,descricao) VALUES (8,181,'desc 181');


--rollback DELETE FROM sistema WHERE id_empresa=6;
--rollback DELETE FROM sistema WHERE id_empresa=5;
--rollback DELETE FROM sistema WHERE id_empresa=5;
--rollback DELETE FROM sistema WHERE id_empresa=4;
--rollback DELETE FROM sistema WHERE id_empresa=10;
--rollback DELETE FROM sistema WHERE id_empresa=10;
--rollback DELETE FROM sistema WHERE id_empresa=10;

--rollback DELETE FROM de_para_secundario WHERE id_de_para=1 AND codigo=200;
--rollback DELETE FROM de_para_secundario WHERE id_de_para=5 AND codigo=4;
--rollback DELETE FROM de_para_secundario WHERE id_de_para=5 AND codigo=5;
--rollback DELETE FROM de_para_secundario WHERE id_de_para=6 AND codigo=6;
--rollback DELETE FROM de_para_secundario WHERE id_de_para=8 AND codigo=180;
--rollback DELETE FROM de_para_secundario WHERE id_de_para=8 AND codigo=181;

--rollback DELETE FROM de_para_primario WHERE id_de_para=1 AND codigo=100;
--rollback DELETE FROM de_para_primario WHERE id_de_para=6 AND codigo=101;
--rollback DELETE FROM de_para_primario WHERE id_de_para=5 AND codigo=2;
--rollback DELETE FROM de_para_primario WHERE id_de_para=5 AND codigo=3;
--rollback DELETE FROM de_para_primario WHERE id_de_para=5 AND codigo=1;
--rollback DELETE FROM de_para_primario WHERE id_de_para=6 AND codigo=100;
--rollback DELETE FROM de_para_primario WHERE id_de_para=8 AND codigo=110;
--rollback DELETE FROM de_para_primario WHERE id_de_para=8 AND codigo=111;
--rollback DELETE FROM de_para_primario WHERE id_de_para=8 AND codigo=109;
