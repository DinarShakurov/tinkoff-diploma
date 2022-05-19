--liquibase formatted sql
--changeset shakurov_dinar:1 failOnError: true
-- https://docs.liquibase.com/concepts/changelogs/sql-format.html

CREATE TABLE qr_storage_service.qr
(
    qr_id            BIGSERIAL PRIMARY KEY,
    qr_code          text UNIQUE NOT NULL,
    document_number  text        NOT NULL,
    vaccine_number   text UNIQUE NOT NULL,
    vaccine_name     text        NOT NULL,
    vaccination_date DATE        NOT NULL,
    create_date      timestamp   NOT NULL
);

--rollback DROP TABLE qr_storage_service.qr;

