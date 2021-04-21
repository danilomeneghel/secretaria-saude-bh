--liquibase formatted sql
 
--changeset lucas.gonzaga:2020-10-09_14-05 labels:1.0.5

CREATE EXTENSION IF NOT EXISTS unaccent;

--rollback DROP EXTENSION IF EXISTS unaccent;

