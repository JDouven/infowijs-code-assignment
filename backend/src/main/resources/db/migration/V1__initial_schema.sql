CREATE TABLE IF NOT EXISTS persons
(
  id     SERIAL  NOT NULL,
  name   VARCHAR NOT NULL,
  avatar VARCHAR NOT NULL,
  email  VARCHAR,
  phone  VARCHAR,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS chats
(
  id        SERIAL  NOT NULL,
  owner_id INTEGER NOT NULL DEFAULT 0,
  person_id INTEGER NOT NULL,
  name      VARCHAR NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_person_id
    FOREIGN KEY (person_id)
      REFERENCES persons (id)
);

CREATE TABLE IF NOT EXISTS messages
(
  id        SERIAL  NOT NULL,
  chat_id   INTEGER NOT NULL,
  sender_id INTEGER NOT NULL,
  message   VARCHAR NOT NULL,
  datetime  VARCHAR NOT NULL DEFAULT to_json(now()) #>> '{}',
  PRIMARY KEY (id),
  CONSTRAINT fk_chat_id
    FOREIGN KEY (chat_id)
      REFERENCES chats (id)
);

