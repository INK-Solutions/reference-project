CREATE SCHEMA IF NOT EXISTS templatedb;

CREATE TABLE templatedb.loan_request
(
    id                     varchar(50) PRIMARY KEY,
    client_id              varchar(50)    NOT NULL,
    amount                 numeric(19, 2) NOT NULL,
    approved               boolean        NOT NULL,

    creation_timestamp     TIMESTAMP      NOT NULL,
    modification_timestamp TIMESTAMP      NOT NULL
);