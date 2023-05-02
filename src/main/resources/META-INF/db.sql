USE discord;

INSERT INTO User(usr_email, usr_password, usr_lastname, usr_firstname) VALUES ('pierre.martin@message.fr', 'PBKDF2WithHmacSHA256:2048:AbTqHlzfBu2NjpkGQ3NdRfJNsgtCHmopyZNijQ92h6A=:hZF6nRYKaSE43qsX4+AKYlNYK+DqG0tjblnnL2Ro5Rs=', 'MARTIN', 'Pierre');
INSERT INTO User(usr_email, usr_password, usr_lastname, usr_firstname) VALUES ('julie.dupont@message.fr', 'PBKDF2WithHmacSHA256:2048:AbTqHlzfBu2NjpkGQ3NdRfJNsgtCHmopyZNijQ92h6A=:hZF6nRYKaSE43qsX4+AKYlNYK+DqG0tjblnnL2Ro5Rs=', 'DUPONT', 'Julie');
INSERT INTO User(usr_email, usr_password, usr_lastname, usr_firstname) VALUES ('jean.dufour@message.fr', 'test', 'DUFOUR', 'Jean');

INSERT INTO Role(rol_name) VALUES (0), (1);

INSERT INTO has_role(usr_id, rol_id) VALUES (1, 1), (1, 2), (2, 1), (3, 1);

INSERT INTO Channel(cha_name, cha_visibility) VALUES ('DEVLOG Java', 0);
INSERT INTO Channel(cha_name, cha_visibility) VALUES ('DEVLOG C#', 1);

INSERT INTO is_allowed_in(cha_id, usr_id) VALUES (1, 1);
# INSERT INTO is_allowed_in(cha_id, usr_id) VALUES (2, 1);
INSERT INTO is_allowed_in(cha_id, usr_id) VALUES (1, 2);

INSERT INTO Subject(DTYPE, sub_sent_at, sub_channel) VALUES ('Message', NOW(), (SELECT cha_id FROM Channel WHERE cha_name = 'DEVLOG Java'));

INSERT INTO Message(sub_id, msg_content, msg_sender, msg_file)  VALUES (last_insert_id(), 'Message de test', (SELECT usr_id FROM User WHERE usr_email = 'pierre.martin@message.fr'), null);

