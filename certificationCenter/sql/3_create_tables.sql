USE certificationCenter;

CREATE TABLE organisation (
    id INT AUTO_INCREMENT,
    unp INT UNSIGNED NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    phone BIGINT UNSIGNED NOT NULL,
    email VARCHAR(255) NOT NULL,
    accept BOOL,
    CONSTRAINT PK_organisation PRIMARY KEY(id)
);

CREATE TABLE user (
    id INT AUTO_INCREMENT,
    organisation_id INT NOT NULL,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    name VARCHAR(30) NOT NULL,
    surname VARCHAR(30) NOT NULL,
    patronymic VARCHAR(30) NOT NULL,
    phone BIGINT UNSIGNED NOT NULL,
    email VARCHAR(255) NOT NULL,
    user_role TINYINT UNSIGNED NOT NULL,
    actual BOOL,
    CONSTRAINT PK_user PRIMARY KEY(id),
    CONSTRAINT FK_user_organisation FOREIGN KEY (organisation_id) REFERENCES organisation(id)
);

CREATE TABLE application (
    id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    organisation_id INT NOT NULL,
    registration_number INT,
    date_add DATE NOT NULL,
    requirements VARCHAR(500) NOT NULL,
    date_resolve DATE,
    application_status TINYINT UNSIGNED,
    CONSTRAINT PK_application PRIMARY KEY(id),
    CONSTRAINT FK_application_user FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT FK_application_organisation FOREIGN KEY (organisation_id) REFERENCES organisation(id),
    CONSTRAINT CH_application_date CHECK (date_add <= date_resolve)
);

CREATE TABLE quantity_attribute (
    id TINYINT AUTO_INCREMENT,
    quantity_attribute_name VARCHAR(30),
    CONSTRAINT PK_product_quantity_attribute PRIMARY KEY(id)
);

CREATE TABLE product (
    id INT AUTO_INCREMENT,
    application_id INT NOT NULL,
    product_name VARCHAR(300) NOT NULL,
    product_code BIGINT UNSIGNED NOT NULL,
    producer_name VARCHAR(100) NOT NULL,
    producer_address VARCHAR(255) NOT NULL,
    quantity_attribute_id TINYINT NOT NULL,
    CONSTRAINT PK_product PRIMARY KEY(id),
    CONSTRAINT FK_product_application FOREIGN KEY (application_id) REFERENCES application(id),
    CONSTRAINT FK_product_quantity_attribute FOREIGN KEY (quantity_attribute_id) REFERENCES quantity_attribute (id)
);

CREATE TABLE documentation (
    id INT AUTO_INCREMENT,
    application_id INT NOT NULL,
    storage VARCHAR(2048),
    CONSTRAINT PK_documentation PRIMARY KEY(id),
    CONSTRAINT FK_documentation_application FOREIGN KEY (application_id) REFERENCES application(id)
);

CREATE INDEX idx_application_user_id ON application (user_id);

CREATE INDEX idx_product_application_id ON product (application_id);