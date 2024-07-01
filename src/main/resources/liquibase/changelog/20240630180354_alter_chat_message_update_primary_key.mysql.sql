-- liquibase formatted sql

-- changeset withu:1719744835969-5
ALTER TABLE chat_message
    DROP PRIMARY KEY;

-- changeset withu:1719744835969-6
ALTER TABLE chat_message
    MODIFY id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY;
