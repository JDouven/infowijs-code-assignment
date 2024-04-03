INSERT INTO persons (id, name, avatar, email, phone)
VALUES (0, 'Hans van Maanen', 'https://randomuser.me/api/portraits/men/12.jpg', 'hans@email.com', null),
       (1, 'Belinda de Vries', 'https://randomuser.me/api/portraits/women/12.jpg', null, '+31612345678'),
       (2, 'Gerrit Groot', 'https://randomuser.me/api/portraits/men/24.jpg', 'gerrit@email.com', '06 - 00 000 000'),
       (3, 'Monique Willems', 'https://randomuser.me/api/portraits/women/24.jpg', 'monique@email.com', null);

SELECT setval('persons_id_seq', 3);

INSERT INTO chats (id, owner_id, person_id, name)
VALUES (0, 0, 0, 'Hans van Maanen'),
       (1, 0, 3, 'Monique Willems');

SELECT setval('chats_id_seq', 1);

INSERT INTO messages (id, chat_id, sender_id, message)
VALUES (0, 0, 0, 'Hey! Hoe gaat het?'),
       (1, 0, 1, 'Goed! En met jou?'),
       (2, 1, 3, 'Had je nog naar dat document kunnen kijken?'),
       (3, 1, 1, 'Nee nog niet! Ik heb het veel te druk.');

SELECT setval('messages_id_seq', 3);
