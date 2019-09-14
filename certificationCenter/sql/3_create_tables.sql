USE certificationCenter;

CREATE TABLE organisation (
    id INT AUTO_INCREMENT,
    unp INT UNSIGNED NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL UNIQUE,
    address TEXT NOT NULL,
    phone BIGINT UNSIGNED NOT NULL,
    email VARCHAR(255) NOT NULL,
    accept BOOL,
    CONSTRAINT PK_organisation PRIMARY KEY(id)
);

CREATE TABLE user_role (
    id TINYINT AUTO_INCREMENT,
    role_name VARCHAR(255) NOT NULL,
    CONSTRAINT PK_user_role PRIMARY KEY(id)
);

CREATE TABLE user (
    id INT AUTO_INCREMENT,
    organisation_id INT NOT NULL,
    login VARCHAR(255) NOT NULL UNIQUE,
    password TEXT NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255) NOT NULL,
    phone BIGINT UNSIGNED NOT NULL,
    email VARCHAR(255) NOT NULL,
    user_role_id TINYINT NOT NULL,
    actual BOOL,
    CONSTRAINT PK_user PRIMARY KEY(id),
    FOREIGN KEY (organisation_id) REFERENCES organisation(id),
    FOREIGN KEY (user_role_id) REFERENCES user_role(id)
);

CREATE TABLE application_status (
    id TINYINT AUTO_INCREMENT,
    status_name VARCHAR(255) NOT NULL,
    CONSTRAINT PK_application_status PRIMARY KEY(id)
);

CREATE TABLE application (
    id INT AUTO_INCREMENT,
    user_id INT NOT NULL,
    organisation_id INT NOT NULL,
    registration_number VARCHAR(255),
    date_add DATE NOT NULL,
    requirements TEXT NOT NULL,
    date_resolve DATE,
    application_status_id TINYINT NOT NULL,
    CONSTRAINT PK_application PRIMARY KEY(id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (organisation_id) REFERENCES organisation(id),
    FOREIGN KEY (application_status_id) REFERENCES application_status(id)
);

CREATE TABLE product_quantity_attribute (
    id TINYINT AUTO_INCREMENT,
    quantity_attribute_name VARCHAR(255),
    CONSTRAINT PK_product_quantity_attribute PRIMARY KEY(id)
);

CREATE TABLE product (
    id INT AUTO_INCREMENT,
    application_id INT NOT NULL,
    product_name TEXT NOT NULL,
    product_code BIGINT UNSIGNED NOT NULL,
    producer_name VARCHAR(255) NOT NULL,
    producer_address TEXT NOT NULL,
    product_quantity_attribute_id TINYINT NOT NULL,
    CONSTRAINT PK_product PRIMARY KEY(id),
    FOREIGN KEY (application_id) REFERENCES application(id),
    FOREIGN KEY (product_quantity_attribute_id) REFERENCES product_quantity_attribute(id)
);

CREATE TABLE documentation (
    id INT AUTO_INCREMENT,
    application_id INT NOT NULL,
    storage VARCHAR(255),
    CONSTRAINT PK_documentation PRIMARY KEY(id),
    FOREIGN KEY (application_id) REFERENCES application(id)
);