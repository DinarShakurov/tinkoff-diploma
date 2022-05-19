--liquibase formatted sql
--changeset shakurov_dinar:1 failOnError: true
-- https://docs.liquibase.com/concepts/changelogs/sql-format.html

CREATE TABLE medical_service.vaccine
(
    vaccine_id   BIGSERIAL PRIMARY KEY,
    vaccine_name text UNIQUE NOT NULL
);

CREATE TABLE medical_service.vaccination_point
(
    vaccination_point_id     BIGSERIAL PRIMARY KEY,
    vaccination_point_name   text        NOT NULL,
    vaccination_point_number text UNIQUE NOT NULL,
    city                     text        not null,
    address                  text        not null
);

CREATE TABLE medical_service.vaccination
(
    vaccination_id       BIGSERIAL PRIMARY KEY,
    vaccine_number       text UNIQUE NOT NULL,
    vaccine_id           BIGINT      NOT NULL,
    vaccination_date     DATE        NOT NUll,
    first_name           text        NOT NULL,
    last_name            text        NOT NULL,
    patronymic           text,
    document_number      text UNIQUE NOT NULL,
    vaccination_point_id BIGINT      NOT NULL,
    FOREIGN KEY (vaccine_id) REFERENCES medical_service.vaccine,
    FOREIGN KEY (vaccination_point_id) REFERENCES medical_service.vaccination_point
);

INSERT INTO medical_service.vaccine(vaccine_name)
VALUES ('vaccine#2'),
       ('vaccine#3');
INSERT INTO medical_service.vaccination_point(vaccination_point_name, vaccination_point_number, city, address)
VALUES ('Point_#3', 'PointNumber_Point_#3', 'City_Point_#3', 'Address_Point_#3'),
       ('Point_#2', 'PointNumber_Point_#2', 'City_Point_#2', 'Address_Point_#2'),
       ('Point_#1', 'PointNumber_Point_#1', 'City_Point_#1', 'Address_Point_#1');

--rollback DROP TABLE medical_service.vaccination;
--rollback DROP TABLE medical_service.vaccine;
--rollback DROP TABLE medical_service.vaccination_point;

