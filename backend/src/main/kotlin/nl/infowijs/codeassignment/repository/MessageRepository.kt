package nl.infowijs.codeassignment.repository

import io.vertx.core.Future
import io.vertx.sqlclient.SqlClient
import io.vertx.sqlclient.templates.SqlTemplate
import nl.infowijs.codeassignment.models.Message

private const val SQL_SELECT_BY_CHATID = """
  SELECT m.id m_id, p.id p_id, *
  FROM messages m
    JOIN persons p ON p.id = m.sender_id
  WHERE chat_id = 0
"""
private const val SQL_SELECT_LAST_BY_CHATID = """
  SELECT DISTINCT ON (chat_id) m.id m_id,
                             p.id p_id,
                             *
  FROM messages m
    JOIN persons p ON p.id = m.sender_id
  WHERE chat_id = 0
  ORDER BY chat_id, datetime DESC
"""
private const val SQL_INSERT = """
  WITH m AS
    (INSERT INTO messages (chat_id, sender_id, message) VALUES (#{chat_id}, #{sender}, #{message}) RETURNING *)
  SELECT m.id m_id, p.id p_id, * FROM m JOIN persons p ON p.id = m.sender_id
"""

class MessageRepository {
  fun selectWhereChatId(client: SqlClient, chatId: Int): Future<List<Message>> {
    return SqlTemplate
      .forQuery(client, SQL_SELECT_BY_CHATID)
      .mapTo(Message.ROW_MAPPER)
      .execute(mapOf(Pair("chat_id", chatId)))
      .map { rowSet ->
        val messages = mutableListOf<Message>()
        rowSet.forEach(messages::add)
        messages
      }
  }

  fun selectLastWhereChatId(client: SqlClient, chatId: Int): Future<Message> {
    return SqlTemplate
      .forQuery(client, SQL_SELECT_LAST_BY_CHATID)
      .mapTo(Message.ROW_MAPPER)
      .execute(mapOf(Pair("chat_id", chatId)))
      .map { it.last() }
  }

  fun create(client: SqlClient, message: Message): Future<Message> {
    return SqlTemplate
      .forQuery(client, SQL_INSERT)
      .mapFrom(Message::class.java)
      .mapTo(Message.ROW_MAPPER)
      .execute(message)
      .map { rowSet ->
        val iterator = rowSet.iterator()
        if (iterator.hasNext()) {
          iterator.next()
        } else {
          throw IllegalStateException("Error creating chat");
        }
      }
  }
}
