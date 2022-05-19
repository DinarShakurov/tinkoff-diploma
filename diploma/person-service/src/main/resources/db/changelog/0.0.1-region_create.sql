--liquibase formatted sql
--changeset shakurov_dinar:1 failOnError: true
-- https://docs.liquibase.com/concepts/changelogs/sql-format.html

CREATE TABLE person_service.region
(
    region_id SERIAL PRIMARY KEY,
    region_code text UNIQUE NOT NULL,
    region_name text NOT NULL,
    kladr_id text UNIQUE NOT NULL,
    fias_id text UNIQUE NOT NULL
);

--rollback DROP TABLE person_service.region;