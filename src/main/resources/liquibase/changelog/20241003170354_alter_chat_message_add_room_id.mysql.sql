-- liquibase formatted sql

-- changeset withu:1727949838223-1
ALTER TABLE chat_message
    ADD room_id BIGINT NULL;

-- changeset withu:1727949838223-2
ALTER TABLE chat_message
    ADD CONSTRAINT FKtc2pputadncp2auve2t53i7pc FOREIGN KEY (room_id) REFERENCES room (id);