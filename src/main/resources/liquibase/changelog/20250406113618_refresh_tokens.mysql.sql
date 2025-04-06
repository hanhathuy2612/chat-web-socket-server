-- liquibase formatted sql

-- changeset withu:1743914184463-1
CREATE TABLE refresh_tokens
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    expiry_date datetime(6) NOT NULL,
    token       VARCHAR(255) NOT NULL,
    username    VARCHAR(255) NOT NULL,
    CONSTRAINT refresh_tokensPK PRIMARY KEY (id)
);

-- changeset withu:1743914184463-2
ALTER TABLE refresh_tokens
    ADD CONSTRAINT UC_REFRESH_TOKENSTOKEN_COL UNIQUE (token);