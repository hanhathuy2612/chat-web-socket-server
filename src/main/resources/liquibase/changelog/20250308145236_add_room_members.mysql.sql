-- liquibase formatted sql

-- changeset withu:1741420360942-4
ALTER TABLE room_app_user DROP FOREIGN KEY FKkc5w3d5qp4wvs19qrlvkesls4;

-- changeset withu:1741420360942-5
ALTER TABLE room_app_user DROP FOREIGN KEY FKn7255ol2nllr3rjm719cc5g79;

-- changeset withu:1741420360942-1
CREATE TABLE room_members
(
    id                 BINARY(16) NOT NULL,
    created_by         VARCHAR(50) NOT NULL,
    created_date       datetime(6) NULL,
    last_modified_by   VARCHAR(50) NULL,
    last_modified_date datetime(6) NULL,
    member_id          BINARY(16) NULL,
    room_id            BINARY(16) NULL,
    CONSTRAINT room_membersPK PRIMARY KEY (id)
);

-- changeset withu:1741420360942-2
ALTER TABLE room_members
    ADD CONSTRAINT FK4art8ns4hx9lhmctfxvx8p1m FOREIGN KEY (member_id) REFERENCES app_user (id);

-- changeset withu:1741420360942-3
ALTER TABLE room_members
    ADD CONSTRAINT FK76lf284bb0baceybfd2gi50rl FOREIGN KEY (room_id) REFERENCES room (id);

-- changeset withu:1741420360942-6
ALTER TABLE user_contacts DROP KEY IX_user_contactsPK;

-- changeset withu:1741420360942-7
DROP TABLE room_app_user;

