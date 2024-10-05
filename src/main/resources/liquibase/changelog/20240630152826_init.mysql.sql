-- liquibase formatted sql

-- changeset withu:1719736495613-1
CREATE TABLE app_user
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_by         VARCHAR(50) NOT NULL,
    created_date       datetime(6) NULL,
    last_modified_by   VARCHAR(50) NULL,
    last_modified_date datetime(6) NULL,
    activated          BIT         NOT NULL,
    activation_key     VARCHAR(20) NULL,
    email              VARCHAR(254) NULL,
    first_name         VARCHAR(50) NULL,
    last_name          VARCHAR(50) NULL,
    login              VARCHAR(50) NOT NULL,
    password_hash      VARCHAR(60) NOT NULL,
    reset_date         datetime(6) NULL,
    reset_key          VARCHAR(20) NULL,
    CONSTRAINT app_userPK PRIMARY KEY (id)
);

-- changeset withu:1719736495613-2
CREATE TABLE app_user_authority
(
    user_id        BIGINT      NOT NULL,
    authority_name VARCHAR(50) NOT NULL,
    CONSTRAINT app_user_authorityPK PRIMARY KEY (user_id, authority_name)
);

-- changeset withu:1719736495613-3
CREATE TABLE authority
(
    name VARCHAR(50) NOT NULL,
    CONSTRAINT authorityPK PRIMARY KEY (name)
);

-- changeset withu:1719736495613-4
CREATE TABLE chat_message
(
    id        BIGINT NOT NULL,
    content   VARCHAR(255) NULL,
    type      VARCHAR(255) NULL,
    sender_id BIGINT NULL,
    CONSTRAINT chat_messagePK PRIMARY KEY (id)
);

-- changeset withu:1719736495613-5
CREATE TABLE room
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    created_by         VARCHAR(50) NOT NULL,
    created_date       datetime(6) NULL,
    last_modified_by   VARCHAR(50) NULL,
    last_modified_date datetime(6) NULL,
    `description`      VARCHAR(255) NULL,
    name               VARCHAR(255) NULL,
    CONSTRAINT roomPK PRIMARY KEY (id)
);

-- changeset withu:1719736495613-6
CREATE TABLE room_app_user
(
    room_id     BIGINT NOT NULL,
    app_user_id BIGINT NOT NULL,
    CONSTRAINT room_app_userPK PRIMARY KEY (room_id, app_user_id)
);

-- changeset withu:1719736495613-7
ALTER TABLE app_user
    ADD CONSTRAINT UC_APP_USEREMAIL_COL UNIQUE (email);

-- changeset withu:1719736495613-8
ALTER TABLE app_user
    ADD CONSTRAINT UC_APP_USERLOGIN_COL UNIQUE (login);

-- changeset withu:1719736495613-9
ALTER TABLE chat_message
    ADD CONSTRAINT FK8y11cpin9xrphp0tkui2jvt2y FOREIGN KEY (sender_id) REFERENCES app_user (id);

-- changeset withu:1719736495613-10
ALTER TABLE app_user_authority
    ADD CONSTRAINT FKfocpjrj1tmhlu9vcfo47nqanp FOREIGN KEY (user_id) REFERENCES app_user (id);

-- changeset withu:1719736495613-11
ALTER TABLE app_user_authority
    ADD CONSTRAINT FKg5hrmqy8eoe8rvyse47qld1xq FOREIGN KEY (authority_name) REFERENCES authority (name);

-- changeset withu:1719736495613-12
ALTER TABLE room_app_user
    ADD CONSTRAINT FKkc5w3d5qp4wvs19qrlvkesls4 FOREIGN KEY (room_id) REFERENCES room (id);

-- changeset withu:1719736495613-13
ALTER TABLE room_app_user
    ADD CONSTRAINT FKn7255ol2nllr3rjm719cc5g79 FOREIGN KEY (app_user_id) REFERENCES app_user (id);

-- changeset withu:1719736495613-14
INSERT INTO authority (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

