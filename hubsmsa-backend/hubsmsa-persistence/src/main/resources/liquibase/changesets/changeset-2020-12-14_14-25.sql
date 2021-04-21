--liquibase formatted sql
 
--changeset weverton.lucena:2020-12-14_14-25 labels:1.0.0

-- TABELA: servico
INSERT INTO servico (id_sistema_primario,id_sistema_secundario,nome,descricao,ativo) VALUES
     ((select id_sistema from sistema where nome = 'SISREDE'),(select id_sistema from sistema where nome = 'SIGRAH'),'agendamento','Agendamento de consultas para pacientes','A'),
     ((select id_sistema from sistema where nome = 'SISREDE'),(select id_sistema from sistema where nome = 'SIGRAH'),'recuperarAtendimentosAgendados','Recuperar agendamentos','A');
