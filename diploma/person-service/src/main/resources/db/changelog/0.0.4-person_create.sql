--liquibase formatted sql
--changeset shakurov_dinar:4 failOnError: true
-- https://docs.liquibase.com/concepts/changelogs/sql-format.html

CREATE TABLE person_service.person
(
    person_id   BIGSERIAL PRIMARY KEY,
    first_name  text NOT NULL,
    last_name   text NOT NULL,
    patronymic  text,
    birth_date  date NOT NULL,
    create_date timestamp,
    modify_date timestamp,
    modified_by text,
    created_by  text,
    is_deleted  bool NOT NULL DEFAULT FALSE
);

CREATE TABLE person_service.person_to_address
(
    id           serial primary key,
    person_id    bigint,
    address_id   bigint,
    address_type text,
    FOREIGN KEY (person_id) REFERENCES person_service.person (person_id),
    FOREIGN KEY (address_id) REFERENCES person_service.address (address_id),
    CONSTRAINT pta__person_address_udx UNIQUE (person_id, address_id)
)

--rollback DROP TABLE person_service.person;
--rollback DROP TABLE person_service.person_to_address;