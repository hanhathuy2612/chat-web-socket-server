-- liquibase formatted sql

-- changeset withu:1719744835969-1
ALTER TABLE chat_message
    ADD created_by VARCHAR(50) NOT NULL;

-- changeset withu:1719744835969-2
ALTER TABLE chat_message
    ADD created_date datetime(6) NULL;

-- changeset withu:1719744835969-3
ALTER TABLE chat_message
    ADD last_modified_by VARCHAR(50) NULL;

-- changeset withu:1719744835969-4
ALTER TABLE chat_message
    ADD last_modified_date datetime(6) NULL;

