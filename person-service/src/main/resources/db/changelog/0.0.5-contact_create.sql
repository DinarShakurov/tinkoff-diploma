--liquibase formatted sql
--changeset shakurov_dinar:5 failOnError: true
-- https://docs.liquibase.com/concepts/changelogs/sql-format.html

CREATE TABLE person_service.contact
(
    contact_id    BIGSERIAL PRIMARY KEY,
    contact_type  text   NOT NULL,
    contact_value text   NOT NULL,
    person_id     bigint NOT NULL,
    create_date      timestamp,
    modify_date      timestamp,
    modified_by      text,
    created_by       text,
    FOREIGN KEY (person_id) REFERENCES person_service.person (person_id),
    CONSTRAINT contact__type_value_udx UNIQUE (contact_type, contact_value)
);

CREATE INDEX contact__value_type_idx ON person_service.contact (contact_value, contact_type);

--rollback DROP INDEX contact__value_type_idx;
--rollback DROP TABLE person_service.contact;