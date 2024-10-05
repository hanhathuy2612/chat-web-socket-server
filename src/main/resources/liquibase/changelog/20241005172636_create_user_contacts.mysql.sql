-- liquibase formatted sql

-- changeset withu:1728124002705-1
CREATE TABLE user_contacts
(
    user_id    BIGINT NOT NULL,
    contact_id BIGINT NOT NULL,
    CONSTRAINT user_contactsPK PRIMARY KEY (user_id, contact_id)
);

-- changeset withu:1728124002705-2
ALTER TABLE user_contacts
    ADD CONSTRAINT FK2ma1py2epd0rp8wpnm2lq734n FOREIGN KEY (contact_id) REFERENCES app_user (id);

-- changeset withu:1728124002705-3
ALTER TABLE user_contacts
    ADD CONSTRAINT FKnjqy3qa6v81enho325u2wode6 FOREIGN KEY (user_id) REFERENCES app_user (id);