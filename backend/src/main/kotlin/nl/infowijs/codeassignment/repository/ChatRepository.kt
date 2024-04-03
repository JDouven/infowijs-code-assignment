package nl.infowijs.codeassignment.repository

import io.vertx.core.Future
import io.vertx.sqlclient.SqlConnection
import io.vertx.sqlclient.templates.SqlTemplate
import nl.infowijs.codeassignment.models.Chat


private const val SQL_SELECT_ALL = """
  SELECT c.id c_id, c.name c_name, p.id p_id, p.name p_name, *
  FROM chats c
    JOIN persons p ON p.id = c.person_id
  GROUP BY c_id, p_id
  """
private const val SQL_INSERT = """
  WITH c AS
  (INSERT INTO chats (name, person_id) VALUES (#{name}, #{person_id}) RETURNING *)
  SELECT c.id c_id, p.id p_id, * FROM c JOIN persons p ON p.id = c.person_id
  """

class ChatRepository {
  fun selectAll(connection: SqlConnection): Future<List<Chat>> {
    return SqlTemplate
      .forQuery(connection, SQL_SELECT_ALL)
      .mapTo(Chat.ROW_MAPPER)
      .execute(emptyMap())
      .map { rowSet ->
        val chats = mutableListOf<Chat>()
        rowSet.forEach(chats::add)
        chats
      }
  }

  fun create(connection: SqlConnection, chat: Chat): Future<Chat> {
    return SqlTemplate
      .forQuery(connection, SQL_INSERT)
      .mapFrom(Chat::class.java)
      .mapTo(Chat.ROW_MAPPER)
      .execute(chat)
      .map { rowSet ->
        val iterator = rowSet.iterator()
        if (iterator.hasNext()) {
          chat.id = iterator.next().id
          chat
        } else {
          throw IllegalStateException("Error creating chat");
        }
      }
  }
}
