--liquibase formatted sql
--changeset shakurov_dinar:6 failOnError: true
-- https://docs.liquibase.com/concepts/changelogs/sql-format.html

CREATE TABLE person_service.identity_document
(
    identity_document_id BIGSERIAL PRIMARY KEY,
    document_type        text   NOT NULL,
    document_number      text   NOT NULL,
    is_primary           bool   NOT NULL,
    person_id            bigint NOT NULL,
    create_date      timestamp,
    modify_date      timestamp,
    modified_by      text,
    created_by       text,
    FOREIGN KEY (person_id) REFERENCES person_service.person (person_id),
    CONSTRAINT identity_document__type_number_udx UNIQUE (document_number, document_type)
);

CREATE INDEX identity_document__type_number_idx ON person_service.identity_document (document_number, document_type)

--rollback DROP INDEX identity_document__type_number_idx;
--rollback DROP TABLE person_service.identity_document;
