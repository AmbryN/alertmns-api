USE discord;

INSERT INTO User(usr_email, usr_password, usr_lastname, usr_firstname) VALUES ('julien.cansell@stagiairesmns.fr', 'test', 'CANSELL', 'Julien');
INSERT INTO User(usr_email, usr_password, usr_lastname, usr_firstname) VALUES ('nathan.wagner@stagiairesmns.fr', 'test', 'WAGNER', 'NATHAN');
INSERT INTO User(usr_email, usr_password, usr_lastname, usr_firstname) VALUES ('herve.bonnabaud@stagiairesmns.fr', 'test', 'BONNABAUD', 'Hervé');


INSERT INTO Visibility(vis_name) VALUES (1);
INSERT INTO Visibility(vis_name) VALUES (2);

INSERT INTO Channel(cha_name, cha_visibility) VALUES ('DEVLOG Java', (SELECT vis_id FROM Visibility WHERE vis_name = 1));
INSERT INTO Channel(cha_name, cha_visibility) VALUES ('DEVLOG C#', (SELECT vis_id FROM Visibility WHERE vis_name = 2));

INSERT INTO Subject(DTYPE, sub_sent_at, sub_channel) VALUES ('Message', NOW(), (SELECT cha_id FROM Channel WHERE cha_name = 'DEVLOG Java'));

INSERT INTO Message(sub_id, msg_content, msg_sender, msg_file)  VALUES (last_insert_id(), 'Message de test', (SELECT usr_id FROM User WHERE usr_email = 'julien.cansell@stagiairesmns.fr'), null);

