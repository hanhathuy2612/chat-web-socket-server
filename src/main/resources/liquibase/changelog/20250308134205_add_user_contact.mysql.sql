-- liquibase formatted sql

-- changeset withu:1741416132273-3
ALTER TABLE user_contacts
    ADD created_by VARCHAR(50) NOT NULL;

-- changeset withu:1741416132273-4
ALTER TABLE user_contacts
    ADD created_date datetime(6) NULL;

-- changeset withu:1741416132273-5
ALTER TABLE user_contacts
    ADD id BINARY(16) NOT NULL;

-- changeset withu:1741416132273-6
ALTER TABLE user_contacts
    ADD last_modified_by VARCHAR(50) NULL;

-- changeset withu:1741416132273-7
ALTER TABLE user_contacts
    ADD last_modified_date datetime(6) NULL;

-- changeset withu:1741416132273-8
ALTER TABLE user_contacts
    ADD status VARCHAR(255) NOT NULL;

-- changeset withu:1741416132273-9
CREATE UNIQUE INDEX IX_user_contactsPK ON user_contacts (id);


-- changeset withu:1741416132273-1
ALTER TABLE user_contacts
DROP
FOREIGN KEY FK2ma1py2epd0rp8wpnm2lq734n;

alter table user_contacts
DROP
FOREIGN KEY FKnjqy3qa6v81enho325u2wode6;

ALTER TABLE user_contacts DROP PRIMARY KEY;

-- changeset withu:1741416132273-2
ALTER TABLE user_contacts
    ADD PRIMARY KEY (id);

-- changeset withu:1741416132274-1
ALTER TABLE user_contacts
    ADD CONSTRAINT FK2ma1py2epd0rp8wpnm2lq734n
        FOREIGN KEY (contact_id)
            REFERENCES app_user (id);


-- changeset withu:1741416132274-2
ALTER TABLE user_contacts
    ADD CONSTRAINT FKnjqy3qa6v81enho325u2wode6
        FOREIGN KEY (user_id)
            REFERENCES app_user (id)


