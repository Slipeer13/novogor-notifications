--liquibase formatted sql

--changeset author:Popov Sergey
CREATE TABLE members (
id BIGINT NOT NULL,
PRIMARY KEY (id)
);
CREATE TABLE address (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE notification (
id BIGSERIAL PRIMARY KEY,
info VARCHAR(255) NOT NULL,
date_disabling DATE NOT NULL,
address_id BIGINT NOT NULL,
CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address(id)
);
CREATE TABLE member_address (
member_id BIGINT,
address_id BIGINT,
PRIMARY KEY (member_id, address_id),
FOREIGN KEY (member_id) REFERENCES members(id),
FOREIGN KEY (address_id) REFERENCES address(id)
);

