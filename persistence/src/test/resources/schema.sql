CREATE TABLE IF NOT EXISTS gift_certificates
(
    certificateId    BIGINT         NOT NULL AUTO_INCREMENT,
    name             VARCHAR(250)   NOT NULL,
    description      VARCHAR(250)   NOT NULL,
    price            DECIMAL(10, 2) NOT NULL,
    duration         INT            NOT NULL,
    create_date      TIMESTAMP      NOT NULL,
    last_update_date TIMESTAMP      NOT NULL,
    PRIMARY KEY (certificateId),
    UNIQUE INDEX name_UNIQUE (name ASC)
);

CREATE TABLE IF NOT EXISTS tags
(
    tagId   BIGINT      NOT NULL AUTO_INCREMENT,
    tagName VARCHAR(45) NOT NULL,
    PRIMARY KEY (tagId),
    UNIQUE INDEX tagName_UNIQUE (tagName ASC)
);

CREATE TABLE IF NOT EXISTS certificates_has_tags
(
    certificateId BIGINT NOT NULL,
    tagId         BIGINT NOT NULL,
    PRIMARY KEY (certificateId, tagId),
    CONSTRAINT fk_gift_certificates_has_tags_gift_certificates
        FOREIGN KEY (certificateId)
            REFERENCES gift_certificates (certificateId)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_gift_certificates_has_tags_tags1
        FOREIGN KEY (tagId)
            REFERENCES tags (tagId)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS users
(
    userId    BIGINT      NOT NULL AUTO_INCREMENT,
    login     VARCHAR(20) NOT NULL,
    firstName VARCHAR(20) NOT NULL,
    lastName  VARCHAR(20) NOT NULL,
    PRIMARY KEY (userId),
    UNIQUE INDEX login_UNIQUE (login ASC)
);

CREATE TABLE IF NOT EXISTS orders
(
    orderId       BIGINT         NOT NULL AUTO_INCREMENT,
    userId        BIGINT         NOT NULL,
    purchase_date TIMESTAMP      NOT NULL,
    cost          DECIMAL(10, 2) NULL,
    PRIMARY KEY (orderId),
    CONSTRAINT fk_orders_users1
        FOREIGN KEY (userId)
            REFERENCES users (userId)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS orders_has_gift_certificate
(
    orderId       BIGINT NOT NULL,
    certificateId BIGINT NOT NULL,
    PRIMARY KEY (orderId, certificateId),
    CONSTRAINT fk_orders_has_gift_certificates_orders1
        FOREIGN KEY (orderId)
            REFERENCES orders (orderId)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_orders_has_gift_certificates_gift_certificates1
        FOREIGN KEY (certificateId)
            REFERENCES gift_certificates (certificateId)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);