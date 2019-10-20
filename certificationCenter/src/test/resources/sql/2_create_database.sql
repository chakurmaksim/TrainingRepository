CREATE DATABASE IF NOT EXISTS `certificationCenter`;

CREATE USER 'certificationCenter_user'@'localhost'
IDENTIFIED BY 'user';

GRANT SELECT, UPDATE, INSERT, DELETE
ON certificationCenter.*
TO 'certificationCenter_user'@'localhost';