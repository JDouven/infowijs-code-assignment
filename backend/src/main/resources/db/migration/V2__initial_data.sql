INSERT INTO persons (id, name, avatar, email, phone)
VALUES (0, 'Hans van Maanen', 'https://randomuser.me/api/portraits/men/12.jpg', 'hans@email.com', null),
       (1, 'Belinda de Vries', 'https://randomuser.me/api/portraits/women/12.jpg', null, '+31612345678'),
       (2, 'Gerrit Groot', 'https://randomuser.me/api/portraits/men/24.jpg', 'gerrit@email.com', '06 - 00 000 000'),
       (3, 'Monique Willems', 'https://randomuser.me/api/portraits/women/24.jpg', 'monique@email.com', null);

ALTER SEQUENCE persons_id_seq INCREMENT BY 4;

INSERT INTO chats (id, person_id, name)
VALUES (0, 0, 'Hans van Maanen'),
       (1, 3, 'Monique Willems');

ALTER SEQUENCE chats_id_seq INCREMENT BY 2;

INSERT INTO messages (id, chat_id, sender, message)
VALUES (0, 0, 'Hans van Maanen', 'Hey! Hoe gaat het?'),
       (1, 0, 'Me', 'Goed! En met jou?'),
       (2, 1, 'Monique Willems', 'Had je nog naar dat document kunnen kijken?'),
       (3, 1, 'Me', 'Nee nog niet! Ik heb het veel te druk.');

ALTER SEQUENCE messages_id_seq INCREMENT BY 4;
