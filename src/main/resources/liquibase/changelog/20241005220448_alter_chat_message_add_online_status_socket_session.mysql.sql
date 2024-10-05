-- liquibase formatted sql

-- changeset withu:1728140692700-1
ALTER TABLE app_user
    ADD is_online BIT NOT NULL;

-- changeset withu:1728140692700-2
ALTER TABLE app_user
    ADD socket_session_id VARCHAR(255) NULL;

