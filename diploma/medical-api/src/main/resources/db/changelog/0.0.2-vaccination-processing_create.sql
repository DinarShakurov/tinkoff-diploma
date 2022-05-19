--liquibase formatted sql
--changeset shakurov_dinar:2 failOnError: true
-- https://docs.liquibase.com/concepts/changelogs/sql-format.html

CREATE TABLE medical_service.vaccination_processing
(
    vaccination_id BIGINT PRIMARY KEY,
    status         text NOT NULL,
    FOREIGN KEY (vaccination_id) REFERENCES medical_service.vaccination
);

--rollback DROP TABLE medical_service.vaccination_processing;

