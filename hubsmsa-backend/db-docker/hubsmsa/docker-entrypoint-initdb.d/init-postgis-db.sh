#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER hubsmsa WITH PASSWORD 'hubsmsa';
    CREATE DATABASE hubsmsa;
    GRANT ALL PRIVILEGES ON DATABASE hubsmsa TO hubsmsa;
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" hubsmsa <<-EOSQL
    CREATE SCHEMA hubsmsa AUTHORIZATION hubsmsa;
	COMMENT ON SCHEMA hubsmsa IS 'Esquema padrao do projeto.';
EOSQL

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" hubsmsa <<-EOSQL
    CREATE EXTENSION postgis;
EOSQL
