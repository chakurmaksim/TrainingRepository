INSERT INTO user(organisation_id, login, password, name, surname, patronymic, phone, email, user_role, actual)
VALUES(1, 'admin', '01fe43d9cb29ad7597006c54e0a6f5ab3e4fe5bd58825e1cd98bfc23724eed74',
       'AdminName', 'AdminSurname', 'AdminPatronymic',
       375291234567, 'admin@certification.org', 1, true);
INSERT INTO user(organisation_id, login, password, name, surname, patronymic, phone, email, user_role, actual)
VALUES(1, 'expert', '0bdd99c5ac4b9acb797eb235eaff3bc935bf9643bacde5168492335af9da74d8',
       'ExpertNme', 'ExpertSurname', 'ExpertPatronymic', 375441234567, 'expert@certification.org', 2, true);
INSERT INTO user(organisation_id, login, password, name, surname, patronymic, phone, email, user_role, actual)
VALUES(2, 'client_1', '96bd6832d0116a3ffd376bfa08499f598d70bfbee68f7582dfca5118c587b684',
       'ClientName_1', 'ClientSurname_1', 'ClientPatronymic_1', 375291234567, 'client@organisation1.org', 3, true);
INSERT INTO user(organisation_id, login, password, name, surname, patronymic, phone, email, user_role, actual)
VALUES(3, 'client_2', 'd99fb0b65a0cf05b3005ddec473fb3b4a4a2e38d250212d1e48f04c36ab04e83',
       'ClientName_2', 'ClientSurname_2', 'ClientPatronymic_2', 375291234567, 'client@organisation2.org', 3, true);