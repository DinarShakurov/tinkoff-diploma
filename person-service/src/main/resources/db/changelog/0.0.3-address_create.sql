--liquibase formatted sql
--changeset shakurov_dinar:3 failOnError: true
-- https://docs.liquibase.com/concepts/changelogs/sql-format.html

CREATE TABLE person_service.address
(
    address_id       BIGSERIAL PRIMARY KEY,
    region_id        integer NOT NULL,
    locality         text    NOT NULL,
    house_number     text    NOT NULL,
    apartment_number text,
    FOREIGN KEY (region_id) REFERENCES person_service.region (region_id)
);

--rollback DROP TABLE person_service.address;