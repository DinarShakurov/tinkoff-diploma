--liquibase formatted sql
--changeset shakurov_dinar:3 failOnError: true
-- https://docs.liquibase.com/concepts/changelogs/sql-format.html

CREATE TABLE medical_service.shedlock
(
    name       VARCHAR(64)  NOT NULL,
    lock_until TIMESTAMP    NOT NULL,
    locked_at  TIMESTAMP    NOT NULL,
    locked_by  VARCHAR(255) NOT NULL,
    PRIMARY KEY (name)
);

--rollback DROP TABLE medical_service.shedlock;